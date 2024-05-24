package harmony.communityservice.board.comment.domain;

import static harmony.communityservice.domain.Threshold.MIN;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.domain.ValueObject;
import lombok.Builder;
import lombok.Getter;

@Getter
public class WriterInfo extends ValueObject<WriterInfo> {

    private final Long writerId;

    private final CommonUserInfo commonUserInfo;


    @Builder
    public WriterInfo(String userName, String profile, Long writerId) {
        if (writerId == null) {
            throw new NotFoundDataException("writerId를 찾을 수 없습니다");
        }
        if (writerId < MIN.getValue()) {
            throw new WrongThresholdRangeException("writerId의 범위가 1 미만입니다.");
        }
        this.commonUserInfo = makeUserInfo(userName, profile);
        this.writerId = writerId;
    }

    private CommonUserInfo makeUserInfo(String userName, String profile) {
        if (userName == null || profile == null) {
            throw new NotFoundDataException();
        }
        return CommonUserInfo.make(userName, profile);
    }

    @Override
    protected Object[] getEqualityFields() {
        return new Object[]{writerId, commonUserInfo};
    }
}
