package harmony.communityservice.community.command.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class RegisterRoomRequest {
    private String name;
    private List<Long> members = new ArrayList<>();
    private String profile;
}
