package org.codefest.app.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the LoginFest entity.
 */
public class LoginFestDTO implements Serializable {

    private Long id;

    private String userName;

    private String password;

    private String rights;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LoginFestDTO loginFestDTO = (LoginFestDTO) o;
        if(loginFestDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), loginFestDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LoginFestDTO{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            ", rights='" + getRights() + "'" +
            "}";
    }
}
