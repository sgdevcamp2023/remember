package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.DeleteCategoryRequest;
import harmony.communityservice.community.command.dto.RegisterCategoryRequest;
import harmony.communityservice.community.command.dto.ModifyCategoryRequest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CategoryCommandService {

    void register(RegisterCategoryRequest registerCategoryRequest);

    void delete(DeleteCategoryRequest deleteCategoryRequest);

    void modify(ModifyCategoryRequest modifyCategoryRequest);
}
