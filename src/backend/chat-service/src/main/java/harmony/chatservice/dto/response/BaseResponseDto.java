package harmony.chatservice.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseResponseDto<T> extends CommonResponseDto {

    private T result;
}