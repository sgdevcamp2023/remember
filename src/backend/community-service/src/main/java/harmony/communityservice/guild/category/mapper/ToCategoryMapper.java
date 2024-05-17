package harmony.communityservice.guild.category.mapper;


import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.category.dto.RegisterCategoryRequest;
import harmony.communityservice.guild.guild.adapter.out.persistence.GuildIdJpaVO;

public class ToCategoryMapper {

    public static Category convert(RegisterCategoryRequest registerCategoryRequest) {
        return Category.builder()
                .name(registerCategoryRequest.name())
                .guildId(GuildIdJpaVO.make(registerCategoryRequest.guildId()))
                .build();
    }
}
