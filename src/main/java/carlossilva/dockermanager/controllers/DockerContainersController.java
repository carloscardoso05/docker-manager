package carlossilva.dockermanager.controllers;

import carlossilva.dockermanager.core.enums.ContainerStatus;
import carlossilva.dockermanager.services.ContainerQuery;
import carlossilva.dockermanager.services.DockerContainersService;
import com.github.dockerjava.api.model.Container;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/containers")
public class DockerContainersController {
    private final DockerContainersService dockerContainersService;

    public DockerContainersController(DockerContainersService dockerContainersService) {
        this.dockerContainersService = dockerContainersService;
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<List<Container>> getContainers(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String name,
            @RequestParam Optional<String> status
    ) {
        final ContainerQuery filter = new ContainerQuery(
                id,
                name,
                status.map(ContainerStatus::valueOf).orElse(null)
        );
        return ResponseEntity.ok(dockerContainersService.findAndFilterContainers(filter));
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Container> getContainerById(@PathVariable String id) {
        return ResponseEntity.ok(dockerContainersService.findContainerById(id).orElseThrow());
    }

    @CrossOrigin
    @PostMapping("/{id}/start")
    public ResponseEntity<Void> startContainer(@PathVariable String id) {
        dockerContainersService.startContainer(id);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PostMapping("/{id}/stop")
    public ResponseEntity<Void> stopContainer(@PathVariable String id) {
        dockerContainersService.stopContainer(id);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @PostMapping("/{id}/restart")
    public ResponseEntity<Void> restartContainer(@PathVariable String id) {
        dockerContainersService.restartContainer(id);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeContainer(@PathVariable String id) {
        dockerContainersService.removeContainer(id);
        return ResponseEntity.noContent().build();
    }
}
