package carlossilva.dockermanager.services;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;

import java.util.List;
import java.util.Optional;

public class ContainerServiceImpl extends carlossilva.dockermanager.core.ContainerService {
    public ContainerServiceImpl(DockerClient client) {
        super(client);
    }

    @Override
    public Container create(Container entity) {
        return null;
    }

    @Override
    public Optional<Container> update(Container entity, String s) {
        return Optional.empty();
    }

    @Override
    public Optional<Container> delete(String s) {
        return Optional.empty();
    }
}
