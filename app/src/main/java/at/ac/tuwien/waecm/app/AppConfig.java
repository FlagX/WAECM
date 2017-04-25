package at.ac.tuwien.waecm.app;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages="at.ac.tuwien.waecm")
@EnableJpaRepositories(basePackages="at.ac.tuwien.waecm")
@EntityScan(basePackages="at.ac.tuwien.waecm")
public class AppConfig {
}
