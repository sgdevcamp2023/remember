package harmony.communityservice.room.adapter.in.web;

import harmony.communityservice.common.annotation.AuthorizeUser;
import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.room.application.port.in.LoadRoomsQuery;
import harmony.communityservice.room.application.port.in.SearchRoomsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AuthorizeUser
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class LoadRoomsController {

    private final LoadRoomsQuery loadRoomsQuery;

    @GetMapping("/search/rooms/{userId}")
    public BaseResponse<?> search(@PathVariable Long userId) {
        SearchRoomsResponse searchRoomsResponse = loadRoomsQuery.loadList(userId);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", searchRoomsResponse);
    }
}
