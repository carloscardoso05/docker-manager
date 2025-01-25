package carlossilva.dockermanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
public class DockerManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerManagerApplication.class, args);
    }

}
