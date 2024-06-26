package harmony.chatservice.exception;

import lombok.Getter;

@Getter
public enum ExceptionStatus {

    DATA_NOT_FOUND(2004,  "Not Found", "삭제 또는 수정하려는 데이터를 찾을 수 없습니다."),

    FILE_UPLOAD_EXCEEDED(2005,  "File Count Error", "파일 업로드 개수는 10개 이하여야 합니다."),

    FILE_SIZE_EXCEEDED(2006, "File Size Error","업로드 할 수 있는 파일의 최대 크기는 15MB 입니다."),

    FILE_UPLOAD_FAILED(2007, "Fail File Upload", "파일 업로드에 실패했습니다."),

    USER_CONNECTION_ERROR(2008,  "Error Connection", "존재 하지 않는 유저의 접속 체크 입니다.");

    private final int statusCode;
    private final String message;
    private final String desc;

    ExceptionStatus(int statusCode, String message, String desc) {
        this.statusCode = statusCode;
        this.message = message;
        this.desc = desc;
    }
}
