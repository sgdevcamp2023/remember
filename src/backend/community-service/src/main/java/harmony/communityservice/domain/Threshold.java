package harmony.communityservice.domain;

import lombok.Getter;

@Getter
public enum Threshold {
    MIN(1L);

    private final Long value;

    Threshold(Long value) {
        this.value = value;
    }
}
