package pdm.persistence.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Movie Theater System",
                description = "Movie database. Paying processes.",
                version = "0.4.3",
                contact = @Contact(
                        name = "Posh",
                        email = "zakharovpo4@gmail.com"
                )
        )
)
public class SwaggerConfig {

}
