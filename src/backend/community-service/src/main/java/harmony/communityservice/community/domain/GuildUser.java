package harmony.communityservice.community.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "guild_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuildUser {

    @Id
    @Column(name = "guild_user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne
    @JoinColumn(name = "guild_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Guild guild;

    private GuildUser(User user, Guild guild) {
        this.user = user;
        this.guild = guild;
    }

    public static GuildUser make(User user, Guild guild) {
        return new GuildUser(user, guild);
    }
}
