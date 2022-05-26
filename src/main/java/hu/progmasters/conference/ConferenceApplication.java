package hu.progmasters.conference;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConferenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConferenceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public OpenAPI customAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Conference API")
						.version("1.0.0")
						.description("This is my practical exam work"));
	}
}
