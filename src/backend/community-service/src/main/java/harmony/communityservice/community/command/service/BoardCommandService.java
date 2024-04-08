package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.DeleteBoardRequest;
import harmony.communityservice.community.command.dto.RegisterBoardRequest;
import harmony.communityservice.community.command.dto.ModifyBoardRequest;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
public interface BoardCommandService {
    void register(RegisterBoardRequest registerBoardRequest, List<MultipartFile> files);

    void modify(ModifyBoardRequest modifyBoardRequest);

    void delete(DeleteBoardRequest deleteBoardRequest);
}
