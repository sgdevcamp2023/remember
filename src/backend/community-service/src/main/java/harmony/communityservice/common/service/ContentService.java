package harmony.communityservice.common.service;

import org.springframework.web.multipart.MultipartFile;

public interface ContentService {
    String imageConvertUrl(MultipartFile image);
}
