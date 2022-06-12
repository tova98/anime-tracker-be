package hr.tvz.android.animetracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class AnimeTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnimeTrackerApplication.class, args);
	}

}
