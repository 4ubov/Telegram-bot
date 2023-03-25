package com.chubov.SpringTelegramBot.models;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int roleId;

    @Column(name = "role_name")
    String roleName;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = User.class, mappedBy = "roles")
    @JsonManagedReference
    private Set<User> users = new HashSet<>();


    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role() {

    }
}
