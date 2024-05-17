package harmony.communityservice.common.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileConverter {
    String fileToUrl(MultipartFile file);
}
