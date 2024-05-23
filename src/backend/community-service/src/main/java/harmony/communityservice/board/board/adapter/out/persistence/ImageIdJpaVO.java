package harmony.communityservice.board.board.adapter.out.persistence;

import harmony.communityservice.common.domainentity.LongTypeIdentifier;
import harmony.communityservice.common.domainentity.LongTypeIdentifierJavaType;

public class ImageIdJpaVO extends LongTypeIdentifier {
    public ImageIdJpaVO(Long id) {
        super(id);
    }

    public static ImageIdJpaVO make(long id) {
        return new ImageIdJpaVO(id);
    }

    public Long getId() {
        return longValue();
    }

    public static class ImageIdJavaType extends LongTypeIdentifierJavaType<ImageIdJpaVO> {
        public ImageIdJavaType() {
            super(ImageIdJpaVO.class);
        }
    }
}
