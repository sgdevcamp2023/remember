package harmony.communityservice.room.domain;

import harmony.communityservice.common.domain.ValueObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
@Embeddable
public class CreationTime extends ValueObject<CreationTime> {

    @NotBlank
    @Column(name = "created_at")
    private String createdAt;

    public CreationTime() {
        this.createdAt = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    protected Object[] getEqualityFields() {
        return new Object[]{createdAt};
    }
}
