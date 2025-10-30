package com.backend.backend_blackcatgurumis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "Blackcatgurumis API",
		version = "1.0",
		description = "API para la tienda de amigurumis Blackcatgurumis",
		contact = @Contact(
			name = "Gabriel",
			email = "gabriel.cortes@blackcatgurumis.com"
		),
		license = @License(
			name = "Apache 2.0",
			url = "http://www.apache.org/licenses/LICENSE-2.0.html"
		)
	),
	servers = @Server(
		url = "http://localhost:8080",
		description = "Servidor local"
	)
)
@SecurityScheme(
	name = "token",
	type = SecuritySchemeType.HTTP,
	scheme = "bearer",
	bearerFormat = "JWT",
	in = SecuritySchemeIn.HEADER
)
public class BackendBlackcatgurumisApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendBlackcatgurumisApplication.class, args);
	}

}
