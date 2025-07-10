package org.graphql.spring.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


@Configuration
public class GraphQLConfig {
	
	@Value("${spring.datasource.url}")
	String url;
	
	@Value("${spring.datasource.username}")
	String userName;
	
	@Value("${spring.datasource.password}")
	String password;
	
	@Value("${spring.datasource.DriverClassName}")
	String driverClassName;
		
	private static final Logger log = LoggerFactory.getLogger(GraphQLConfig.class);
	
	@Bean(name="dataSource")
	public DriverManagerDataSource dataSource() {
		//log.info("url:: "+url);
		//log.info("userName:: "+userName);
		//log.info("password:: "+password);
		//log.info("driverClassName:: "+driverClassName);
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		return dataSource;
	}
	

}
