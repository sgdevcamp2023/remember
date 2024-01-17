package harmony.communityservice.community.query.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class ImagesResponseDto {
    List<ImageResponseDto> image;

    public ImagesResponseDto(List<ImageResponseDto> image) {
        this.image = image;
    }
}
