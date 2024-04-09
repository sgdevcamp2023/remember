package harmony.communityservice.guild.domain;

import harmony.communityservice.common.exception.NotFoundDataException;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
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

@Getter
@Entity
@Table(name = "guild")
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

    @OrderColumn(name = "category_id")
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "categories", joinColumns = @JoinColumn(name = "guild_id"))
    private List<Category> categories;

    @NotBlank
    @Column(name = "invite_code")
    private String inviteCode;

    @Column(name = "manager_id")
    private Long managerId;

    @ElementCollection
    @CollectionTable(name = "guild_user",
            joinColumns = @JoinColumn(name = "guild_id"))
    private Set<Long> userIds = new HashSet<>();

    @Builder
    public Guild(String name, String profile, String inviteCode,
                 Long managerId, Long userId) {
        this.guildInfo = makeGuildInfo(name, profile);
        this.creationTime = new CreationTime();
        this.inviteCode = inviteCode;
        this.managerId = managerId;
        this.userIds = updateUserIds(userId);
        this.categories = new ArrayList<>();
    }

    public Set<Long> updateUserIds(long userId) {
        this.userIds = this.userIds == null ? new HashSet<>() : this.userIds;
        Set<Long> newUserIds = new HashSet<>(this.userIds);
        newUserIds.add(userId);
        return newUserIds;
    }

    private ProfileInfo makeGuildInfo(String name, String profile) {
        return ProfileInfo.make(name, profile);
    }

    public void registerCategory(Category category) {
        this.categories = this.categories == null ? new ArrayList<>() : this.categories;
        ArrayList<Category> newCategories = new ArrayList<>(this.categories);
        newCategories.add(category);
        this.categories = newCategories;
    }

    public void deleteCategory(int categoryId) {
        verifyExistCategory(categoryId);
        this.categories.remove(categoryId);
        this.categories = new ArrayList<>(this.categories);
    }

    public void modifyCategoryName(int categoryId, String newName) {
        verifyExistCategory(categoryId);
        Category category = this.categories.get(categoryId);
        category.modifyName(newName);
        this.categories = new ArrayList<>(this.categories);
    }

    private void verifyExistCategory(int categoryId) {
        if (this.categories.get(categoryId) == null) {
            throw new NotFoundDataException("카테고리가 존재하지 않습니다");
        }
    }
}
