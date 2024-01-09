package harmony.communityservice.community.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "guild_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuildUser {

    @Id
    @Column(name = "guild_user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "guild_id")
    private Guild guild;

    private GuildUser(User user, Guild guild) {
        this.user = user;
        this.guild = guild;
    }

    public static GuildUser make(User user, Guild guild) {
        return new GuildUser(user, guild);
    }
}
