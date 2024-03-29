package harmony.communityservice.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import harmony.communityservice.common.exception.IllegalGcsException;
import harmony.communityservice.common.service.ContentService;
import harmony.communityservice.common.service.impl.GcsContentService;
import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class GCPConfig {
    @Bean
    public Storage gcpStorage() {
        try {
            ClassPathResource resource = new ClassPathResource("smilegate-recamp-project-8342d152b109.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());
            String projectId = "smilegate-recamp-project";
            return StorageOptions.newBuilder()
                    .setProjectId(projectId)
                    .setCredentials(credentials)
                    .build()
                    .getService();
        } catch (IOException e) {
            throw new IllegalGcsException();
        }
    }

    @Bean
    public ContentService contentService() {
        return new GcsContentService(gcpStorage());
    }
}
