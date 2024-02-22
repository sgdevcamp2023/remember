package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.Category;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CategoryQueryService {

    Category findByCategoryId(Long categoryId);
}
