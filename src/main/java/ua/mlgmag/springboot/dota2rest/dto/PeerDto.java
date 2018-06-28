package ua.mlgmag.springboot.dota2rest.dto;

import lombok.Data;

@Data
public class PeerDto {
    private int account_id;
    private int last_played;
    private int win;
    private int games;
    private int with_win;
    private int with_games;
    private int against_win;
    private int against_games;
    private int with_gpm_sum;
    private int with_xpm_sum;
    private String personaname;
    private String last_login;
    private String avatar;
    private String avatarfull;
}
