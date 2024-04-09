package harmony.communityservice.board.board.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class SearchImagesResponse {
    private List<SearchImageResponse> image = new ArrayList<>();

    public SearchImagesResponse(List<SearchImageResponse> image) {
        this.image = image;
    }
}
