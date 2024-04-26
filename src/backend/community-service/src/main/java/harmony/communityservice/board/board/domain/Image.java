package harmony.communityservice.board.board.domain;

import harmony.communityservice.board.board.domain.ImageId.ImageIdJavaType;
import harmony.communityservice.common.domain.DomainEntity;
import harmony.communityservice.common.domain.ValueObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JavaType;

@Getter
@Entity
@Table(name = "image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends DomainEntity<Image,ImageId> {

    @Id
    @Column(name = "image_id")
    @JavaType(ImageIdJavaType.class)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ImageId imageId;

    @NotBlank
    @Column(name = "image_url")
    private String imageUrl;

    private Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static Image make(String imageUrl) {
        return new Image(imageUrl);
    }

    @Override
    public ImageId getId() {
        return imageId;
    }
}
