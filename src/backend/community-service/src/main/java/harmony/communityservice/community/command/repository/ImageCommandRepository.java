package harmony.communityservice.community.command.repository;

import harmony.communityservice.community.domain.Image;
import java.util.List;

public interface ImageCommandRepository {

    void saveAll(List<Image> images);
}
