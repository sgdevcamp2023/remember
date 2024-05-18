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

    public static Image make(String imageUrl) {
        return new Image(imageUrl);
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
