package carlossilva.dockermanager.services;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Image;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DockerImagesService {
    private final DockerClient client;

    public DockerImagesService(DockerClient client) {
        this.client = client;
    }

    public List<Image> findAllImages() {
        return client.listImagesCmd().exec();
    }

    public List<Image> findImagesByName(String name) {
        return client.listImagesCmd().withImageNameFilter(name).exec();
    }

}
