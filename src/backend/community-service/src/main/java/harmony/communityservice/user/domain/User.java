package harmony.communityservice.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Embedded
    private UserInfo userInfo;

    @Builder
    public User(Long userId, String email, String nickname, String profile) {
        this.userId = userId;
        this.userInfo = makeUserInfo(email, nickname, profile);
    }

    private UserInfo makeUserInfo(String email, String nickname, String profile) {
        return UserInfo.make(email, nickname, profile);
    }

    public void modifyProfile(String profile) {
        this.userInfo = this.userInfo.modifyProfile(profile);
    }

    public void modifyNickname(String nickname) {
        this.userInfo = this.userInfo.modifyNickname(nickname);
    }

}
