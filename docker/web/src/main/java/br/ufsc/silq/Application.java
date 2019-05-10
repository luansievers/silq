package br.ufsc.silq;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import br.ufsc.silq.config.Profiles;
import lombok.extern.slf4j.Slf4j;
import br.ufsc.silq.config.JHipsterProperties;

@ComponentScan
@EnableAutoConfiguration(exclude = { MetricFilterAutoConfiguration.class, MetricRepositoryAutoConfiguration.class })
@EnableConfigurationProperties({ JHipsterProperties.class })
@Slf4j
public class Application {

	@Inject
	private Environment env;

	/**
	 * Initializes silq2.
	 * <p/>
	 * Spring profiles can be configured with a program arguments
	 * --spring.profiles.active=your-active-profile
	 * <p/>
	 * <p>
	 * You can find more information on how profiles work with JHipster on
	 * <a href="http://jhipster.github.io/profiles.html">http://jhipster.github.
	 * io/profiles.html</a>.
	 * </p>
	 */
	@PostConstruct
	public void initApplication() throws IOException {
		if (this.env.getActiveProfiles().length == 0) {
			log.warn("No Spring profile configured, running with default configuration");
		} else {
			log.info("Running with Spring profile(s) : {}", Arrays.toString(this.env.getActiveProfiles()));
			Collection<String> activeProfiles = Arrays.asList(this.env.getActiveProfiles());
			if (activeProfiles.contains(Profiles.DEVELOPMENT)
					&& activeProfiles.contains(Profiles.PRODUCTION)) {
				log.error("You have misconfigured your application! "
						+ "It should not run with both the 'dev' and 'prod' profiles at the same time.");
			}
			if (activeProfiles.contains(Profiles.PRODUCTION)
					&& activeProfiles.contains(Profiles.FAST)) {
				log.error("You have misconfigured your application! "
						+ "It should not run with both the 'prod' and 'fast' profiles at the same time.");
			}
		}
	}

	/**
	 * Main method, used to run the application.
	 */
	public static void main(String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(Application.class);
		SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
		addDefaultProfile(app, source);

		Environment env = app.run(args).getEnvironment();
		log.info(
				"Access URLs:\n----------------------------------------------------------\n\t"
						+ "Local: \t\thttp://127.0.0.1:{}\n\t"
						+ "External: \thttp://{}:{}\n----------------------------------------------------------",
				env.getProperty("server.port"), InetAddress.getLocalHost().getHostAddress(),
				env.getProperty("server.port"));
	}

	/**
	 * If no profile has been configured, set by default the "dev" profile.
	 */
	private static void addDefaultProfile(SpringApplication app, SimpleCommandLinePropertySource source) {
		if (!source.containsProperty("spring.profiles.active")
				&& !System.getenv().containsKey("SPRING_PROFILES_ACTIVE")) {

			app.setAdditionalProfiles(Profiles.DEVELOPMENT);
		}
	}
}
