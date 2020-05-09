package com.eMurasu.model;

import com.eMurasu.enums.UserState;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class UserCredential {
    @Id
    @Column(length = 350, nullable = false, unique = true)
    private String username;
    @Column(length = 250, nullable = false)
    private String password;
    @Column(nullable = false)
    private int roleId;
    @Enumerated(EnumType.STRING)
    @Column(length = 25, nullable = false)
    private UserState status;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public UserState getStatus() {
        return status;
    }

    public void setStatus(UserState status) {
        this.status = status;
    }
}
