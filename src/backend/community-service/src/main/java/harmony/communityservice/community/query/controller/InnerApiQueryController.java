package harmony.communityservice.community.query.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.query.dto.RoomGuildResponseDto;
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

    @GetMapping("/check/room/guild/{userId}")
    public BaseResponse<?> search(@PathVariable Long userId) {
        RoomGuildResponseDto roomGuildResponseDto = innerApiQueryService.search(userId);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK", roomGuildResponseDto);
    }
}
