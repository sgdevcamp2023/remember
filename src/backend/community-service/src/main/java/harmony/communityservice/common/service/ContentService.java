package harmony.communityservice.common.service;

import org.springframework.web.multipart.MultipartFile;

public interface ContentService {
    String image(MultipartFile image);
}
