package harmony.communityservice.common.service.impl;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

import harmony.communityservice.common.exception.IllegalGcsException;
import harmony.communityservice.common.utils.UuidHolder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class GcsFileConverterTest {

    @Autowired
    private GcsFileConverter gcsFileConverter;

    @MockBean
    private UuidHolder uuidHolder;

    @Test
    @DisplayName("파일을 GCS에 등록 테스트")
    void register_file_gcs() {
        MockMultipartFile file = new MockMultipartFile("profile", "test-profile.jpg", "image/jpeg",
                "test image content".getBytes());
        given(uuidHolder.random()).willReturn("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        gcsFileConverter.fileToUrl(file);
    }

    @Test
    @DisplayName("파일을 GCS에 등록 예외 테스트")
    void register_file_gcs_exception() {
        MockMultipartFile file = new MockMultipartFile("profile", "test-profile.jpg", "image/jpeg",
                "test image content".getBytes());
        doThrow(new RuntimeException()).when(uuidHolder).random();
        Assertions.assertThrows(IllegalGcsException.class, () -> gcsFileConverter.fileToUrl(file));
    }
}