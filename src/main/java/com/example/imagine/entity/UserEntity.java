package com.example.imagine.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;
import java.util.Set;

@Document
@Data
@NoArgsConstructor
public class UserEntity {

    public static final String ACTIVE = "ACTIVE";
    public static final String PENDING = "PENDING";
    public static final String DELETED = "DELETED";

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;
    private String password;

    private String firstName;
    private String lastName;
    private String status = PENDING;

    @DBRef
    private Set<Role> roles;

    public UserEntity(String email, String password){
        this.email = email;
        this.password = password;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(email, ((UserEntity) obj).getEmail());
    }

}
