package carlossilva.dockermanager.config;

import com.github.dockerjava.api.DockerClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DockerClientConfigurationTests {
    private final DockerClient dockerClient;

    @Autowired
    public DockerClientConfigurationTests(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
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
