# 카프카

### 이벤트마다 Topic을 생성

| Topic | 설명 |
| --- | --- |
| communityChat | 길드 TEXT 채널 채팅을 위한 토픽 |
| directChat | DM 채팅을 위한 토픽 |
| emojiChat | 이모지 채팅을 위한 토픽 |
| connectionEvent | 유저 online, offline 처리를 위한 토픽 |
| sessionEvent | 상태관리 서버로 보내는 유저의 on, off 이벤트를 위한 토픽 |
| communityEvent | 길드 이벤트를 위한 토픽(채널 삭제, 생성, 이름 변경) |
| channelEvent | 음성 채널 이벤트를 위한 토픽(유저의 음성 채널 입, 퇴장 이벤트) |

### 해당 토픽으로 데이터를 보내는 producer 구현

``` java
    public void sendMessageForCommunity(CommunityMessageDto messageDto) {
        log.info("messageDto {}", messageDto.getType());
        kafkaTemplateForCommunity.send(communityChatTopic, messageDto);
    }

    public void sendMessageForDirect(DirectMessageDto messageDto) {
        log.info("messageDto {}", messageDto.getType());
        kafkaTemplateForDirect.send(directChatTopic, messageDto);
    }

    public void sendMessageForEmoji(EmojiDto emojiDto) {
        log.info("emoji {}", emojiDto.getType());
        kafkaTemplateForEmoji.send(emojiChatTopic, emojiDto);
    }

    public void sendMessageForConnectionEvent(ConnectionEventDto connectionEventDto) {
        log.info("ConnectionEvent {}", connectionEventDto.getType());
        kafkaTemplateForConnectionEvent.send(connectionEventTopic, connectionEventDto);
    }

    public void sendMessageForSession(SessionDto sessionDto) {
        log.info("SessionEvent {}", sessionDto.getType());
        kafkaTemplateForSession.send(sessionEventTopic, sessionDto);
    }
```

### Topic마다 Consumer Group을 생성합니다.

| Consumer Group | 설명 |
| --- | --- |
| communityChatGroup | 길드 채팅을 위한 컨슈머 그룹(채팅 서버) |
| directChatGroup | DM 채팅을 위한 컨슈머 그룹(채팅 서버) |
| emojiGroup | 이모지 채팅 위한 컨슈머 그룹(채팅 서버) |
| connectionEventGroup | on, off 상태 이벤트를 위한 컨슈머 그룹(채팅 서버) |
| communityEventGroup | 커뮤니티 이벤트를 위한 컨슈머 그룹(채팅 서버) |
| channelEventGroupChat | 음성 채널 이벤트를 위한 컨슈머 그룹(채팅 서버) |
| sessionEventGroup | on, off 상태 이벤트를 위한 컨슈머 그룹(상태 관리 서버) |
| channelEventGroupState | 음성 채널 이벤트를 위한 컨슈머 그룹(상태 관리 서버) |

### 해당 토픽으로부터 데이터를 가져오는 consumer 구현

