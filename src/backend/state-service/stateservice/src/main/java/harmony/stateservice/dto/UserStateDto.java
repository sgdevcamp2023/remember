package harmony.stateservice.dto;

import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserStateDto {

    Map<Long, String> connectionStates;
    Map<String, Set<String>> channelStates;
}
