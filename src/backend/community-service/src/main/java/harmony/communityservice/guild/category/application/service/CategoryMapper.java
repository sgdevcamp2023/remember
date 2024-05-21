package harmony.communityservice.guild.category.application.service;


import harmony.communityservice.guild.category.application.port.in.RegisterCategoryCommand;
import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;

class CategoryMapper {

    public static Category convert(RegisterCategoryCommand registerCategoryCommand) {
        return Category.builder()
                .name(registerCategoryCommand.name())
                .guildId(GuildId.make(registerCategoryCommand.guildId()))
                .build();
    }
}
