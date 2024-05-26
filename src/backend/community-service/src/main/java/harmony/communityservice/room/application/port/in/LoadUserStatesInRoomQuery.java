package harmony.communityservice.room.application.port.in;

import java.util.Map;

public interface LoadUserStatesInRoomQuery {

    Map<Long, LoadUserStateResponse> loadUserStates(Long dmId);
}
