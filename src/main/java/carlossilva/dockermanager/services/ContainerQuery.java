package carlossilva.dockermanager.services;


import carlossilva.dockermanager.core.enums.ContainerStatus;
import com.github.dockerjava.api.model.Container;
import jakarta.annotation.Nullable;
import lombok.Builder;

import java.util.Arrays;

@Builder
public record ContainerQuery(
        @Nullable
        String id,
        @Nullable
        String name,
        @Nullable
        ContainerStatus status
) {
    public boolean test(Container container) {
        if (id() != null && !container.getId().equals(id())) return false;
        if (name() != null && Arrays.stream(container.getNames()).map(String::toLowerCase).noneMatch(name -> name.contains(name().toLowerCase())))
            return false;
        return status() == null || container.getStatus().toLowerCase().startsWith(status().getName().toLowerCase());
    }
}