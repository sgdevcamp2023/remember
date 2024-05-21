package harmony.communityservice.board.board.application.port.in;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface RegisterBoardUseCase {

    void register(RegisterBoardCommand registerBoardCommand, List<MultipartFile> files);
}
