package campuspaths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"hw8", "campuspaths"})
@SpringBootApplication
public class CampusPathsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampusPathsApplication.class, args);
	}
}
