package chessserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    // The main class of the Springboot server

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
