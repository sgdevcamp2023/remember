package harmony.communityservice.common.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import harmony.communityservice.common.dto.LoadRoomsAndGuildsResponse;
import harmony.communityservice.guild.guild.application.port.in.LoadGuildIdsQuery;
import harmony.communityservice.guild.guild.domain.Guild.GuildId;
import harmony.communityservice.room.application.port.in.LoadRoomIdsQuery;
import harmony.communityservice.user.domain.User.UserId;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InnerApiQueryServiceTest {

    @Mock
    private LoadRoomIdsQuery loadRoomIdsQuery;
    @Mock
    private LoadGuildIdsQuery loadGuildIdsQuery;
    private InnerApiQueryService innerApiQueryService;

    @BeforeEach
    void setting() {
        innerApiQueryService = new InnerApiQueryService(loadRoomIdsQuery, loadGuildIdsQuery);
    }

    @Test
    @DisplayName("유저가 속한 Dm 룸 ID와 길드 ID 조회 테스트")
    void load_user_belongs() {
        List<GuildId> guildIds = List.of(GuildId.make(1L), GuildId.make(1L), GuildId.make(1L));
        List<Long> roomIds = List.of(1L, 2L, 3L);
        List<Long> ids = guildIds.stream()
                .map(GuildId::getId)
                .toList();
        given(loadGuildIdsQuery.loadGuildIdsByUserId(UserId.make(1L))).willReturn(guildIds);
        given(loadRoomIdsQuery.loadRoomIds(1L)).willReturn(roomIds);

        LoadRoomsAndGuildsResponse loadRoomsAndGuildsResponse = innerApiQueryService.load(1L);

        assertEquals(roomIds, loadRoomsAndGuildsResponse.getRoomIds());
        assertEquals(ids, loadRoomsAndGuildsResponse.getGuildIds());
        then(loadGuildIdsQuery).should(times(1)).loadGuildIdsByUserId(UserId.make(1L));
        then(loadRoomIdsQuery).should(times(1)).loadRoomIds(1L);


    }
}