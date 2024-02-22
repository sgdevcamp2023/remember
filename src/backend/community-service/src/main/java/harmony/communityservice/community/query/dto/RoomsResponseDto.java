package harmony.communityservice.community.query.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class RoomsResponseDto {
    private List<RoomResponseDto> roomResponseDtos = new ArrayList<>();

    public RoomsResponseDto(List<RoomResponseDto> roomResponseDtos) {
        this.roomResponseDtos = roomResponseDtos;
    }
}
