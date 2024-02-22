package harmony.communityservice.community.command.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class RoomRegistrationRequestDto {
    private String name;
    private List<Long> members = new ArrayList<>();
    private String profile;
}
