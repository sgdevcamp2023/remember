package harmony.communityservice.common.outbox;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class EnumTypeHandlerTest {

    @Autowired
    private InnerEventOutBoxMapper mapper;

    @Test
    @DisplayName("Mybatis 데이터 저장 테스트")
    void register_event() {
        InnerEventRecord record = InnerEventRecord.builder()
                .channelId(1L)
                .userId(1L)
                .type(InnerEventType.DELETED_BOARD_IN_CHANNEL)
                .sentType(SentType.INIT)
                .build();

        mapper.insertInnerEventRecord(record);
        InnerEventRecord innerEventRecord = mapper.findInnerEventRecord(record).get();

        assertEquals(innerEventRecord.getType(),record.getType());
    }

}