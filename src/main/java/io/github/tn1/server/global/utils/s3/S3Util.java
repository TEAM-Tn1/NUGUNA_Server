package io.github.tn1.server.global.utils.s3;

import java.io.IOException;
import java.util.UUID;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import io.github.tn1.server.global.exception.FileEmptyException;
import io.github.tn1.server.global.exception.FileUploadFailException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class S3Util {

    private final AmazonS3 amazonS3;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.s3.base_image_url}")
	private String baseUrl;

    public String upload(MultipartFile file) {
        if(file.isEmpty() && file.getOriginalFilename() != null) throw new FileEmptyException();
        String originalFilename = file.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String randomName = UUID.randomUUID().toString();
        String fileName = randomName + "." + ext;

        try {
            ObjectMetadata objMeta = new ObjectMetadata();
            byte[] bytes = IOUtils.toByteArray(file.getInputStream());
            objMeta.setContentLength(bytes.length);
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), objMeta)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new FileUploadFailException();
        }

        return baseUrl + "/" + fileName;
    }

	public void delete(String objectName) {
		amazonS3.deleteObject(bucket, objectName);
	}

}
