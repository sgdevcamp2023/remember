package harmony.communityservice.guild.channel.service.query;

import harmony.communityservice.guild.channel.dto.SearchChannelResponse;
import harmony.communityservice.common.dto.SearchParameterMapperRequest;
import java.util.Map;

public interface ChannelQueryService {

    Map<Long, SearchChannelResponse> searchMapByGuildId(SearchParameterMapperRequest searchParameterMapperRequest);
}
