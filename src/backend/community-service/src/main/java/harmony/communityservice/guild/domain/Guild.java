package harmony.communityservice.guild.domain;

import harmony.communityservice.common.exception.NotFoundDataException;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@Table(name = "guild", indexes = @Index(name = "idx__invite_code", columnList = "invite_code"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Guild {

    @Id
    @Column(name = "guild_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guildId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "guild_name")),
            @AttributeOverride(name = "profile", column = @Column(name = "guild_profile"))
    })
    private ProfileInfo guildInfo;

    @Embedded
    private CreationTime creationTime;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "guild_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private List<Category> categories = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "guild_id", foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private List<Channel> channels = new ArrayList<>();

    @NotBlank
    @Column(name = "invite_code")
    private String inviteCode;

    @Column(name = "manager_id")
    private Long managerId;

    @ElementCollection
    @CollectionTable(name = "guild_user", joinColumns = @JoinColumn(name = "guild_id"))
    private Set<Long> userIds = new HashSet<>();

    @Builder
    public Guild(String name, String profile, String inviteCode,
                 Long managerId) {
        this.guildInfo = makeGuildInfo(name, profile);
        this.creationTime = new CreationTime();
        this.inviteCode = inviteCode;
        this.managerId = managerId;
        this.categories = new ArrayList<>();
        this.channels = new ArrayList<>();
    }

    public void updateUserIds(long userId) {
        this.userIds = this.userIds == null ? new HashSet<>() : this.userIds;
        this.userIds.add(userId);
        this.userIds = new HashSet<>(this.userIds);
    }

    private ProfileInfo makeGuildInfo(String name, String profile) {
        return ProfileInfo.make(name, profile);
    }

    public void registerChannel(Channel channel) {
        this.channels.add(channel);
    }

    public void registerCategory(Category category) {
        this.categories.add(category);
    }

    public void deleteChannel(Long channelId) {
        Channel channel = verifyExistChannel(channelId);
        this.channels.remove(channel);
    }


    public void deleteCategory(Long categoryId) {
        Category category = verifyExistCategory(categoryId);
        this.categories.remove(category);
    }

    public void modifyCategoryName(Long categoryId, String newName) {
        Category category = verifyExistCategory(categoryId);
        category.modifyName(newName);
    }

    private Category verifyExistCategory(Long categoryId) {
        for (Category category : this.categories) {
            if (category.getCategoryId().equals(categoryId)) {
                return category;
            }
        }
        throw new NotFoundDataException("카테고리가 존재하지 않습니다");
    }

    private Channel verifyExistChannel(Long channelId) {
        for (Channel channel : this.channels) {
            if (channel.getChannelId().equals(channelId)) {
                return channel;
            }
        }
        throw new NotFoundDataException("채널이 존재하지 않습니다");
    }
}
