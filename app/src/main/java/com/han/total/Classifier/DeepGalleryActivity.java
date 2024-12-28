package com.han.total.Classifier;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.han.total.R;

import java.io.IOException;
import java.util.Locale;

public class DeepGalleryActivity extends AppCompatActivity {
    public static final String TAG = "[IC]GalleryActivity";
    public static final int GALLERY_IMAGE_REQUEST_CODE = 1;
    private ClassifierWithModel cls;
    private ImageView imageView;
    private TextView textView;

//    private Button newButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deepgallery);

        Button selectBtn = findViewById(R.id.deepselectBtn);
        selectBtn.setOnClickListener(v -> getImageFromGallery());

        imageView = findViewById(R.id.deepimageView);
        textView = findViewById(R.id.deeptextView);

        cls = new ClassifierWithModel(this);
        try {
            cls.init();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void getImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
        startActivityForResult(intent, GALLERY_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK &&
                requestCode == GALLERY_IMAGE_REQUEST_CODE) {
            if (data == null) {
                return;
            }

            Uri selectedImage = data.getData();
            Bitmap bitmap = null;

            try {
                if(Build.VERSION.SDK_INT >= 29) {
                    ImageDecoder.Source src =
                            ImageDecoder.createSource(getContentResolver(), selectedImage);
                    bitmap = ImageDecoder.decodeBitmap(src);
                } else {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                }
            } catch (IOException ioe) {
                Log.e(TAG, "Failed to read Image", ioe);
            }

            if(bitmap != null) {
                Pair<String, Float> output = cls.classify(bitmap);
                String resultStr = String.format(Locale.ENGLISH,
                        "class : %s, prob : %.2f%%",
                        output.first, output.second * 100);
                if(isOutterGarment(output.first)){
                    resultStr = "아우터";
                }
                // 여기에서 결과값을 확인하여 특정 단어에 해당한다면 "상의"로 변경
                if(isUpperGarment(output.first)) {
                    resultStr = "상의";
                }

                // 여기에서 결과값을 확인하여 특정 단어에 해당한다면 "하의"로 변경
                if(isLowerGarment(output.first)) {
                    resultStr = "하의";
                }

                textView.setText(output.first+output);
                imageView.setImageBitmap(bitmap);

                // 결과값이 출력되면 newButton을 보이게 설정
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
                "swimming trunks", "trousers", "underpants", "vase", "jean"
        };

        for (String garment : lowerGarments) {
            if (className.equalsIgnoreCase(garment)) {
                return true;
            }
        }
        return false;
    }

    private boolean isOutterGarment(String className){
        String[] outterGarments = {
                "sweatshirt","suit","cardigan","fur coat","academic gown","bathrobe"
        };
        for (String garment : outterGarments) {
            if (className.equalsIgnoreCase(garment)) {
                return true;
            }
        }
        return false;
    }

    private boolean isUpperGarment(String className) {
        String[] upperGarments = {
                "apron", "wool",
                "bikini", "binder", "bonnet", "bow tie", "brassiere",
                "bulletproof vest", "cocktail dress", "cravat",
                "diadem", "dinner jacket", "dressing gown", "gown","hoodie", "jockstrap",
                "kimono", "lab coat", "life jacket", "loafer", "neck brace",
                "nightshirt", "pajama", "sarong", "scarf", "shawl",
                "silk dress", "ski mask", "slipper", "sombrero", "stole", "tiara", "tunic",
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