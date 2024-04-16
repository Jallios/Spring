package com.example.pr4_2.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;


@Entity
@Table(name = "Role")
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long IdRole;

    @Column(unique = true)
    @NotBlank(message = "Роль не должна быть пустой")
    @Size(min=4)
    private String NameRole;

    @OneToOne(mappedBy = "role", cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    private User user;

    public Role() {
    }

    public Role(Long idRole, String nameRole) {
        IdRole = idRole;
        NameRole = nameRole;
    }

    public Long getIdRole() {
        return IdRole;
    }

    public void setIdRole(Long idRole) {
        IdRole = idRole;
    }

    public String getNameRole() {
        return NameRole;
    }

    public void setNameRole(String nameRole) {
        NameRole = nameRole;
    }
}