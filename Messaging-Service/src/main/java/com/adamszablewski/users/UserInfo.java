package com.adamszablewski.users;



import com.adamszablewski.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "userInfo")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "rid", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id", referencedColumnName = "rid"))
    private List<Role> roles;

    @NotEmpty
    @Column(name = "first_name")
    private String name;

    @NotEmpty
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty
    @Column(name = "email")
    private String username;

    private String phoneNumber;

    @NotEmpty
    @Column(name = "password")
    @JsonIgnore
    private String password;


}
