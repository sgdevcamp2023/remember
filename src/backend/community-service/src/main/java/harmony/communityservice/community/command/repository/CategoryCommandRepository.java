package harmony.communityservice.community.command.repository;

import harmony.communityservice.community.domain.Category;

public interface CategoryCommandRepository {

    void save(Category category);
}
