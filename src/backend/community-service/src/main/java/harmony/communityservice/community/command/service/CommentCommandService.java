package harmony.communityservice.community.command.service;

import harmony.communityservice.community.command.dto.DeleteCommentRequest;
import harmony.communityservice.community.command.dto.RegisterCommentRequest;
import harmony.communityservice.community.command.dto.ModifyCommentRequest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CommentCommandService {

    void register(RegisterCommentRequest registerCommentRequest);

    void modify(ModifyCommentRequest modifyCommentRequest);

    void delete(DeleteCommentRequest deleteCommentRequest);
}
