package harmony.communityservice.common.event.handler;

import harmony.communityservice.common.event.Events;
import harmony.communityservice.common.event.dto.inner.DeleteChannelEvent;
import harmony.communityservice.common.event.dto.inner.RegisterChannelEvent;
import harmony.communityservice.common.event.dto.produce.ChannelCreatedEvent;
import harmony.communityservice.common.event.dto.produce.ChannelDeletedEvent;
import harmony.communityservice.common.event.mapper.ToChannelCreatedEventMapper;
import harmony.communityservice.common.service.ProducerService;
import harmony.communityservice.guild.channel.dto.RegisterChannelRequest;
import harmony.communityservice.guild.channel.mapper.ToRegisterChannelRequestMapper;
import harmony.communityservice.guild.channel.service.command.ChannelCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
public class ChannelEventHandler {

    private final ChannelCommandService channelCommandService;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = RegisterChannelEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handler(RegisterChannelEvent event) {
        RegisterChannelRequest registerChannelRequest = ToRegisterChannelRequestMapper.convert(event);
        channelCommandService.register(registerChannelRequest);
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = DeleteChannelEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handler(DeleteChannelEvent event) {
        channelCommandService.deleteByGuildId(event.guildId());
    }

}
