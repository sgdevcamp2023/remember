package harmony.communityservice.community.query.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record SearchCategoryResponse(Long categoryId, Long guildId, String categoryName) {
}
