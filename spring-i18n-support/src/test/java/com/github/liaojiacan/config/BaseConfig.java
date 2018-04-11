package com.github.liaojiacan.config;

import com.github.liaojiacan.spring.support.i18n.provider.JdbcMessageSoucreProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = { "com.github.liaojiacan" })
public class BaseConfig {

	@Bean
	public DataSource dataSource() {
		SimpleDriverDataSource simpleDataSource = new SimpleDriverDataSource();
		simpleDataSource.setDriverClass(com.mysql.jdbc.Driver.class);
		simpleDataSource.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true");
		simpleDataSource.setUsername("root");
		simpleDataSource.setPassword("liaojiacan");
		return simpleDataSource;
	}

	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
		return  jdbcTemplate;
	}

	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager()  {
		return new DataSourceTransactionManager(dataSource());
	}

	@Bean
	public JdbcMessageSoucreProvider jdbcMessageSoucreProvider(){
		JdbcMessageSoucreProvider provider = new JdbcMessageSoucreProvider();
		provider.setJdbcTemplate(jdbcTemplate());
		return provider;
	}
}
