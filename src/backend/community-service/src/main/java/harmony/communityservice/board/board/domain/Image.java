package harmony.communityservice.board.board.domain;

import harmony.communityservice.board.board.domain.Image.ImageId;
import harmony.communityservice.common.exception.WrongThresholdRangeException;
import harmony.communityservice.domain.Domain;
import harmony.communityservice.domain.Threshold;
import harmony.communityservice.domain.ValueObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Image extends Domain<Image, ImageId> {

    private final String imageUrl;
    private ImageId imageId;

    private Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private Image(String imageUrl, ImageId imageId) {
        verifyImageId(imageId);
        this.imageUrl = imageUrl;
        this.imageId = imageId;
    }

    public static Image make(String imageUrl) {
        return new Image(imageUrl);
    }

    public static Image make(Long imageId, String imageUrl) {
        return new Image(imageUrl, ImageId.make(imageId));
    }

    private void verifyImageId(ImageId imageId) {
        if (imageId != null && imageId.getId() < Threshold.MIN.getValue()) {
            throw new WrongThresholdRangeException("imageId의 범위가 1 미만입니다");
        }
    }


    @Override
    public ImageId getId() {
        return imageId;
    }


    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ImageId extends ValueObject<ImageId> {
        private Long id;

        public static ImageId make(Long imageId) {
            return new ImageId(imageId);
        }
    }
}
