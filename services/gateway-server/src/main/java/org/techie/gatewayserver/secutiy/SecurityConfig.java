package org.techie.gatewayserver.secutiy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .authorizeExchange(exchange->exchange
//                        .pathMatchers("/eureka/**").permitAll()
//                        .anyExchange().authenticated()
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
//                .build();
//    }
}
