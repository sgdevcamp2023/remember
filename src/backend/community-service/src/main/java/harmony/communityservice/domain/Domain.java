package harmony.communityservice.domain;

import lombok.Getter;

@Getter
public abstract class Domain<T extends Domain<T, TID>, TID> {

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
}
