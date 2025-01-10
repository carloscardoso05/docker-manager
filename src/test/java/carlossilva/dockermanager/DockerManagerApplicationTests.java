package carlossilva.dockermanager;

import com.github.dockerjava.api.DockerClient;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DockerManagerApplicationTests {
    private final DockerClient dockerClient;

    @Autowired
    DockerManagerApplicationTests(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void testDockerClientBeanCreated() {
        assertNotNull(dockerClient, "DockerClient bean should be created");
    }

    @Test
    void testDockerClientWorking() {
        assertDoesNotThrow(dockerClient.pingCmd()::exec, "DockerClient should be able to ping the Docker daemon");
    }

}
