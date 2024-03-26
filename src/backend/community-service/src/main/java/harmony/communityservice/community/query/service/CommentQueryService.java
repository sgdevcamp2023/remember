package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.Comment;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CommentQueryService {


    Comment searchById(Long commentId);
}
