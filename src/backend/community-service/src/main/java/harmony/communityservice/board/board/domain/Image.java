package harmony.communityservice.board.board.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Image {

    private final String imageUrl;
    private ImageId imageId;

    private Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private Image(String imageUrl, ImageId imageId) {
        this.imageUrl = imageUrl;
        this.imageId = imageId;
    }

    public static Image make(String imageUrl) {
        return new Image(imageUrl);
    }

    public static Image make(Long imageId, String imageUrl) {
        return new Image(imageUrl, ImageId.make(imageId));
    }


    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ImageId {
        private Long id;

        public static ImageId make(Long imageId) {
            return new ImageId(imageId);
        }
    }
}
