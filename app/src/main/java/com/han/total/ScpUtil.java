package com.han.total;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class ScpUtil {
    public static void sendFile(String username, String password, String host, int port, String localFilePath, String remoteFilePath) {
        JSch jsch = new JSch();
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        try {
            // 세션 객체 생성
            session = jsch.getSession(username, host, port);
            session.setPassword(password);

            // 호스트 정보를 확인하지 않음
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            // 연결
            session.connect();

            // SFTP 채널 오픈 및 연결
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;

            // 파일 전송
            channelSftp.put(localFilePath, remoteFilePath);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channelSftp != null) {
                channelSftp.exit();
            }
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }

    public static void receiveFile(String username, String password, String host, int port, String remoteFilePath, String localFilePath) {
        JSch jsch = new JSch();
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;

        try {
            // 세션 객체 생성
            session = jsch.getSession(username, host, port);
            session.setPassword(password);

            // 호스트 정보를 확인하지 않음
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            // 연결
            session.connect();

            // SFTP 채널 오픈 및 연결
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;

            // 파일 수신
            channelSftp.get(remoteFilePath, localFilePath);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channelSftp != null) {
                channelSftp.exit();
            }
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }
}
