package harmony.communityservice.community.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
@Embeddable
public class ModifiedInfo {

    @NotNull
    @Column(name = "modified")
    private boolean modified;

    @NotBlank
    @Column(name = "modified_at")
    private String modifiedAt;


    public ModifiedInfo() {
        this.modified = false;
        this.modifiedAt = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private ModifiedInfo(boolean modified) {
        this.modified = modified;
        this.modifiedAt = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public ModifiedInfo modify() {
        return new ModifiedInfo(true);
    }
}
