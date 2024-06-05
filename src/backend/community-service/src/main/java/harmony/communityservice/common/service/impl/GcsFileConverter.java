package harmony.communityservice.common.service.impl;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import harmony.communityservice.common.annotation.UseCase;
import harmony.communityservice.common.exception.IllegalGcsException;
import harmony.communityservice.common.service.FileConverter;
import harmony.communityservice.common.utils.UuidHolder;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

@UseCase
@RequiredArgsConstructor
class GcsFileConverter implements FileConverter {

    private final Storage storage;
    private final UuidHolder uuidHolder;
    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;
    @Value("${google.cloud.storage.url}")
    private String bucketUrl;

    @Override
    public String fileToUrl(MultipartFile image) {
        try {
            String ext = image.getContentType();
            String uuid = uuidHolder.random();
            storage.create(
                    BlobInfo.newBuilder(bucketName, uuid)
                            .setContentType(ext)
                            .build(),
                    image.getBytes());
            return bucketUrl + uuid;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalGcsException();
        }

    }
}
