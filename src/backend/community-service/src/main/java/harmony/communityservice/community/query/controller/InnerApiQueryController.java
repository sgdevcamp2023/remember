package harmony.communityservice.community.query.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.query.dto.SearchRoomsAndGuildsResponse;
import harmony.communityservice.community.query.service.impl.InnerApiQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class InnerApiQueryController {

    private final InnerApiQueryService innerApiQueryService;

    @GetMapping("/search/rooms/guilds/{userId}")
    public BaseResponse<?> searchRoomsAndGuildsBelongToUser(@PathVariable Long userId) {
        SearchRoomsAndGuildsResponse roomGuildResponseDto = innerApiQueryService.search(userId);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", roomGuildResponseDto);
    }
}
