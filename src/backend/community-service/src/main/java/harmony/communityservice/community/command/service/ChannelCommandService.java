package harmony.communityservice.community.command.service;

import harmony.communityservice.common.annotation.AuthorizeGuildMember;
import harmony.communityservice.community.command.dto.DeleteChannelRequest;
import harmony.communityservice.community.command.dto.RegisterChannelRequest;
import org.springframework.transaction.annotation.Transactional;

public interface ChannelCommandService {

    Long register(RegisterChannelRequest registerChannelRequest);

    void delete(DeleteChannelRequest deleteChannelRequest);
}
