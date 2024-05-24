package harmony.communityservice.board.comment.domain;


import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.domain.ValueObject;
import lombok.Getter;

@Getter
public class CommonUserInfo extends ValueObject<CommonUserInfo> {
    private final String nickname;
    private final String profile;

    private CommonUserInfo(String nickname, String profile) {
        verifyCommonUserInfo(nickname, profile);
        this.nickname = nickname;
        this.profile = profile;
    }

    static CommonUserInfo make(String nickname, String profile) {
        return new CommonUserInfo(nickname, profile);
    }

    private void verifyCommonUserInfo(String nickname, String profile) {
        if (nickname == null || profile == null) {
            throw new NotFoundDataException("데이터를 찾을 수 없습니다");
        }
    }

    @Override
    protected Object[] getEqualityFields() {
        return new Object[]{nickname, profile};
    }
}
