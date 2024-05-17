package harmony.communityservice.user.adapter.out.persistence;

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
class UserEntity extends AggregateRoot<UserEntity, UserIdJpaVO> {

    @EmbeddedId
    @Column(name = "user_id")
    private UserIdJpaVO userId;

    @Embedded
    private UserInfoJpaVO userInfo;

    @Version
    private Long version;

    @Builder
    public UserEntity(UserIdJpaVO userId, String email, String nickname, String profile) {
        this.userId = userId;
        this.userInfo = makeUserInfo(email, nickname, profile);
    }

    private UserInfoJpaVO makeUserInfo(String email, String nickname, String profile) {
        return UserInfoJpaVO.make(email, nickname, profile);
    }

    @Override
    public UserIdJpaVO getId() {
        return userId;
    }
}
