package harmony.communityservice.community.query.controller;

import harmony.communityservice.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class UserQueryController {


    @GetMapping("/check/room/guild/{userId}")
    public BaseResponse<?> search(@PathVariable Long userId) {

    }
}
