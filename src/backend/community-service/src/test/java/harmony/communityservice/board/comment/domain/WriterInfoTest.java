package harmony.communityservice.board.comment.domain;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import harmony.communityservice.common.exception.WrongThresholdRangeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class WriterInfoTest {

    @Test
    @DisplayName("value가 모두 일치할 때 같은 객체로 인식 테스트")
    void same_WriterInfo() {
        WriterInfo firstWriterInfo = WriterInfo.builder()
                .writerId(1L)
                .userName("test")
                .profile("https://cdn.com/test")
                .build();

        WriterInfo secondWriterInfo = WriterInfo.builder()
                .writerId(1L)
                .userName("test")
                .profile("https://cdn.com/test")
                .build();

        boolean equals = firstWriterInfo.equals(secondWriterInfo);

        assertSame(equals, true);
    }

    @Test
    @DisplayName("value가 다를 때 다른 객체로 인식 테스트")
    void different_writerInfo() {
        WriterInfo firstWriterInfo = WriterInfo.builder()
                .writerId(1L)
                .userName("test1")
                .profile("https://cdn.com/test1")
                .build();

        WriterInfo secondWriterInfo = WriterInfo.builder()
                .writerId(2L)
                .userName("test2")
                .profile("https://cdn.com/test2")
                .build();

        boolean equals = firstWriterInfo.equals(secondWriterInfo);

        assertSame(equals, false);
    }

    @ParameterizedTest
    @DisplayName("writerId 범위 테스트")
    @ValueSource(longs = {0L, -1L, -10L, -100L, -1000L})
    void writer_id_range_threshold(long writerId) {
        assertThrows(WrongThresholdRangeException.class, () -> WriterInfo.builder()
                .writerId(writerId)
                .userName("test1")
                .profile("https://cdn.com/test1")
                .build());
    }
}