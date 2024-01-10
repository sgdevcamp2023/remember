package harmony.communityservice.community.query.repository;

public interface UserReadQueryRepository {
    boolean existByUserIdAndGuildId(Long userid, Long guildId);
}
