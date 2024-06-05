package harmony.communityservice.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import harmony.communityservice.common.exception.IllegalGcsException;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class GCPConfig {
    @Bean
    public Storage gcpStorage() {
        try {
            ClassPathResource resource = new ClassPathResource("focal-bucksaw-425512-i0-b87a4f3d0cdf.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
            String projectId = "focal-bucksaw-425512-i0";
            return StorageOptions.newBuilder()
                    .setProjectId(projectId)
                    .setCredentials(credentials)
                    .build()
                    .getService();
        } catch (IOException e) {
            throw new IllegalGcsException();
        }
    }
}
