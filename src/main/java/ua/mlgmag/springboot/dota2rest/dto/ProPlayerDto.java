package ua.mlgmag.springboot.dota2rest.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ProPlayerDto {
    private int account_id;
    private String steamid;
    private String avatar;
    private String avatarmedium;
    private String avatarfull;
    private String profileurl;
    private String personaname;
    private Date last_login;
    private Date full_history_time;
    private int cheese;
    private Boolean fh_unavailable;
    private String loccountrycode;
    private String name;
    private String country_code;
    private int fantasy_role;
    private int team_id;
    private String team_name;
    private String team_tag;
    private Boolean is_locked;
    private Boolean is_pro;
    private int locked_until;
}
