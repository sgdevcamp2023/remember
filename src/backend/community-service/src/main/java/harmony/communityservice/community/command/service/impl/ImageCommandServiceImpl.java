package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.repository.ImageCommandRepository;
import harmony.communityservice.community.command.service.ImageCommandService;
import harmony.communityservice.community.domain.Board;
import harmony.communityservice.community.domain.Image;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ImageCommandServiceImpl implements ImageCommandService {

    private final ImageCommandRepository imageCommandRepository;

    @Override
    public void registerImagesInBoard(List<String> imageUrls, Board board) {
        List<Image> images = imageUrls.stream()
                .map(url -> new Image(board, url)).toList();
        imageCommandRepository.saveAll(images);
    }
}
