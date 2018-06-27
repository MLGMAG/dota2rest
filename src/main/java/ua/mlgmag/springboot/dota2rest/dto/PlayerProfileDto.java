package ua.mlgmag.springboot.dota2rest.dto;

import lombok.Data;

@Data
public class PlayerProfileDto {
    private int account_id;
    private String personaname;
    private String name;
    private int cheese;
    private String steamid;
    private String avatar;
    private String avatarmedium;
    private String avatarfull;
    private String profileurl;
    private String last_login;
    private String loccountrycode;
}
