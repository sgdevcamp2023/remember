package harmony.communityservice.board.board.domain;

import harmony.communityservice.common.exception.NotFoundDataException;
import harmony.communityservice.domain.ValueObject;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Content extends ValueObject<Content> {

    private final String title;

    private final String content;

    @Builder
    public Content(String content, String title) {
        verifyContent(content, title);
        this.content = content;
        this.title = title;
    }

    private void verifyContent(String content, String title) {
        if (content == null || title == null) {
            throw new NotFoundDataException("데이터가 존재하지 않습니다");
        }
    }
}
