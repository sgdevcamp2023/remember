package harmony.communityservice.common.outbox;

import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InnerEventOutBoxMapper {
    void insertInnerEventRecord(@Param("record") InnerEventRecord innerEventRecord);
    Optional<InnerEventRecord> findInnerEventRecord(@Param("record") InnerEventRecord record);
    void updateInnerEventRecord(@Param("sentType") SentType sentType, @Param("eventId") Long eventId);
}
