package com.test.springcloud2service.configurations;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.eclipse.persistence.jpa.PersistenceProvider;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "authEntityManagerFactory", transactionManagerRef = "authTransactionManager", basePackages = "com.test.springcloud2service")
@EntityScan("com.test.springcloud2service.entities")
public class AuthDSConfiguration extends HikariConfig {

	@Bean
	@ConditionalOnMissingBean
	public EntityManagerFactoryBuilder entityManagerFactoryBuilder(JpaVendorAdapter jpaVendorAdapter,
			ObjectProvider<PersistenceUnitManager> persistenceUnitManagerProvider) {
		EntityManagerFactoryBuilder builder = new EntityManagerFactoryBuilder(jpaVendorAdapter,
				this.getVendorProperties(), persistenceUnitManagerProvider.getIfAvailable());
		//builder.setCallback(getVendorCallback());
		return builder;
	}

	@Bean(name = "authDataSource")
	public DataSource dataSource() {
		return new HikariDataSource(this);
	}

	@Bean
	public JpaVendorAdapter venderAdapter() {
		return this.vendorAdaptor();
	}

	@Bean(name = "authEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean authEntityManagerFactory(final EntityManagerFactoryBuilder builder,
			@Qualifier("authDataSource") final DataSource dataSource) {
		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setJpaVendorAdapter(this.vendorAdaptor());
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setPersistenceProviderClass(PersistenceProvider.class);
		entityManagerFactoryBean.setPersistenceUnitName("auth");
		entityManagerFactoryBean.setPackagesToScan("com.test.springcloud2service");
		entityManagerFactoryBean.setJpaPropertyMap(this.getVendorProperties());
		entityManagerFactoryBean.afterPropertiesSet();
		return entityManagerFactoryBean;
	}

	@Bean(name = "authTransactionManager")
	public PlatformTransactionManager authTransactionManager(
			@Qualifier("authEntityManagerFactory") final EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	private EclipseLinkJpaVendorAdapter vendorAdaptor() {
		final EclipseLinkJpaVendorAdapter vendorAdapter = new EclipseLinkJpaVendorAdapter();
		// put all the adapter properties here, such as show sql
		return vendorAdapter;
	}

	private Map<String, Object> getVendorProperties() {
		final Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("eclipselink.weaving", "false");
		ret.put("eclipselink.ddl-generation", "none");
		ret.put("eclipselink.query-results-cache", "false");
		ret.put("eclipselink.weaving.internal", "false");
		ret.put("eclipselink.cache.shared.default", "false");
		ret.put("eclipselink.show_sql", "true");
		ret.put("eclipselink.logging.parameters", "true");
		ret.put("eclipselink.logging.level", "FINE");
		ret.put("eclipselink.logging.level.sql", "FINE");
		return ret;
	}
}
