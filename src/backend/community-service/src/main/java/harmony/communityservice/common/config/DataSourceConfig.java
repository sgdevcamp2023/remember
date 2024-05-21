package harmony.communityservice.common.config;

import com.google.common.collect.ImmutableMap;
import com.zaxxer.hikari.HikariDataSource;
import harmony.communityservice.common.datasource.RoutingDataSource;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class DataSourceConfig {

    private static final String SOURCE_DATASOURCE = "sourceDataSource";
    private static final String REPLICA_DATASOURCE = "replicaDataSource";

    @Bean(SOURCE_DATASOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.source.hikari")
    public DataSource sourceDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(REPLICA_DATASOURCE)
    @ConfigurationProperties(prefix = "spring.datasource.replica.hikari")
    public DataSource replicaDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public DataSourceInitializer sourceDataSourceInitializer(@Qualifier(SOURCE_DATASOURCE) DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    @Bean
    public DataSourceInitializer replicaDataSourceInitializer(@Qualifier(REPLICA_DATASOURCE) DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    private DatabasePopulator databasePopulator() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("schema.sql"));
        return populator;
    }


    @Bean
    public DataSource routingDataSource(
            @Qualifier(SOURCE_DATASOURCE) DataSource sourceDataSource,
            @Qualifier(REPLICA_DATASOURCE) DataSource replicaDataSource
    ) {

        Map<Object, Object> datasourceMap = ImmutableMap.<Object, Object>builder()
                .put("source", sourceDataSource)
                .put("replica", replicaDataSource)
                .build();
        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(datasourceMap);
        routingDataSource.setDefaultTargetDataSource(sourceDataSource);
        return routingDataSource;
    }

    @Bean
    @Primary
    public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
