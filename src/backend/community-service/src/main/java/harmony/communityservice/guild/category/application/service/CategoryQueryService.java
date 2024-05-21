package harmony.communityservice.guild.category.application.service;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.common.annotation.UseCase;
import harmony.communityservice.guild.category.application.port.in.LoadCategoriesQuery;
import harmony.communityservice.guild.category.application.port.in.SearchCategoryResponse;
import harmony.communityservice.guild.category.application.port.in.SearchListCommand;
import harmony.communityservice.guild.category.application.port.out.LoadListPort;
import harmony.communityservice.guild.category.domain.Category;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryQueryService implements LoadCategoriesQuery {

    private final LoadListPort loadListPort;

    @Override
    @AuthorizeGuildMember
    public SearchCategoryResponse loadList(SearchListCommand searchListCommand) {
        return new SearchCategoryResponse(loadListPort.loadList(GuildId.make(searchListCommand.guildId())));
    }
}
