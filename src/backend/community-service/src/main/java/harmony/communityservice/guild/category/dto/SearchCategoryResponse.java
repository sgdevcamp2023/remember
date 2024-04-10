package harmony.communityservice.guild.category.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record SearchCategoryResponse(Long categoryId, Long guildId, String categoryName) {
}
