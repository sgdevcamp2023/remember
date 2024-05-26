package harmony.communityservice.guild.category.application.service;


import harmony.communityservice.domain.Threshold;
import harmony.communityservice.guild.category.application.port.in.RegisterCategoryCommand;
import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.category.domain.Category.CategoryId;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;

class CategoryMapper {

    public static Category convert(RegisterCategoryCommand registerCategoryCommand) {
        return Category.builder()
                .categoryId(CategoryId.make(Threshold.MIN.getValue()))
                .name(registerCategoryCommand.name())
                .guildId(GuildId.make(registerCategoryCommand.guildId()))
                .build();
    }
}
