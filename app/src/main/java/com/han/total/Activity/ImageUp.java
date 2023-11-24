package com.han.total.Activity;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ImageUp {
    public void uploadImageToServer(String imagePath) {
        JSch jsch = new JSch();
        Session session = null;

        try {
            // SSH 연결 설정
            String sshHost = "http://14.63.223.16";
            String sshUser = "kmong";
            String sshPassword = "kmong";
            int sshPort = 22;

            session = jsch.getSession(sshUser, sshHost, sshPort);
            session.setPassword(sshPassword);

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            // SSH 연결
            session.connect();

            // SCP로 이미지 파일 전송
            String remoteDirectory = "/test/img/"; // 리눅스 서버의 원격 디렉토리
            String remoteFileName = "uploaded_image.jpg"; // 서버에 저장될 파일 이름

            Channel channel = session.openChannel("sftp");
            channel.connect();

            com.jcraft.jsch.ChannelSftp sftpChannel = (com.jcraft.jsch.ChannelSftp) channel;

            File localFile = new File(imagePath);
            FileInputStream fileInputStream = new FileInputStream(localFile);
            sftpChannel.put(fileInputStream, remoteDirectory + remoteFileName);

            fileInputStream.close();
            sftpChannel.exit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }
}
