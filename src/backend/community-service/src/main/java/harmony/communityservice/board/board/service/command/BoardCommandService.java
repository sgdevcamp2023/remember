package harmony.communityservice.board.board.service.command;

import harmony.communityservice.board.board.dto.DeleteBoardRequest;
import harmony.communityservice.board.board.dto.ModifyBoardRequest;
import harmony.communityservice.board.board.dto.RegisterBoardRequest;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface BoardCommandService {
    void register(RegisterBoardRequest registerBoardRequest, List<MultipartFile> files);

    void modify(ModifyBoardRequest modifyBoardRequest);

    void delete(DeleteBoardRequest deleteBoardRequest);
}
