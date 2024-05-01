package harmony.communityservice.common.outbox;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OutBoxMapper {
    void insertExternalEventRecord(@Param("record") ExternalEventRecord record);
}
