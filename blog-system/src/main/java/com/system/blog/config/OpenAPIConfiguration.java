package com.system.blog.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenAPIConfiguration {

	@Bean
	   public OpenAPI defineOpenApi() {
	       Server server = new Server();
	       server.setUrl("http://localhost:8080");
	       server.setDescription("Development");

	       Contact myContact = new Contact();
	       myContact.setName("Ivan List");
	       myContact.setEmail("alejandrolistc@gmail.com");
	       myContact.setUrl("https://github.com/Alelizzt");

	       Info information = new Info()
	               .title("Blog Management System API")
	               .version("1.0")
	               .description("This API exposes endpoints to manage a simple blog.")
	               .contact(myContact);
	       return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
	               .components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()))
	               .info(information).servers(List.of(server));
	   }
	
	private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP)
            .bearerFormat("JWT")
            .scheme("bearer");
    }
}
