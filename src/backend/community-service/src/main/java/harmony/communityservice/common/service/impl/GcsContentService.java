package harmony.communityservice.common.service.impl;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import harmony.communityservice.common.exception.IllegalGcsException;
import harmony.communityservice.common.service.ContentService;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class GcsContentService implements ContentService {

    private final Storage storage;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    @Value("${google.cloud.storage.url}")
    private String bucketUrl;

    @Override
    public String convertFileToUrl(MultipartFile image) {
        try {
            String uuid = UUID.randomUUID().toString();
            String ext = image.getContentType();
            BlobInfo blobInfo = storage.create(
                    BlobInfo.newBuilder(bucketName, uuid)
                            .setContentType(ext)
                            .build(),
                    image.getInputStream()
            );
            return bucketUrl + uuid;
        } catch (IOException e) {
            throw new IllegalGcsException();
        }

    }
}
