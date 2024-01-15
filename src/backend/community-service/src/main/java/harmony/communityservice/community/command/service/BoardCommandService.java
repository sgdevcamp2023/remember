package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.BoardRegistrationRequestDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
public interface BoardCommandService {
    void save(BoardRegistrationRequestDto requestDto, List<MultipartFile> images);
}
