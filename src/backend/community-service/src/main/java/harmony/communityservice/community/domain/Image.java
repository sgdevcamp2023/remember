package harmony.communityservice.community.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@Entity
@Table(name = "image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @Column(name = "image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "board_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Board board;

    @NotBlank
    @Column(name = "image_addr")
    private String imageAddr;

    @Builder
    public Image(Board board, String imageAddr) {
        this.board = board;
        this.imageAddr = imageAddr;
    }
}
