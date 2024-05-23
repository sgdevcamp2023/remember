package harmony.communityservice.common.domainentity;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@MappedSuperclass
public abstract class DomainEntity<T extends DomainEntity<T, TID>, TID> {

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamp(3)")
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "timestamp(3)")
    private Instant updatedAt;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "modified_type", columnDefinition = "ENUM('MODIFY', 'NOT_YET') default 'NOT_YET'")
    private ModifiedType type = ModifiedType.NOT_YET;

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        return equals((T) other);
    }

    protected boolean equals(T other) {
        if (other == null) {
            return false;
        }

        if (getId() == null) {
            return false;
        }

        if (other.getClass().equals(getClass())) {
            return getId().equals(other.getId());
        }

        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return getId() == null ? 0 : getId().hashCode();
    }

    abstract public TID getId();

    protected void updateType() {
        this.type = ModifiedType.MODIFY;
    }
}
