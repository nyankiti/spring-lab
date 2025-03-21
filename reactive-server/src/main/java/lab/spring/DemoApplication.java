package lab.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args)
	{
		System.out.println("testtest");
		SpringApplication.run(DemoApplication.class, args);
	}

}
