package harmony.communityservice.guild.category.application.port.in;

import harmony.communityservice.guild.category.domain.Category;
import java.util.List;
import lombok.Builder;

@Builder(toBuilder = true)
public record LoadCategoryResponse(List<Category> categories) {
}
