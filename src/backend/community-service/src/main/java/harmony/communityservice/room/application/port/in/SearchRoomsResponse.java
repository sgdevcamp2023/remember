package harmony.communityservice.room.application.port.in;

import harmony.communityservice.room.domain.Room;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class SearchRoomsResponse {
    private List<Room> rooms = new ArrayList<>();

    public SearchRoomsResponse(List<Room> rooms) {
        this.rooms = rooms;
    }
}
