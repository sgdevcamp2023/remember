package harmony.communityservice.common.event.handler;

import harmony.communityservice.common.event.dto.inner.DeleteGuildReadEvent;
import harmony.communityservice.common.event.dto.inner.RegisterGuildReadEvent;
import harmony.communityservice.common.event.mapper.ToGuildCreatedEventMapper;
import harmony.communityservice.common.event.mapper.ToGuildDeletedEventMapper;
import harmony.communityservice.common.service.ProducerService;
import harmony.communityservice.guild.guild.dto.RegisterGuildReadRequest;
import harmony.communityservice.guild.guild.mapper.ToRegisterGuildReadRequestMapper;
import harmony.communityservice.guild.guild.service.command.GuildReadCommandService;
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
public class GuildReadEventHandler {

    private final ProducerService producerService;
    private final GuildReadCommandService guildReadCommandService;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = RegisterGuildReadEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handler(RegisterGuildReadEvent event) {
        RegisterGuildReadRequest registerGuildReadRequest = ToRegisterGuildReadRequestMapper.convert(event);
        guildReadCommandService.register(registerGuildReadRequest);
        producerService.publishGuildCreationEvent(ToGuildCreatedEventMapper.convert(event));
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 2000L))
    @TransactionalEventListener(classes = DeleteGuildReadEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handler(DeleteGuildReadEvent event) {
        guildReadCommandService.delete(event.guildId());
        producerService.publishGuildDeletionEvent(ToGuildDeletedEventMapper.convert(event.guildId()));
    }
}
