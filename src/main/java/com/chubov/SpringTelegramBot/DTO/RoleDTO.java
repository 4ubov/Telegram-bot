package com.chubov.SpringTelegramBot.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDTO {
    String roleName;

    public RoleDTO(String roleName) {
        this.roleName = roleName;
    }

    public RoleDTO() {

    }
}
