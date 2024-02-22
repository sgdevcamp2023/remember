package harmony.communityservice.community.query.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ImageResponseDto {
    private String image;

    public ImageResponseDto(String image) {
        this.image = image;
    }
}
