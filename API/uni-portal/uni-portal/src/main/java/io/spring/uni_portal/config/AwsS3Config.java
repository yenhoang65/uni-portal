package io.spring.uni_portal.config;

import io.spring.uni_portal.exception.OurException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.InputStream;

@Service
public class AwsS3Config {
    //    private final String bucketName = "phegon-hotel-images";
//    private final String bucketName = "phegons-hotel-images";
    private final String bucketName = "uni-portal-images";
    @Value("${aws.s3.access.key}")
    private String awsS3AccessKey;

    @Value("${aws.s3.secret.key}")
    private String awsS3SecretKey;

    public String saveImageToS3(MultipartFile photo) {
        String s3LocationImage = null;

        try {
            String s3Filename = photo.getOriginalFilename();
            long contentLength = photo.getSize(); // Lấy độ dài nội dung từ MultipartFile
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsS3AccessKey, awsS3SecretKey);
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.AP_SOUTHEAST_2)
                    .build();

            InputStream inputStream = photo.getInputStream();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(contentLength);
            metadata.setContentType("image/jpeg");

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3Filename, inputStream, metadata);
            s3Client.putObject(putObjectRequest);
            return "https://" + bucketName + ".s3.amazonaws.com/" + s3Filename;

        } catch (Exception e) {
            e.printStackTrace();
            throw new OurException("Không thể tải hình ảnh lên thùng s3" + e.getMessage());
        }
    }
}
