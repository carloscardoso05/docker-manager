package carlossilva.dockermanager.core;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class ContainerService implements RetrieverService<Container, String> {
    protected final DockerClient client;

    protected ContainerService(DockerClient client) {
        this.client = client;
    }

    public List<Container> filterContainers(Predicate<Container> predicate) {
        return findAll().stream()
                .filter(predicate)
                .toList();
    }

    public List<Container> findRunningContainers() {
        return filterContainers(
                (container) -> container.getStatus().startsWith("Up")
        );
    }

    public List<Container> findStoppedContainers() {
        return filterContainers(
                (container) -> container.getStatus().startsWith("Exited")
        );
    }

    @Override
    public List<Container> findAll() {
        return client.listContainersCmd()
                .withShowAll(true)
                .exec();
    }

    @Override
    public Optional<Container> findById(String id) {
        return client.listContainersCmd()
                .withIdFilter(List.of(id))
                .exec()
                .stream()
                .findFirst();
    }
}
