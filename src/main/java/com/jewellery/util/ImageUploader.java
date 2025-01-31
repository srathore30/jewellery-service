package com.jewellery.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.jewellery.constant.ApiErrorCodes;
import com.jewellery.exception.NoSuchElementFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

@Service
public class ImageUploader {
    @Value("${aws.bucket}")
    private String bucketName;

    private final AmazonS3 amazonS3;
    public final String baseUrl = "https://jwellery-bucket.s3.ap-south-1.amazonaws.com/";

    @Autowired
    public ImageUploader(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }


    public String uploadFile(String base64Data){
        byte[] fileBytes = Base64Decoder.getImageByte(base64Data);
        InputStream fileStream = Base64Decoder.convertBase64Image(base64Data);
        String key = System.currentTimeMillis() + ".jpg";
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(fileBytes.length);
        metadata.setContentType("image/jpeg");
        amazonS3.putObject(bucketName, key, fileStream, metadata);
        return baseUrl + key;
    }

    public void deleteImage(String base64Url){
        try {
            if (!Objects.equals(base64Url, "")) {
                URL url = new URL(base64Url);
                String key = url.getPath().substring(1);
                amazonS3.deleteObject(bucketName, key);
            }
        }catch (MalformedURLException malformedURLException){
            throw new NoSuchElementFoundException(ApiErrorCodes.INVALID_INPUT.getErrorCode(), ApiErrorCodes.INVALID_INPUT.getErrorMessage());
     }
    }
}