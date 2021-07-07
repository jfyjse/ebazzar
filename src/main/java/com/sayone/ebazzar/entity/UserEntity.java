package com.sayone.ebazzar.entity;

import javax.persistence.*;
import java.io.Serializable;

//table name changed , @entity added.
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    @Id
    //column added to name user id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id")
    private long userId;
    @Column(nullable = false,length = 50)
    private String firstName;
    @Column(nullable = false,length = 50)
    private String lastName;
    @Column(nullable = false,length = 120)
    private String email;
    @Column(nullable = false,length = 10)
    private String password;
    @Column(nullable = false,length = 10)
    private int phoneNumber;
    @Column(nullable = false,length = 50)
    private String userType;
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private AddressEntity address;

    //one to one mapping for wishlist

    //@OneToOne(cascade = CascadeType.ALL)
    //joining user to wishlist(uno reverse)
    //@JoinColumn(name = "wishlist_id", referencedColumnName = "id")
    @OneToOne(targetEntity = WishlistEntity.class,cascade = CascadeType.ALL)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private WishlistEntity wishlist;

    public WishlistEntity getWishlist() {
        return wishlist;
    }

    public void setWishlist(WishlistEntity wishlist) {
        this.wishlist = wishlist;
    }

    public long getId() {
        return userId;
    }
    public void setId(long id) {
        this.userId = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getUserType() {
        return userType;
    }
    public void setUserType(String userType) {
        this.userType = userType;
    }
    public AddressEntity getAddress() {
        return address;
    }
    public void setAddress(AddressEntity address) {
        this.address = address;
    }
}
