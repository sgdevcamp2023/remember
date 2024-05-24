package harmony.communityservice.board.board.domain;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.domain.Threshold;
import harmony.communityservice.domain.ValueObject;
import lombok.Builder;
import lombok.Getter;

@Getter
public class WriterInfo extends ValueObject<WriterInfo> {

    private final Long writerId;

    private final CommonUserInfo commonUserInfo;


    @Builder
    public WriterInfo(String userName, String profile, Long writerId) {
        this.commonUserInfo = makeUserInfo(writerId, userName, profile);
        this.writerId = writerId;
    }

    private CommonUserInfo makeUserInfo(Long writerId, String userName, String profile) {
        if (writerId == null) {
            throw new NotFoundDataException("writerId를 찾을 수 없습니다");
        }

        if (writerId < Threshold.MIN.getValue()) {
            throw new WrongThresholdRangeException("writerId가 1 미만입니다");
        }
        return CommonUserInfo.make(userName, profile);
    }


    @Override
    protected Object[] getEqualityFields() {
        return new Object[]{writerId, commonUserInfo};
    }
}