``` java

    // 채팅 서버 consumer

    @KafkaListener(topics = "${spring.kafka.topic.community-chat}", groupId = "${spring.kafka.consumer.group-id.community}", containerFactory = "communityListener")
    public void consumeForCommunity(CommunityMessageDto messageDto){
        messagingTemplate.convertAndSend("/topic/guild/" + messageDto.getGuildId(), messageDto);
    }

    @KafkaListener(topics = "${spring.kafka.topic.direct-chat}", groupId = "${spring.kafka.consumer.group-id.direct}", containerFactory = "directListener")
    public void consumeForDirect(DirectMessageDto messageDto){
        messagingTemplate.convertAndSend("/topic/direct/" + messageDto.getRoomId(), messageDto);
    }

    @KafkaListener(topics = "${spring.kafka.topic.emoji-chat}", groupId = "${spring.kafka.consumer.group-id.emoji}", containerFactory = "emojiListener")
    public void consumeForEmoji(EmojiDto emojiDto){
        if (emojiDto.getRoomId() > 0) {
            messagingTemplate.convertAndSend("/topic/direct/" + emojiDto.getRoomId(), emojiDto);
        }
        if (emojiDto.getGuildId() > 0) {
            messagingTemplate.convertAndSend("/topic/guild/" + emojiDto.getGuildId(), emojiDto);
        }
    }

    @KafkaListener(topics = "${spring.kafka.topic.connection-event}", groupId = "${spring.kafka.consumer.group-id.connection-event}", containerFactory = "connectionEventListener")
    public void consumeForConnectionEvent(ConnectionEventDto connectionEventDto) throws JsonProcessingException {
        if (connectionEventDto.getType().equals("CONNECT") || connectionEventDto.getType().equals("DISCONNECT")) {
            HashMap<String,String> stateInfo = new HashMap<>();
            stateInfo.put("state", connectionEventDto.getState());
            stateInfo.put("type", connectionEventDto.getType());
            stateInfo.put("userId", String.valueOf(connectionEventDto.getUserId()));
            String connectionEvent = objectMapper.writeValueAsString(stateInfo);

            if (connectionEventDto.getGuildIds() != null) {
                for (Long guildId : connectionEventDto.getGuildIds()) {
                    messagingTemplate.convertAndSend("/topic/guild/" + guildId, connectionEvent);
                }
            }
            if (connectionEventDto.getRoomIds() != null) {
                for (Long roomId : connectionEventDto.getRoomIds()) {
                    messagingTemplate.convertAndSend("/topic/direct/" + roomId, connectionEvent);
                }
            }
        }
    }

    @KafkaListener(topics = "${spring.kafka.topic.community-event}", groupId = "${spring.kafka.consumer.group-id.community-event}", containerFactory = "communityEventListener")
    public void consumeForCommunityEvent(CommunityEventDto eventDto) throws JsonProcessingException {
        log.info("채널 이벤트 체크 로그 {}", eventDto.toString());
        HashMap<String,String> communityEventInfo = new HashMap<>();
        if (eventDto.getType().equals("CREATE-GUILD")) {
            communityEventInfo.put("guildId", String.valueOf(eventDto.getGuildId()));
            communityEventInfo.put("guildReadId", String.valueOf(eventDto.getGuildReadId()));
            communityEventInfo.put("userId", String.valueOf(eventDto.getUserId()));
            communityEventInfo.put("name", eventDto.getName());
            communityEventInfo.put("profile", eventDto.getProfile());
            communityEventInfo.put("type", "CREATE-GUILD");
        } else if (eventDto.getType().equals("DELETE-GUILD")) {
            communityEventInfo.put("guildId", String.valueOf(eventDto.getGuildId()));
            communityEventInfo.put("type", "DELETE-GUILD");
        } else if (eventDto.getType().equals("CREATE-CHANNEL")) {
            communityEventInfo.put("guildId", String.valueOf(eventDto.getGuildId()));
            communityEventInfo.put("channelName", eventDto.getChannelName());
            communityEventInfo.put("channelType", eventDto.getChannelType());
            communityEventInfo.put("channelReadId", String.valueOf(eventDto.getChannelReadId()));
            communityEventInfo.put("categoryId", String.valueOf(eventDto.getCategoryId()));
            communityEventInfo.put("type", eventDto.getType());
        } else if (eventDto.getType().equals("DELETE-CHANNEL")) {
            communityEventInfo.put("guildId", String.valueOf(eventDto.getGuildId()));
            communityEventInfo.put("channelReadId", String.valueOf(eventDto.getChannelReadId()));
            communityEventInfo.put("type", "DELETE-CHANNEL");
        }
        String communityEvent = objectMapper.writeValueAsString(communityEventInfo);

        messagingTemplate.convertAndSend("/topic/guild/" + eventDto.getGuildId(), communityEvent);
    }

    @KafkaListener(topics = "${spring.kafka.topic.channel-event}", groupId = "${spring.kafka.consumer.group-id.channel-event}", containerFactory = "channelEventListener")
    public void consumeForChannelEvent(ChannelEventDto eventDto) {
        if (eventDto.getType().equals("JOIN") || eventDto.getType().equals("LEAVE")) {
            messagingTemplate.convertAndSend("/topic/guild/" + eventDto.getGuildId(), eventDto);
        }
    }

```

``` java
	 
    // 상태 관리 서버 consumer

    @KafkaListener(topics = "sessionEvent", groupId = "sessionEventGroup", containerFactory = "sessionListener")
    public void consumeForSession(SessionDto sessionDto){
        chatServerService.updateSession(sessionDto);
    }

    @KafkaListener(topics = "channelEvent", groupId = "channelEventGroupState", containerFactory = "channelEventListener")
    public void consumeForChannelEvent(ChannelEventDto channelEventDto) {

        signalingServerService.updateChannelEvent(channelEventDto);
    }
```