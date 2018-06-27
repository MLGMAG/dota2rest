package ua.mlgmag.springboot.dota2rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProPlayer {
    private int steamId32;
    private String steamId64;
    private String avatarUrl;
    private String profileUrl;
    private String personaName;
    private Date lastLogin;
    private Date fullHistoryTime;
    private int cheese;
    private Boolean fhUnavailable;
    private String localCountryCode;
    private String name;
    private String countryCode;
    private int fantasyRole;
    private Team team;
    private Boolean isLocked;
    private Boolean isPro;
    private int lockedUntil;
}
