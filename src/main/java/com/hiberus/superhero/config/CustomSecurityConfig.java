package com.hiberus.superhero.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer{

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("/**")
				.allowedHeaders("Authorization", "Cache-Control", "Content-Type", "Content-Length")
				.allowCredentials(Boolean.TRUE).maxAge(3600)
				.allowedMethods(HttpMethod.GET.name(),HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name());
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		auth.inMemoryAuthentication()
				.withUser("admin").password(encoder.encode("admin")).roles("ADMIN")
				.and()
				.withUser("user").password(encoder.encode("user")).roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.headers().frameOptions().disable();
		http.formLogin().disable();
		http.cors();
		http.csrf().disable();
		http.httpBasic();
		http.authorizeRequests().antMatchers("/h2/**").hasRole("ADMIN")
								.antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**").permitAll()
								.antMatchers(HttpMethod.GET, "/api/**").hasAnyRole("ADMIN", "USER")
								.antMatchers(HttpMethod.POST, "/api/**").hasAnyRole("ADMIN", "USER")
								.antMatchers("/api/**").hasRole("ADMIN")
								.anyRequest().fullyAuthenticated();
		http.exceptionHandling().accessDeniedHandler((request, response, accessDeniedException) -> {
            AccessDeniedHandler defaultAccessDeniedHandler = new AccessDeniedHandlerImpl();
            defaultAccessDeniedHandler.handle(request, response, accessDeniedException);
        });
	}
}
