package harmony.communityservice.common.outbox;

import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ExternalEventOutBoxMapper {
    void insertExternalEventRecord(@Param("record") ExternalEventRecord record);
    Optional<ExternalEventRecord> findExternalEventRecord(@Param("record") ExternalEventRecord record);
    void updateExternalEventRecord(@Param("sentType") SentType sentType, @Param("eventId") Long eventId);
}
