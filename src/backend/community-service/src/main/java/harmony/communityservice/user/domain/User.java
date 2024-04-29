package harmony.communityservice.user.domain;

import harmony.communityservice.common.domain.AggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AggregateRoot<User, UserId> {

    @EmbeddedId
    @Column(name = "user_id")
    private UserId userId;

    @Embedded
    private UserInfo userInfo;

    @Version
    private Long version;

    @Builder
    public User(UserId userId, String email, String nickname, String profile) {
        this.userId = userId;
        this.userInfo = makeUserInfo(email, nickname, profile);
    }

    private UserInfo makeUserInfo(String email, String nickname, String profile) {
        return UserInfo.make(email, nickname, profile);
    }

    public void modifyProfile(String profile) {
        this.userInfo = this.userInfo.modifyProfile(profile);
        super.updateType();
    }

    public void modifyNickname(String nickname) {
        this.userInfo = this.userInfo.modifyNickname(nickname);
        super.updateType();
    }

    @Override
    public UserId getId() {
        return userId;
    }
}
