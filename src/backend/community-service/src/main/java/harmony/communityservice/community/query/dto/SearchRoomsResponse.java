package harmony.communityservice.community.query.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class SearchRoomsResponse {
    private List<SearchRoomResponse> searchRoomResponses = new ArrayList<>();

    public SearchRoomsResponse(List<SearchRoomResponse> searchRoomResponses) {
        this.searchRoomResponses = searchRoomResponses;
    }
}
