package ua.mlgmag.springboot.dota2rest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;
import ua.mlgmag.springboot.dota2rest.enums.Authority;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Document(collection = "users")
public class User implements UserDetails {

    public boolean isAccountNonExpired;
    public boolean isAccountNonLocked;
    public boolean isCredentialsNonExpired;
    public boolean isEnabled;
    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    @Indexed(unique = true)
    private String email;
    private String name;
    private String password;
    @Transient
    private String confirmPassword;
    private Set<Authority> authorities;

}
