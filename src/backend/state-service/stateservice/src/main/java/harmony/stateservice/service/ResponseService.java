package harmony.stateservice.service;

import harmony.stateservice.dto.response.BaseResponse;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {
    public <T> BaseResponse<T> getSuccessResponse(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(200);
        response.setResult(data);
        response.setIsSuccess(true);
        response.setMessage("요청 성공");
        return response;
    }

    public <T> BaseResponse<T> getFailResponse(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(200);
        response.setResult(data);
        response.setIsSuccess(false);
        response.setMessage("요청 실패");
        return response;
    }
}