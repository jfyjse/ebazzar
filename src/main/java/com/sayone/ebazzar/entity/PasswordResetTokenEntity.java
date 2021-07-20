package com.sayone.ebazzar.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "passwordResetTokenEntity")
public class PasswordResetTokenEntity implements Serializable {
    private static final long serialVersionUID = -796661773177330343L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userDetails;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserEntity getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserEntity userDetails) {
        this.userDetails = userDetails;
    }


}
