package harmony.communityservice.community.command.service.impl;

import harmony.communityservice.community.command.dto.CategoryRegistrationRequestDto;
import harmony.communityservice.community.command.repository.CategoryCommandRepository;
import harmony.communityservice.community.command.service.CategoryCommandService;
import harmony.communityservice.community.command.service.CategoryReadCommandService;
import harmony.communityservice.community.domain.Category;
import harmony.communityservice.community.domain.Guild;
import harmony.communityservice.community.mapper.ToCategoryMapper;
import harmony.communityservice.community.query.service.GuildQueryService;
import harmony.communityservice.community.query.service.UserReadQueryService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryCommandServiceImpl implements CategoryCommandService {

    private final GuildQueryService guildQueryService;
    private final UserReadQueryService userReadQueryService;
    private final CategoryCommandRepository categoryCommandRepository;
    private final CategoryReadCommandService categoryReadCommandService;
    @Override
    public void save(CategoryRegistrationRequestDto requestDto) {
        userReadQueryService.existsUserIdAndGuildId(requestDto.getUserId(), requestDto.getGuildId());
        Guild findGuild = guildQueryService.findByGuildId(requestDto.getGuildId());
        Category category = ToCategoryMapper.convert(findGuild, requestDto);
        categoryCommandRepository.save(category);
        categoryReadCommandService.save(category, requestDto.getGuildId());
    }
}
