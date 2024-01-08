package harmony.communityservice.community.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    private String email;

    @NotBlank
    private String nickname;

    @NotBlank
    private String profile;

    @OneToMany(mappedBy = "user")
    private List<GuildUser> guildUsers;

    @Builder
    public User(Long userId, String email, String nickname, String profile) {
        this.userId = userId;
        this.email = email;
        this.nickname = nickname;
        this.profile = profile;
    }

}
