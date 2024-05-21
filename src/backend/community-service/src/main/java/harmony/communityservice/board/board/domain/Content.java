package harmony.communityservice.board.board.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Content {

    private final String title;

    private final String content;

    @Builder
    public Content(String content, String title) {
        this.content = content;
        this.title = title;
    }
}
