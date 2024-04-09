package harmony.communityservice.board.board.dto;

import lombok.Getter;

@Getter
public class SearchImageResponse {
    private String image;

    public SearchImageResponse(String image) {
        this.image = image;
    }
}
