package harmony.communityservice.community.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WriterInfo {

    @NotBlank
    @Column(name = "writer_name")
    private String writerName;

    @NotBlank
    @Column(name = "writer_profile")
    private String writerProfile;

    @NotNull
    @Column(name = "writer_id")
    private Long writerId;


    public static WriterInfo make(String name, Long id, String profile) {
        return new WriterInfo(name, profile, id);
    }

    public void verifyWriter(Long writerId) {
        if (!this.writerId.equals(writerId)) {
            throw new IllegalStateException("Wrong Writer");
        }
    }

}
