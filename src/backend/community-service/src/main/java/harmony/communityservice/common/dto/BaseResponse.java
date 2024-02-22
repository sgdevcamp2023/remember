package harmony.communityservice.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;

@Getter
@JsonInclude(Include.NON_NULL)
public class BaseResponse<T> {

    private int resultCode;
    private String resultMessage;
    private T resultData;

    public BaseResponse(int resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public BaseResponse(int resultCode, String resultMessage, T resultData) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.resultData = resultData;
    }
}
