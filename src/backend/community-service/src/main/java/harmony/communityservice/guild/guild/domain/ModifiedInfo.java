package harmony.communityservice.guild.guild.domain;

import harmony.communityservice.common.domain.ValueObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
@Embeddable
public class ModifiedInfo extends ValueObject<ModifiedInfo> {


    @Column(name = "modified_type")
    @Enumerated(value = EnumType.STRING)
    private ModifiedType modifiedType;

    @NotBlank
    @Column(name = "modified_at")
    private String modifiedAt;


    public ModifiedInfo() {
        this.modifiedType = ModifiedType.NOT_YET;
        this.modifiedAt = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private ModifiedInfo(ModifiedType type) {
        this.modifiedType = type;
        this.modifiedAt = LocalDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public ModifiedInfo modify() {
        return new ModifiedInfo(ModifiedType.MODIFY);
    }

    @Override
    protected Object[] getEqualityFields() {
        return new Object[]{modifiedType, modifiedAt};
    }
}
