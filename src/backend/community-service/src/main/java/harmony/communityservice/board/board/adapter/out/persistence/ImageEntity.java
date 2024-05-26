package harmony.communityservice.board.board.adapter.out.persistence;

import harmony.communityservice.board.board.adapter.out.persistence.ImageIdJpaVO.ImageIdJavaType;
import harmony.communityservice.common.domainentity.DomainEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JavaType;

@Getter
@Entity
@Table(name = "image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageEntity extends DomainEntity<ImageEntity, ImageIdJpaVO> {

    @Id
    @Column(name = "image_id")
    @JavaType(ImageIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ImageIdJpaVO imageId;

    @NotBlank
    @Column(name = "image_url")
    private String imageUrl;

    private ImageEntity(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static ImageEntity make(String imageUrl) {
        return new ImageEntity(imageUrl);
    }

    @Override
    public ImageIdJpaVO getId() {
        return imageId;
    }
}
