package org.springframework.samples.petclinic;

import org.hdiv.config.annotation.ExclusionRegistry;
import org.hdiv.ee.config.annotation.ValidationConfigurer;
import org.hdiv.filter.ValidatorFilter;
import org.hdiv.listener.InitListener;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hdivsecurity.services.config.EnableHdiv4ServicesSecurityConfiguration;
import com.hdivsecurity.services.config.HdivServicesSecurityConfigurerAdapter;
import com.hdivsecurity.services.config.ServicesSecurityConfigBuilder;

@Configuration
@EnableHdiv4ServicesSecurityConfiguration
public class DelegateConfig extends HdivServicesSecurityConfigurerAdapter {
	@Bean
	// Register the Validator Filter
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		ValidatorFilter validatorFilter = new ValidatorFilter();
		registrationBean.setFilter(validatorFilter);
		registrationBean.setOrder(0);
		return registrationBean;
	}

	@Bean
	// Register the listener
	public InitListener initListener() {
		return new InitListener();
	}

	@Override
	// Add default editable validation rules for all urls
	public void configureEditableValidation(final ValidationConfigurer validationConfigurer) {
		validationConfigurer.addValidation("/.*");
	}

	@Override
	// Validation configuration
	public void configure(final ServicesSecurityConfigBuilder builder) {
		builder.confidentiality(false).sessionExpired().homePage("/");
		builder.showErrorPageOnEditableValidation(true);
		builder.hypermediaSupport(false);
		builder.appName("spring-petclinic-angularjs");

		// Dashboard credentials
		builder.dashboardUser("admin");
		builder.dashboardPass("hdiv");
	}

	@Override
	// UI resources should be excluded
	public void addExclusions(final ExclusionRegistry registry) {
		registry.addUrlExclusions("/",
				"/scripts/.*", "/bootstrap/.*", "/images/.*", "/fonts/.*",
				"/angular-ui-router/.*", "/angular/.*", "/jquery/.*", "/css/.*");
	}
}