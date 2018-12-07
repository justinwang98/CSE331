package campuspaths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Application for running campus path
 */
@ComponentScan(basePackages = {"hw8", "campuspaths"})
@SpringBootApplication
public class CampusPathsApplication {

	/**
	 * main method to run campus paths
	 * @param args arguments to be passed in
	 */
	public static void main(String[] args) {
		SpringApplication.run(CampusPathsApplication.class, args);
	}
}
