package gov.max.resource.demo.config;

import gov.max.resource.demo.authorizer.CustomAuthorizer;

import org.pac4j.core.authorization.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.oauth.client.CasOAuthWrapperClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Pac4jConfig {

    @Bean
    public Config config() {

        final CasOAuthWrapperClient casOAuthWrapperClient = new CasOAuthWrapperClient("keyvalue", "topsecret", "http://localhost:5252/cas/oauth2.0");
        final Clients clients = new Clients("http://localhost:8080/callback", casOAuthWrapperClient);

        final Config config = new Config(clients);
        config.addAuthorizer("admin", new RequireAnyRoleAuthorizer("ROLE_ADMIN"));
        config.addAuthorizer("custom", new CustomAuthorizer());

        return config;
    }
}
