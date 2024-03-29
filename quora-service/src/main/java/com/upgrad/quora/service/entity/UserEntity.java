package com.upgrad.quora.service.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;


//Note that the object of this class maps with database table "users"
//Examine the "users" table schema
/*
ID                   SERIAL(PK)   (Not null)
UUID                 VARCHAR(200) (Not null)
FIRSTNAME            VARCHAR(30) (Not null)
LASTNAME             VARCHAR(30) (Not null)
USERNAME             VARCHAR(30) (Not null)
EMAIL                VARCHAR(50) (Not null)
PASSWORD             VARCHAR(255) (Not null)
SALT                 VARCHAR(200) (Not null)
COUNTRY              VARCHAR(30)
ABOUTME              VARCHAR(50)
DOB                  VARCHAR(30)
ROLE                 VARCHAR(30)
CONTACTNUMBER        VARCHAR(30)
*/

//Complete this class on the basis of above table schema

//Write the annotation which defines that a class can be mapped to a Table
//
@Entity
@Table(name = "USERS")
@NamedQueries(
        {
                @NamedQuery(name = "deleteUserById", query = "delete from UserEntity u where u.id = :ids"),
                @NamedQuery(name = "userById", query = "select u from UserEntity u where u.id = :id"),
                @NamedQuery(name = "userByUname", query = "select u from UserEntity u where u.userName =:username"),
                @NamedQuery(name = "getAllUsers", query = "SELECT u from UserEntity u"),
//                @NamedQuery(name = "userAuthTokenByAccessToken", query = "SELECT u from UserEntity u"),
        }
)
public class UserEntity implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "UUID")
    @NotNull
    @Size(max = 200)
    private String uuid;

    @Column(name = "FIRSTNAME")
    @NotNull
    @Size(max = 30)
    private String firstName;

    @Column(name = "LASTNAME")
    @NotNull
    @Size(max = 30)
    private String lastName;

    @Column(name = "USERNAME")
    @NotNull
    @Size(max = 30)
    private String userName;

    @Column(name = "EMAIL")
    @NotNull
    @Size(max = 50)
    private String email;



    @Column(name = "PASSWORD")
    @NotNull
    @Size(max = 255)
    private String password;


    //@ToStringExclude
    @Column(name = "SALT")
    @NotNull
    @Size(max = 200)
    private String salt;

    @Column(name = "COUNTRY")
    @Size(max = 30)
    private String country;

    @Column(name = "ABOUTME")
    @Size(max = 30)
    private String aboutMe;

    @Column(name = "DOB")
    @Size(max = 30)
    private String dob;

    @Column(name = "ROLE")
    @Size(max = 30)
    private String role;

    @Column(name = "CONTACTNUMBER")
    @Size(max = 30)
    private String contactNumber;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id &&
                uuid.equals(that.uuid) &&
                firstName.equals(that.firstName) &&
                lastName.equals(that.lastName) &&
                userName.equals(that.userName) &&
                email.equals(that.email) &&
                password.equals(that.password) &&
                salt.equals(that.salt) &&
                Objects.equals(country, that.country) &&
                Objects.equals(aboutMe, that.aboutMe) &&
                Objects.equals(dob, that.dob) &&
                Objects.equals(role, that.role) &&
                Objects.equals(contactNumber, that.contactNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, firstName, lastName, userName, email, password, salt, country, aboutMe, dob, role, contactNumber);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
