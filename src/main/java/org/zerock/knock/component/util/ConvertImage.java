package org.zerock.knock.component.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URL;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Base64;

/**
 * @author nks
 * @apiNote Image src 주소를 받아 Base64로 인코딩 하거나, Base64 문자열을 받아 image 반환.
 */
public class ConvertImage {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 이미지를 Base64로 변환하는 유틸리티 메서드.
     * @param imageUrl 이미지의 URL
     * @return Base64로 인코딩된 문자열
     * @throws Exception URL 연결 또는 변환 중 예외 발생 시
     */
    public String convertImageToBase64(String imageUrl) throws Exception {

        logger.info("{} START", getClass().getSimpleName());

        URL requestURL = URI.create(imageUrl).toURL();
        HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        try (InputStream inputStream = connection.getInputStream()) {
            byte[] imageBytes = inputStream.readAllBytes();
            return Base64.getEncoder().encodeToString(imageBytes);
        }


    }

}
