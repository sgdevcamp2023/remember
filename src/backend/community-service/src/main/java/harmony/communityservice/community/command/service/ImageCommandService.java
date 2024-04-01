package harmony.communityservice.community.command.service;

import harmony.communityservice.community.domain.Board;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface ImageCommandService {

    void registerImagesInBoard(List<String> imageUrls, Board board);
}
