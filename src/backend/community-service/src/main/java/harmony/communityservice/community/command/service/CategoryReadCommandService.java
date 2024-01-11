package harmony.communityservice.community.command.service;

import harmony.communityservice.community.domain.Category;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CategoryReadCommandService {

    void save(Category category, Long guildId);

    void delete(Long categoryId);
}
