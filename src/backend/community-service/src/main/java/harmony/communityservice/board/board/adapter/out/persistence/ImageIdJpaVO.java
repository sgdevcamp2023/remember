package harmony.communityservice.board.board.adapter.out.persistence;

import harmony.communityservice.common.domainentity.EntityLongTypeIdentifier;
import harmony.communityservice.common.domainentity.EntityLongTypeIdentifierJavaType;

public class ImageIdJpaVO extends EntityLongTypeIdentifier {
    public ImageIdJpaVO(Long id) {
        super(id);
    }

    public static ImageIdJpaVO make(long id) {
        return new ImageIdJpaVO(id);
    }

    public Long getId() {
        return longValue();
    }

    public static class ImageIdJavaType extends EntityLongTypeIdentifierJavaType<ImageIdJpaVO> {
        public ImageIdJavaType() {
            super(ImageIdJpaVO.class);
        }
    }
}
