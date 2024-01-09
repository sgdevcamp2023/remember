package harmony.communityservice.community.query.domain;

import harmony.communityservice.community.command.domain.ChannelType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "channel_query")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChannelQuery {

    @Id
    @Column(name = "channel_query_id")
    private Long channelQueryId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "guild_id")
    private Long guildId;

    @Column(name = "channel_name")
    private String name;

    @Enumerated(EnumType.STRING)
    private ChannelType type;

}
