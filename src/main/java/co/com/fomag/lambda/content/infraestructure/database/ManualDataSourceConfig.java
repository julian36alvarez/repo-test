package co.com.fomag.lambda.content.infraestructure.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

//@Factory
public class ManualDataSourceConfig {

    /*private static final Logger LOG = LoggerFactory.getLogger(ManualDataSourceConfig.class);

    @Singleton
    @Bean
    @Context
    @Named("default")
    public DataSource dataSource() {
        LOG.info("Inicializando DataSource con configuración temporal...");

        try {
            // Configuración hardcodeada temporal
            HikariConfig hikariConfig = new HikariConfig();

            // DATOS QUEMADOS (solo para desarrollo temporal)
            hikariConfig.setJdbcUrl("jdbc:postgresql://fomag-develop-0-instancia.caziu2awou81.us-east-1.rds.amazonaws.com:5432/FOMAG_Dev");
            hikariConfig.setUsername("userpostgres");
            hikariConfig.setPassword("XZUVMAPba55qvCER");
            hikariConfig.setDriverClassName("org.postgresql.Driver");

            // Configuración optimizada
            hikariConfig.setMaximumPoolSize(10);
            hikariConfig.setMinimumIdle(2);
            hikariConfig.setConnectionTimeout(30000); // 30 segundos
            hikariConfig.setIdleTimeout(600000);     // 10 minutos
            hikariConfig.setMaxLifetime(1800000);    // 30 minutos
            hikariConfig.setPoolName("FOMAG-HikariPool-Temporal");

            // Configuración adicional para PostgreSQL
            hikariConfig.addDataSourceProperty("preparedStatementCacheQueries", "250");
            hikariConfig.addDataSourceProperty("preparedStatementCacheSizeMiB", "5");
            hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
            hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");

            // Crear y validar DataSource
            HikariDataSource dataSource = new HikariDataSource(hikariConfig);
            validateDataSource(dataSource);

            return dataSource;

        } catch (Exception e) {
            LOG.error("Error al crear DataSource temporal", e);
            throw new RuntimeException("Failed to initialize temporary database connection", e);
        }
    }

    private void validateDataSource(HikariDataSource dataSource) {
        try {
            LOG.info("🔄 Validando conexión temporal a la base de datos...");
            dataSource.getConnection().close();
            LOG.info("✔ Conexión temporal validada exitosamente");
        } catch (Exception e) {
            LOG.error("Validación de conexión temporal fallida", e);
            dataSource.close();
            throw new RuntimeException("Temporary database connection validation failed", e);
        }
    }*/
}
