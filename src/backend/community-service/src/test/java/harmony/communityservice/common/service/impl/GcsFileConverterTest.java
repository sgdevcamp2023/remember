package harmony.communityservice.common.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class GcsFileConverterTest {

    @Autowired
    private GcsFileConverter gcsFileConverter;


    @Test
    @DisplayName("파일을 GCS에 등록 테스트")
    void register_file_gcs() {
        MockMultipartFile file = new MockMultipartFile("profile", "test-profile.jpg", "image/jpeg",
                "test image content".getBytes());

        gcsFileConverter.fileToUrl(file);
    }
}