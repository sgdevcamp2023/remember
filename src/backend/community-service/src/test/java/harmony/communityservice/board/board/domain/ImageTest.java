package harmony.communityservice.board.board.domain;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import harmony.communityservice.common.exception.WrongThresholdRangeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ImageTest {

    @Test
    @DisplayName("같은 식별자면 같은 객체로 인식 테스트")
    void same_image() {
        Image firstImage = Image.make(1L, "https://cdn.com/test1");
        Image secondImage = Image.make(1L, "https://cdn.com/test2");

        boolean equals = firstImage.equals(secondImage);

        assertSame(true, equals);
    }

    @Test
    @DisplayName("다른 식별자면 다른 객체로 인식 테스트")
    void different_image() {
        Image firstImage = Image.make(1L, "https://cdn.com/test");
        Image secondImage = Image.make(2L, "https://cdn.com/test");

        boolean equals = firstImage.equals(secondImage);

        assertSame(false, equals);
    }

    @ParameterizedTest
    @DisplayName("imageId 범위 테스트")
    @ValueSource(longs = {-1L, 0L, -100L, -10000L, -1000L})
    void image_id_range_threshold(long imageId) {
        assertThrows(WrongThresholdRangeException.class, () -> Image.make(imageId, "https://cdn.com/test"));
    }
}