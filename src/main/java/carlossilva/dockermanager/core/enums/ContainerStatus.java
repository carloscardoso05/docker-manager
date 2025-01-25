package carlossilva.dockermanager.core.enums;

import lombok.Getter;

@Getter
public enum ContainerStatus {
    Created("created"),
    Restarting("restarting"),
    Running("running"),
    Paused("paused"),
    Exited("exited");

    private final String name;

    ContainerStatus(String name) {
        this.name = name;
    }
}
