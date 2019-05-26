package br.ufsc.silq.config;

import br.ufsc.silq.web.filter.CachingHttpHeadersFilter;
import br.ufsc.silq.web.filter.StaticResourcesProductionFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlet.InstrumentedFilter;
import com.codahale.metrics.servlets.MetricsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.inject.Inject;
import javax.servlet.*;
import java.util.Arrays;
import java.util.EnumSet;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer, EmbeddedServletContainerCustomizer {

	private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

	@Inject
	private Environment env;

	@Inject
	private JHipsterProperties props;

	@Autowired(required = false)
	private MetricRegistry metricRegistry;

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		this.log.info("Web application configuration, using profiles: {}", Arrays.toString(this.env.getActiveProfiles()));
		EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.ASYNC);
		if (!this.env.acceptsProfiles(Profiles.FAST)) {
			this.initMetrics(servletContext, disps);
		}
		if (this.env.acceptsProfiles(Profiles.PRODUCTION)) {
			this.initCachingHttpHeadersFilter(servletContext, disps);
			this.initStaticResourcesProductionFilter(servletContext, disps);
		}
		this.log.info("Web application fully configured");
	}

	/**
	 * Set up Mime types.
	 */
	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
		// IE issue, see https://github.com/jhipster/generator-jhipster/pull/711
		mappings.add("html", "text/html;charset=utf-8");
		// CloudFoundry issue, see https://github.com/cloudfoundry/gorouter/issues/64
		mappings.add("json", "text/html;charset=utf-8");
		container.setMimeMappings(mappings);
	}

	/**
	 * Initializes the static resources production Filter.
	 */
	private void initStaticResourcesProductionFilter(ServletContext servletContext,
			EnumSet<DispatcherType> disps) {

		this.log.debug("Registering static resources production Filter");
		FilterRegistration.Dynamic staticResourcesProductionFilter = servletContext.addFilter("staticResourcesProductionFilter",
				new StaticResourcesProductionFilter());

		staticResourcesProductionFilter.addMappingForUrlPatterns(disps, true, "/");
		staticResourcesProductionFilter.addMappingForUrlPatterns(disps, true, "/index.html");
		staticResourcesProductionFilter.addMappingForUrlPatterns(disps, true, "/assets/*");
		staticResourcesProductionFilter.addMappingForUrlPatterns(disps, true, "/scripts/*");
		staticResourcesProductionFilter.setAsyncSupported(true);
	}

	/**
	 * Initializes the caching HTTP Headers Filter.
	 */
	private void initCachingHttpHeadersFilter(ServletContext servletContext,
			EnumSet<DispatcherType> disps) {
		this.log.debug("Registering Caching HTTP Headers Filter");
		FilterRegistration.Dynamic cachingHttpHeadersFilter = servletContext.addFilter("cachingHttpHeadersFilter",
				new CachingHttpHeadersFilter(this.env));

		cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/dist/assets/*");
		cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/dist/scripts/*");
		cachingHttpHeadersFilter.setAsyncSupported(true);
	}

	/**
	 * Initializes Metrics.
	 */
	private void initMetrics(ServletContext servletContext, EnumSet<DispatcherType> disps) {
		this.log.debug("Initializing Metrics registries");
		servletContext.setAttribute(InstrumentedFilter.REGISTRY_ATTRIBUTE,
				this.metricRegistry);
		servletContext.setAttribute(MetricsServlet.METRICS_REGISTRY,
				this.metricRegistry);

		this.log.debug("Registering Metrics Filter");
		FilterRegistration.Dynamic metricsFilter = servletContext.addFilter("webappMetricsFilter",
				new InstrumentedFilter());

		metricsFilter.addMappingForUrlPatterns(disps, true, "/*");
		metricsFilter.setAsyncSupported(true);

		this.log.debug("Registering Metrics Servlet");
		ServletRegistration.Dynamic metricsAdminServlet = servletContext.addServlet("metricsServlet", new MetricsServlet());

		metricsAdminServlet.addMapping("/metrics/metrics/*");
		metricsAdminServlet.setAsyncSupported(true);
		metricsAdminServlet.setLoadOnStartup(2);
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = this.props.getCors();
		if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
			source.registerCorsConfiguration("/api/**", config);
			source.registerCorsConfiguration("/v2/api-docs", config);
			source.registerCorsConfiguration("/oauth/**", config);
		}
		return new CorsFilter(source);
	}
}
