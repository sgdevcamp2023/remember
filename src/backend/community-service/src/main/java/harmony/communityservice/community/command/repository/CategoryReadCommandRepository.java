package harmony.communityservice.community.command.repository;

import harmony.communityservice.community.domain.CategoryRead;

public interface CategoryReadCommandRepository {
    void save(CategoryRead categoryRead);
}
