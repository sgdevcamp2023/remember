package harmony.communityservice.community.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.NoArgsConstructor;

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

    @Nullable
    private String profile;

    @NotBlank
    private String nickname;

    @Builder
    public UserRead(Long guildId, Long userId, String profile, String nickname) {
        this.guildId = guildId;
        this.userId = userId;
        this.profile = profile;
        this.nickname = nickname;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfile(String profile){
        this.profile = profile;
    }
}
