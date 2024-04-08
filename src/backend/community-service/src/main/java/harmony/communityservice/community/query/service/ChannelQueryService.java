package harmony.communityservice.community.query.service;

import harmony.communityservice.community.domain.Channel;
import harmony.communityservice.community.query.dto.SearchChannelResponse;
import harmony.communityservice.community.query.dto.SearchParameterMapperRequest;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;

public interface ChannelQueryService {

    Map<Long, SearchChannelResponse> searchMapByGuildId(SearchParameterMapperRequest searchParameterMapperRequest);
}
