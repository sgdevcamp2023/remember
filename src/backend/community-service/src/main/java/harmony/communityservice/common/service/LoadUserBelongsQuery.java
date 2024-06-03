package harmony.communityservice.common.service;

import harmony.communityservice.common.dto.LoadRoomsAndGuildsResponse;

public interface LoadUserBelongsQuery {
    LoadRoomsAndGuildsResponse load(Long userId);
}
