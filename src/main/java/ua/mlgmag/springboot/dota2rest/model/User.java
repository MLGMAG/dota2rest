package ua.mlgmag.springboot.dota2rest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.userdetails.UserDetails;
import ua.mlgmag.springboot.dota2rest.enums.Authority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString(exclude = "playerCollection")
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "uuid", unique = true, nullable = false)
    private UUID id;

    @Column(name = "username", columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
    private String username;

    @Column(name = "email", columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
    private String email;

    @Column(name = "name", columnDefinition = "VARCHAR(255)", nullable = false)
    private String name;

    @Column(name = "password", columnDefinition = "VARCHAR(255)", nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_players", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "player_id"))
    @Column(name = "player")
    private Set<Player> playerCollection;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Authority.class, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "authority", columnDefinition = "VARCHAR(255)", nullable = false)
    private Set<Authority> authorities = new HashSet<>();

    @Column(name = "non_expired", columnDefinition = "boolean", nullable = false)
    private boolean isAccountNonExpired;

    @Column(name = "non_locked", columnDefinition = "boolean", nullable = false)
    private boolean isAccountNonLocked;

    @Column(name = "credentials_non_expired", columnDefinition = "boolean", nullable = false)
    private boolean isCredentialsNonExpired;

    @Column(name = "enabled", columnDefinition = "boolean", nullable = false)
    private boolean isEnabled;

}
