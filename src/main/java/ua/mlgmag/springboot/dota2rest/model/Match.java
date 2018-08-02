package ua.mlgmag.springboot.dota2rest.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    private Long matchId;
    private String result;
    private String duration;
    private Integer gameMode;
    private String heroName;
    private String heroAvatarUrl;
    private Integer version;
    private Integer kills;
    private Integer deaths;
    private Integer assists;
}
