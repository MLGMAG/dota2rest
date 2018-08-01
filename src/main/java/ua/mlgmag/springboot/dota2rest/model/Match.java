package ua.mlgmag.springboot.dota2rest.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString(exclude = "players")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "matches")
public class Match {

    @Id
    @Column(name = "id", columnDefinition = "BIGINT", nullable = false, unique = true)
    private Long matchId;

    @Column(name = "radiant_win", columnDefinition = "boolean")
    private Boolean radiantWin;

    @Column(name = "duration", columnDefinition = "VARCHAR(255)", nullable = false)
    private String duration;

    @Column(name = "game_mode", columnDefinition = "INTEGER", nullable = false)
    private Integer gameMode;

    @Column(name = "patch", columnDefinition = "INTEGER")
    private Integer version;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "player_match", joinColumns = @JoinColumn(name = "match_id"), inverseJoinColumns = @JoinColumn(name = "player_id"))
    private Set<Player> players = new HashSet<>();
}
