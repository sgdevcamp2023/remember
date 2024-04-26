package harmony.communityservice.guild.category.mapper;


import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.category.dto.RegisterCategoryRequest;
import harmony.communityservice.guild.guild.domain.GuildId;

public class ToCategoryMapper {

    public static Category convert(RegisterCategoryRequest registerCategoryRequest) {
        return Category.builder()
                .name(registerCategoryRequest.name())
                .guildId(GuildId.make(registerCategoryRequest.guildId()))
                .build();
    }
}
