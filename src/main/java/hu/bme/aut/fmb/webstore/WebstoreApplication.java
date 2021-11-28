package hu.bme.aut.fmb.webstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@EnableScheduling
@SpringBootApplication
public class WebstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebstoreApplication.class, args);
	}
  
	@Scheduled(cron = "0 */1 * * * *")
	public void saveDataBase(){
		System.out.println(new Date());

	}
}
