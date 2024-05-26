package harmony.communityservice.board.board.application.port.in;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class LoadImageResponse {
    private String image;

    public LoadImageResponse(String image) {
        this.image = image;
    }
}
