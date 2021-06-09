package com.yuham.user.enetity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author yuhan
 * @date 06.05.2021 - 19:42
 * @purpose
 */
@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_uid", nullable = false, length = 40, unique = true)
    private int userUid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, password);
    }

    public User() {
    }

    public User(Integer id, int userUid, String name, String password) {
        this.id = id;
        this.userUid = userUid;
        this.name = name;
        this.password = password;
    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(Integer id, int userUid, String name, String password, String role) {
        this.id = id;
        this.userUid = userUid;
        this.name = name;
        this.password = password;
        this.role = role;
    }
}
