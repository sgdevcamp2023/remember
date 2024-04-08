package harmony.communityservice.community.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @NotBlank
    @Column(name = "image_url")
    private String imageUrl;

    private Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static Image make(String imageUrl) {
        return new Image(imageUrl);
    }
}
