package harmony.communityservice.guild.category.application.port.in;

public interface DeleteCategoryUsingGuildIdUseCase {
    void deleteByGuildId(Long guildId);
}
