package harmony.communityservice.guild.category.application.service;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.annotation.UseCase;
import harmony.communityservice.guild.category.application.port.in.LoadCategoriesQuery;
import harmony.communityservice.guild.category.application.port.in.LoadCategoryResponse;
import harmony.communityservice.guild.category.application.port.in.LoadListCommand;
import harmony.communityservice.guild.category.application.port.out.LoadListPort;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryQueryService implements LoadCategoriesQuery {

    private final LoadListPort loadListPort;

    @Override
    @AuthorizeGuildMember
    public LoadCategoryResponse loadList(LoadListCommand searchListCommand) {
        return new LoadCategoryResponse(loadListPort.loadList(GuildId.make(searchListCommand.guildId())));
    }
}
