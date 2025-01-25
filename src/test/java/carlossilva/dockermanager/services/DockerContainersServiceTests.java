package carlossilva.dockermanager.services;

import carlossilva.dockermanager.core.enums.ContainerStatus;
import com.github.dockerjava.api.model.Container;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DockerContainersServiceTests {
    private static final Logger log = LoggerFactory.getLogger(DockerContainersServiceTests.class);

    private final DockerContainersService dockerContainersService;

    @Autowired
    public DockerContainersServiceTests(DockerContainersService dockerContainersService) {
        this.dockerContainersService = dockerContainersService;
    }

    @Test
    public void testFindAll() {
        assertDoesNotThrow(() -> {
            final List<Container> containers = dockerContainersService.findAllContainers();
            log.info("Found {} containers:\n{}", containers.size(), containers.stream().map(Container::toString).collect(Collectors.joining("\n")));
        });
    }

    @Test
    public void testFindRunningContainers() {
        assertDoesNotThrow(() -> {
            final List<Container> containers = dockerContainersService.findAndFilterContainers(
                    ContainerQuery.builder().status(ContainerStatus.Running).build()
            );
            log.info("Found {} containers:\n{}", containers.size(), containers.stream().map(Container::toString).collect(Collectors.joining("\n")));
            assertTrue(containers.isEmpty());
        });
    }

    @Test
    public void testFindStoppedContainers() {
        assertDoesNotThrow(() -> {
            final List<Container> containers = dockerContainersService.findAndFilterContainers(
                    ContainerQuery.builder().status(ContainerStatus.Exited).build()
            );
            log.info("Found {} containers:\n{}", containers.size(), containers.stream().map(Container::toString).collect(Collectors.joining("\n")));
            assertFalse(containers.isEmpty());
        });
    }
}
