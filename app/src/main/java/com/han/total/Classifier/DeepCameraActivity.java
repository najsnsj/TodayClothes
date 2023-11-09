package com.han.total.Classifier;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.han.total.R;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class DeepCameraActivity extends AppCompatActivity {
    public static final String TAG = "[IC]CameraActivity";
    public static final int CAMERA_IMAGE_REQUEST_CODE = 1;
    private static final String KEY_SELECTED_URI = "KEY_SELECTED_URI";

    private ClassifierWithModel cls;
    private ImageView imageView;
    private TextView textView;

//    private Button newButton;

    Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deepcamera);

        Button takeBtn = findViewById(R.id.deeptakeBtn);
        takeBtn.setOnClickListener(v -> getImageFromCamera());
//        Button newButton = findViewById(R.id.newButton);


        imageView = findViewById(R.id.deepimageView);
        textView = findViewById(R.id.deeptextView);

        cls = new ClassifierWithModel(this);
        try {
            cls.init();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        if(savedInstanceState != null) {
            Uri uri = savedInstanceState.getParcelable(KEY_SELECTED_URI);
            if (uri != null)
                selectedImageUri = uri;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(KEY_SELECTED_URI, selectedImageUri);
    }

    private void getImageFromCamera(){
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "picture.jpg");
        selectedImageUri = FileProvider.getUriForFile(this, getPackageName(), file);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImageUri);
        startActivityForResult(intent, CAMERA_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK &&
                requestCode == CAMERA_IMAGE_REQUEST_CODE) {

            Uri selectedImageUri = data.getData();

            Bitmap bitmap = null;
            try {
                if(Build.VERSION.SDK_INT >= 29) {
                    ImageDecoder.Source src = ImageDecoder.createSource(
                            getContentResolver(), selectedImageUri);
                    bitmap = ImageDecoder.decodeBitmap(src);
                } else {
                    bitmap = MediaStore.Images.Media.getBitmap(
                            getContentResolver(), selectedImageUri);
                }
            } catch (IOException ioe) {
                Log.e(TAG, "Failed to read Image", ioe);
            }

            if(bitmap != null) {
                Pair<String, Float> output = cls.classify(bitmap);
                String resultStr = String.format(Locale.ENGLISH,
                        "class : %s, prob : %.2f%%",
                        output.first, output.second * 100);

                // 여기에서 결과값을 확인하여 특정 단어에 해당한다면 "상의"로 변경
                if(isUpperGarment(output.first)) {
                    resultStr = "상의";
                }

                // 여기에서 결과값을 확인하여 특정 단어에 해당한다면 "하의"로 변경
                if(isLowerGarment(output.first)) {
                    resultStr = "하의";
                }

                imageView.setImageBitmap(bitmap);
                textView.setText(resultStr);

//                // 결과값이 출력되면 newButton을 보이게 설정
//                newButton.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    protected void onDestroy() {
        cls.finish();
        super.onDestroy();
    }

    private boolean isLowerGarment(String className) {
        String[] lowerGarments = {
                "bikini", "jockstrap", "miniskirt", "pajama", "pantyhose",
                "sarong", "skirt", "slacks", "socks", "stocking",
                "swimming trunks", "trousers", "underpants", "vase", "wool", "jean"
        };

        for (String garment : lowerGarments) {
            if (className.equalsIgnoreCase(garment)) {
                return true;
            }
        }
        return false;
    }

    private boolean isUpperGarment(String className) {
        String[] upperGarments = {
                "academic gown","sweatshirt", "apron", "bandana", "bandanna", "bathrobe",
                "bikini", "binder", "bonnet", "bow tie", "brassiere",
                "bulletproof vest", "cardigan", "cocktail dress", "cravat",
                "diadem", "dinner jacket", "dressing gown", "ear muff",
                "face mask", "feather boa", "fur coat", "gown", "handkerchief",
                "hard hat", "hat", "headband", "hoodie", "jockstrap",
                "kimono", "lab coat", "life jacket", "loafer", "neck brace",
                "nightshirt", "pajama", "sarong", "scarf", "shawl",
                "silk dress", "ski mask", "slipper", "sombrero", "stole",
                "suit", "sunhat", "sunglasses", "tiara", "tunic",
                "turban", "tuxedo", "uniform", "veil", "vest", "jersey"
        };

        for (String garment : upperGarments) {
            if (className.equalsIgnoreCase(garment)) {
                return true;
            }
        }
        return false;
    }
}