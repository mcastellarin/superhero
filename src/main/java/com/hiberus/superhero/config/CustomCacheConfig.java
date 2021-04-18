package com.hiberus.superhero.config;

import javax.cache.configuration.MutableConfiguration;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import com.hiberus.superhero.model.Superhero;
import com.hiberus.superhero.model.Superpower;

@EnableCaching
public class CustomCacheConfig {

	@Bean
	public JCacheManagerCustomizer superheroesCacheConfigurationCustomizer() {
		return cm -> {
			cm.createCache(Superhero.class.getSimpleName(), cacheConfiguration());
			cm.createCache(Superpower.class.getSimpleName(), cacheConfiguration());
		};
	}

	private static javax.cache.configuration.Configuration<Object, Object> cacheConfiguration() {
		return new MutableConfiguration<>().setStatisticsEnabled(Boolean.TRUE);
	}
}
