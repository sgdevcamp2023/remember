package harmony.communityservice.board.board.domain;

import harmony.communityservice.domain.ValueObject;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Content extends ValueObject<Content> {

    private final String title;

    private final String content;

    @Builder
    public Content(String content, String title) {
        this.content = content;
        this.title = title;
    }

    @Override
    protected Object[] getEqualityFields() {
        return new Object[]{title, content};
    }
}
