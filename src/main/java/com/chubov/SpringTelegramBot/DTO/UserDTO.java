package com.chubov.SpringTelegramBot.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private Long telegramId;

    private String username;

    private String firstName;

    private Set<RoleDTO> roles = new HashSet<>();

    public UserDTO(Long telegramId, String username, String firstName, Set<RoleDTO> roles) {
        this.telegramId = telegramId;
        this.username = username;
        this.firstName = firstName;
        this.roles = roles;
    }

    public UserDTO() {
    }
}
