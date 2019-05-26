package br.ufsc.silq.config.datasource;

import br.ufsc.silq.config.JHipsterProperties;
import br.ufsc.silq.config.Profiles;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile(Profiles.PRODUCTION)
public class ProductionDatabaseConfig {

	private final Logger log = LoggerFactory.getLogger(ProductionDatabaseConfig.class);

	@Bean
	public DataSource dataSource(DataSourceProperties dataSourceProperties, JHipsterProperties jHipsterProperties) {
		this.log.debug("Configuring Production Datasource");
		
		HikariConfig config = new HikariConfig();
		config.setDataSourceClassName(dataSourceProperties.getDriverClassName());
		config.addDataSourceProperty("url", this.getEnvOrFail("SILQ_DATABASE_URL"));
		config.addDataSourceProperty("user", this.getEnvOrFail("SILQ_DATABASE_USER"));
		config.addDataSourceProperty("password", this.getEnvOrFail("SILQ_DATABASE_PASSWORD"));
		return new HikariDataSource(config);
	}
	
	private String getEnvOrFail(String env) {
		String value = System.getenv(env);
		if (value == null) {
			throw new ApplicationContextException(String.format("System env %s must be defined", env));
		}
		return value;
	}
}
