package harmony.communityservice.community.command.controller;

import harmony.communityservice.common.dto.BaseResponse;
import harmony.communityservice.community.command.dto.CategoryDeleteRequestDto;
import harmony.communityservice.community.command.dto.CategoryRegistrationRequestDto;
import harmony.communityservice.community.command.service.CategoryCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/community")
public class CategoryCommandController {

    private final CategoryCommandService categoryCommandService;

    @PostMapping("/registration/category")
    public BaseResponse<?> registration(@RequestBody @Validated CategoryRegistrationRequestDto requestDto) {
        categoryCommandService.save(requestDto);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }

    @DeleteMapping("/delete/category")
    public BaseResponse<?> delete(@RequestBody @Validated CategoryDeleteRequestDto requestDto) {
        categoryCommandService.delete(requestDto);
        return new BaseResponse<>(HttpStatus.OK.value(), "OK");
    }
}
