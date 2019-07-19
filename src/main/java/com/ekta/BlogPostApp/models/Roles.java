package com.ekta.BlogPostApp.models;

import javax.persistence.*;

@Entity
@Table(name = "Roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RoleID")
    private int roleId;

    @Column(name = "Role")
    private String role;

    public Roles() {
    }

    public int getRoleId() {
        return roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
