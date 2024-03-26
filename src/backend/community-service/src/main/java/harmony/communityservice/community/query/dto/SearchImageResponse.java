package harmony.communityservice.community.query.dto;

import lombok.Getter;

@Getter
public class SearchImageResponse {
    private String image;

    public SearchImageResponse(String image) {
        this.image = image;
    }
}
