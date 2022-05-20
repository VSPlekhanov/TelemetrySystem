package org.azul.telemetry.web.dto;

import lombok.Data;
import org.azul.telemetry.data.model.entity.User;
import org.jetbrains.annotations.NotNull;

/**
 * DTO object for transferring <code>User</code> over HTTP.
 *
 * @see User
 */
@Data
public class UserDto {
    @NotNull
    private final Long id;
    @NotNull
    private final String name;

    public UserDto(@NotNull User user) {
        id = user.getId();
        name = user.getUsername();
    }
}
