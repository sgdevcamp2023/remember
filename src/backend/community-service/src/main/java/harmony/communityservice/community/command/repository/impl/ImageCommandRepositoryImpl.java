package harmony.communityservice.community.command.repository.impl;

import harmony.communityservice.community.command.repository.ImageCommandRepository;
import harmony.communityservice.community.command.repository.jpa.JpaImageCommandRepository;
import harmony.communityservice.community.domain.Image;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ImageCommandRepositoryImpl implements ImageCommandRepository {

    private final JpaImageCommandRepository jpaImageCommandRepository;

    @Override
    public void saveAll(List<Image> images) {
        jpaImageCommandRepository.saveAll(images);
    }
}
