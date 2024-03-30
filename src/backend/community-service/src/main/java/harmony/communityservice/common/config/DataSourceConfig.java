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
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
public class DataSourceConfig {

    public static final String SOURCE_DATASOURCE = "sourceDataSource";
    public static final String REPLICA_DATASOURCE = "replicaDataSource";

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
