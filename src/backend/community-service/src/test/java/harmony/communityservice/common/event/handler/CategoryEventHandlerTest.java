package harmony.communityservice.common.event.handler;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.times;

import harmony.communityservice.common.event.dto.inner.DeleteCategoryEvent;
import harmony.communityservice.common.outbox.InnerEventOutBoxMapper;
import harmony.communityservice.common.outbox.InnerEventRecord;
import harmony.communityservice.common.outbox.InnerEventType;
import harmony.communityservice.common.outbox.SentType;
import harmony.communityservice.guild.category.application.port.in.DeleteCategoryUsingGuildIdUseCase;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryEventHandlerTest {

    @Mock
    private InnerEventOutBoxMapper outBoxMapper;

    @Mock
    private DeleteCategoryUsingGuildIdUseCase deleteCategoryUsingGuildIdUseCase;

    private CategoryEventHandler categoryEventHandler;

    @BeforeEach
    void setting() {
        categoryEventHandler = new CategoryEventHandler(outBoxMapper, deleteCategoryUsingGuildIdUseCase);
    }

    @Test
    @DisplayName("카테고리 삭제 이벤트 저장 테스트")
    void delete_category_event_before() {
        DeleteCategoryEvent deleteCategoryEvent = new DeleteCategoryEvent(1L);
        willDoNothing().given(outBoxMapper).insertInnerEventRecord(argThat(record ->
                        record.getGuildId().equals(deleteCategoryEvent.guildId()) &&
                        record.getType().equals(InnerEventType.DELETED_CATEGORY) &&
                        record.getSentType().equals(SentType.INIT)
        ));

        categoryEventHandler.categoryDeleteEventBeforeHandler(deleteCategoryEvent);

        then(outBoxMapper).should(times(1)).insertInnerEventRecord(any(InnerEventRecord.class));
    }

    @Test
    @DisplayName("카테고리 삭제 이벤트 테스트")
    void delete_category_event_after() {
        DeleteCategoryEvent deleteCategoryEvent = new DeleteCategoryEvent(1L);
        InnerEventRecord record = InnerEventRecord.builder()
                .eventId(1L)
                .type(InnerEventType.DELETED_CATEGORY)
                .sentType(SentType.INIT)
                .guildId(deleteCategoryEvent.guildId())
                .build();
        given(outBoxMapper.findInnerEventRecord(any(InnerEventRecord.class))).willReturn(Optional.of(record));
        willDoNothing().given(deleteCategoryUsingGuildIdUseCase).deleteByGuildId(1L);
        willDoNothing().given(outBoxMapper).updateInnerEventRecord(SentType.SEND_SUCCESS,1L);

        categoryEventHandler.categoryDeleteEventAfterHandler(deleteCategoryEvent);

        then(outBoxMapper).should(times(1)).findInnerEventRecord(any(InnerEventRecord.class));
        then(deleteCategoryUsingGuildIdUseCase).should(times(1)).deleteByGuildId(1L);
        then(outBoxMapper).should(times(1)).updateInnerEventRecord(SentType.SEND_SUCCESS,1L);
    }

    @Test
    @DisplayName("카테고리 삭제 이벤트 예외 테스트")
    void delete_category_event_exception() {
        DeleteCategoryEvent deleteCategoryEvent = new DeleteCategoryEvent(1L);
        InnerEventRecord record = InnerEventRecord.builder()
                .eventId(1L)
                .type(InnerEventType.DELETED_CATEGORY)
                .sentType(SentType.INIT)
                .guildId(deleteCategoryEvent.guildId())
                .build();
        given(outBoxMapper.findInnerEventRecord(any(InnerEventRecord.class))).willReturn(Optional.of(record));
        willThrow(RuntimeException.class).given(deleteCategoryUsingGuildIdUseCase).deleteByGuildId(1L);
        willDoNothing().given(outBoxMapper).updateInnerEventRecord(SentType.SEND_FAIL,1L);

        categoryEventHandler.categoryDeleteEventAfterHandler(deleteCategoryEvent);

        then(outBoxMapper).should(times(1)).findInnerEventRecord(any(InnerEventRecord.class));
        then(deleteCategoryUsingGuildIdUseCase).should(times(1)).deleteByGuildId(1L);
        then(outBoxMapper).should(times(1)).updateInnerEventRecord(SentType.SEND_FAIL,1L);
    }
}