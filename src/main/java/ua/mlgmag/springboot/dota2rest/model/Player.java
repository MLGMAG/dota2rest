package ua.mlgmag.springboot.dota2rest.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = "users")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "players")
public class Player {

    @Id
    @Column(name = "steam_id32", columnDefinition = "INTEGER", unique = true, nullable = false)
    private int steamId32;

    @Column(name = "name", columnDefinition = "VARCHAR(255)", nullable = false)
    private String name;

    @Column(name = "steam_id64", columnDefinition = "VARCHAR(255)", unique = true, nullable = false)
    private String steamId64;

    @Column(name = "avatar", columnDefinition = "VARCHAR(255)", nullable = false)
    private String avatar;

    @Column(name = "steam_url", columnDefinition = "VARCHAR(255)", nullable = false)
    private String steamUrl;

    @Column(name = "solo_competitive_rank", columnDefinition = "VARCHAR(255)")
    private String solo_competitive_rank;

    @Column(name = "competitive_rank", columnDefinition = "VARCHAR(255)")
    private String competitive_rank;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "playerCollection")
    private Set<User> users;

    @Transient
    private Boolean isInDB;
}
