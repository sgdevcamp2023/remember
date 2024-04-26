package harmony.communityservice.board.board.domain;

import harmony.communityservice.common.domain.LongTypeIdentifier;
import harmony.communityservice.common.domain.LongTypeIdentifierJavaType;

public class ImageId extends LongTypeIdentifier {
    public ImageId(Long id) {
        super(id);
    }

    public static ImageId make(long id) {
        return new ImageId(id);
    }

    public static class ImageIdJavaType extends LongTypeIdentifierJavaType<ImageId> {
        public ImageIdJavaType() {
            super(ImageId.class);
        }
    }
}
