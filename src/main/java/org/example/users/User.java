package org.example.users;

import java.util.Objects;

public class User {
    private final String login;
    private final String password;
    private final UserType userType;
    private final int userId;

    public User(String login, String password, UserType userType, int userId) {
        this.login = login;
        this.password = password;
        this.userType = userType;
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return getUserId() == user.getUserId() && Objects.equals(getLogin(), user.getLogin()) && Objects.equals(getPassword(), user.getPassword()) && getUserType() == user.getUserType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin(), getPassword(), getUserType(), getUserId());
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                ", userId=" + userId +
                '}';
    }
}
