package harmony.communityservice.community.query.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ImagesResponseDto {
    private List<ImageResponseDto> image = new ArrayList<>();

    public ImagesResponseDto(List<ImageResponseDto> image) {
        this.image = image;
    }
}
