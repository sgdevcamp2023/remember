package harmony.chatservice.service;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {

    @Value("${spring.cloud.gcp.storage.bucket}") // application.yml에 써둔 bucket 이름
    private String bucketName;

    @Value("${file.dir}")
    private String fileDir;

    private final static String SEP = "/";
    private final static String DOT = ".";

    private final Storage storage;

    public String createFullPath(String filename) {
        return fileDir + bucketName + SEP + filename;
    }

    public List<String> uploadFile(List<MultipartFile> files) throws IOException {
        List<String> uploadFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            String uuid = UUID.randomUUID().toString(); // Google Cloud Storage에 저장될 파일 이름
            String ext = file.getContentType(); // 파일의 형식 ex) JPG
            String filename = uuid + DOT + ext;

            // Cloud에 이미지 업로드
            BlobInfo blobInfo = storage.create(
                    BlobInfo.newBuilder(bucketName, filename)
                            .setContentType(ext)
                            .build(),
                    file.getInputStream()
            );

            uploadFiles.add(createFullPath(filename));
        }
        return uploadFiles;
    }
}
