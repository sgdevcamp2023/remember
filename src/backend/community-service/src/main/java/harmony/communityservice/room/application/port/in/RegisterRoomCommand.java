package harmony.communityservice.room.application.port.in;

import java.util.List;
import lombok.Builder;

@Builder(toBuilder = true)
public record RegisterRoomCommand(String name,
                                  List<Long> members,
                                  String profile,
                                  Long userId) {
}
