package harmony.communityservice.community.query.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.query.dto.RoomsResponseDto;
import harmony.communityservice.community.query.service.RoomQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class RoomQueryController {

    private final RoomQueryService roomQueryService;

    @GetMapping("/check/room/{userId}")
    public BaseResponse<?> search(@PathVariable Long userId) {
        RoomsResponseDto roomsResponseDto = roomQueryService.searchRoom(userId);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", roomsResponseDto);
    }
}
