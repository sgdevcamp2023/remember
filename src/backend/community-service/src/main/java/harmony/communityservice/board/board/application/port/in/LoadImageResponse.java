package harmony.communityservice.board.board.application.port.in;

import lombok.Getter;

@Getter
public class LoadImageResponse {
    private String image;

    public LoadImageResponse(String image) {
        this.image = image;
    }
}
