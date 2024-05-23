package harmony.communityservice.board.comment.domain;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.domain.ValueObject;
import lombok.Builder;
import lombok.Getter;

@Getter
public class WriterInfo extends ValueObject<WriterInfo> {

    private final Long writerId;

    private final CommonUserInfo commonUserInfo;


    @Builder
    public WriterInfo(String userName, String profile, Long writerId) {
        this.commonUserInfo = makeUserInfo(userName, profile);
        this.writerId = writerId;
    }

    private CommonUserInfo makeUserInfo(String userName, String profile) {
        if (userName == null || profile == null) {
            throw new NotFoundDataException();
        }
        return CommonUserInfo.make(userName, profile);
    }
}
