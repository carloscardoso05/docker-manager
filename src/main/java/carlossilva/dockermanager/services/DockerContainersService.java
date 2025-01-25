package carlossilva.dockermanager.services;

import carlossilva.dockermanager.core.enums.ContainerStatus;
import carlossilva.dockermanager.core.util.result.Result;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.exception.DockerException;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.Container;
import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DockerContainersService {
    private final DockerClient client;
    private final ApplicationContext applicationContext;


    public DockerContainersService(DockerClient client, ApplicationContext applicationContext) {
        this.client = client;
        this.applicationContext = applicationContext;
    }

    @Cacheable("containers")
    public List<Container> findAllContainers() {
        return client.listContainersCmd().withShowAll(true).exec();
    }

    public List<Container> findAndFilterContainers(@Nonnull ContainerQuery filter) {
        final DockerContainersService self = applicationContext.getBean(DockerContainersService.class);
        return self.findAllContainers().stream().filter(filter::test).toList();
    }

    public List<Container> findContainersByName(String name) {
        return findAndFilterContainers(ContainerQuery.builder().name(name).build());
    }

    public List<Container> findContainersByStatus(ContainerStatus status) {
        return findAndFilterContainers(ContainerQuery.builder().status(status).build());
    }

    public Optional<Container> findContainerById(String id) {
        return findAndFilterContainers(ContainerQuery.builder().id(id).build()).stream().findFirst();
    }

    public List<Container> findContainersByIds(Collection<String> ids) {
        return client.listContainersCmd().withShowAll(true).withIdFilter(ids).exec();
    }

    @CacheEvict(value = "containers", allEntries = true)
    public Optional<DockerException> startContainer(String id) {
        try {
            client.startContainerCmd(id).exec();
            return Optional.empty();
        } catch (DockerException e) {
            return Optional.of(e);
        }
    }

    @CacheEvict(value = "containers", allEntries = true)
    public Optional<DockerException> stopContainer(String id) {
        try {
            client.stopContainerCmd(id).exec();
            return Optional.empty();
        } catch (DockerException e) {
            return Optional.of(e);
        }
    }

    @CacheEvict(value = "containers", allEntries = true)
    public Optional<NotFoundException> killContainer(String id) {
        try {
            client.killContainerCmd(id).exec();
            return Optional.empty();
        } catch (NotFoundException e) {
            return Optional.of(e);
        }
    }

    @CacheEvict(value = "containers", allEntries = true)
    public Optional<NotFoundException> restartContainer(String id) {
        try {
            client.restartContainerCmd(id).exec();
            return Optional.empty();
        } catch (NotFoundException e) {
            return Optional.of(e);
        }
    }

    @CacheEvict(value = "containers", allEntries = true)
    public Result<CreateContainerResponse, DockerException> createContainer(String imageName) {
        try {
            return Result.success(client.createContainerCmd(imageName).exec());
        } catch (DockerException e) {
            return Result.failure(e);
        }
    }

    @CacheEvict(value = "containers", allEntries = true)
    public void removeContainer(String id) {
        client.removeContainerCmd(id).exec();
    }

    @CacheEvict(value = "containers", allEntries = true)
    public Optional<NotFoundException> renameContainer(String id, String name) {
        try {
            client.renameContainerCmd(id).withName(name).exec();
            return Optional.empty();
        } catch (NotFoundException e) {
            return Optional.of(e);
        }
    }
}
