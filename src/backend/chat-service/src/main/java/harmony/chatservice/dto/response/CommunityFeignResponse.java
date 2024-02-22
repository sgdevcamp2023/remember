package harmony.chatservice.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommunityFeignResponse {

    private int resultCode;
    private String resultMessage;
    private RoomGuildIdsDto resultData;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public class RoomGuildIdsDto {

        private List<Long> roomIds;
        private List<Long> guildIds;
    }
}