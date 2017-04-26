package at.ac.tuwien.waecm.oauth;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan(basePackages="at.ac.tuwien.waecm")
@EnableJpaRepositories(basePackages="at.ac.tuwien.waecm")
@EntityScan(basePackages="at.ac.tuwien.waecm")
public class AppConfig {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
}
