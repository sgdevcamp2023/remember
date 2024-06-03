package harmony.communityservice.board.board.adapter.out.persistence;

import harmony.communityservice.common.domainentity.EntityValueObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentJpaVO extends EntityValueObject<ContentJpaVO> {

    @NotBlank
    @Column(name = "title")
    private String title;

    @NotBlank
    @Column(name = "content")
    private String content;

    public static ContentJpaVO make(String title, String content) {
        return new ContentJpaVO(title, content);
    }
}
