package harmony.communityservice.community.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "user_read")
public class UserRead {

    @Id
    @Column(name = "user_read_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userReadId;

    @NotNull
    @Column(name = "guild_id")
    private Long guildId;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @Embedded
    private CommonUserInfo userInfo;

    @Builder
    public UserRead(Long guildId, Long userId, String profile, String nickname) {
        this.guildId = guildId;
        this.userId = userId;
        this.userInfo = makeCommonUserInfo(profile, nickname);
    }

    private CommonUserInfo makeCommonUserInfo(String profile, String nickname) {
        return CommonUserInfo.make(nickname, profile);
    }

    public void modifyNickname(String nickname) {
        this.userInfo = userInfo.modifyNickname(nickname);
    }

    public void modifyProfile(String profile) {
        this.userInfo = userInfo.modifyProfile(profile);
    }
}
