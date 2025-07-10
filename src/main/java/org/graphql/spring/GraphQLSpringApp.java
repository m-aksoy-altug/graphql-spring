package org.graphql.spring;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import jakarta.annotation.PostConstruct;

/* -REST: only HTTP (GET, POST) - SOAP: HTTP, SMTP, TCP, JMS - GraphQL: over HTTP POST
 * Feature:				REST				SOAP			GraphQL 
 * Transport:			HTTP (mostly)		HTTP, SMTP, more		HTTP (often POST /graphql)
 * Format:	   			JSON (usually)	 	XML only			JSON (query & response)
 * Contract:			OpenAPI/Swagger		WSDL				Schema (.graphqls)
 * Flexibility:			Multiple endpoints	Rigid structure		Single endpoint, flexible query
 * Over/Under-fetch:	fixed responses		fixed responses		Rare (client controls shape)
 * Caching				Easy (URL-based)	Hard				Needs custom implementation
 * Versioning			Via URL (/v1, /v2)	Versioned WSDLs		Avoided, evolves via schema 
 * Use case				General APIs		Enterprise-legacy	Modern APIs with complex data needs
 * */
@SpringBootApplication
public class GraphQLSpringApp {
	
	private static final Logger log= LoggerFactory.getLogger(GraphQLSpringApp.class);
	
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	public void intProcess() {
		try {
			if(dataSource !=null) {
				JdbcTemplate template= new JdbcTemplate();
				template.setDataSource(dataSource);
			}else {
				throw new NullPointerException();
			}
			
		} catch (Exception e) {
			log.error("intProcess()"+e.getMessage());
		}
	}

	
	public static void main(String[] args) {
		log.info("GraphQLSpringApp starting...");
		SpringApplication.run(GraphQLSpringApp.class, args);
	}
}
