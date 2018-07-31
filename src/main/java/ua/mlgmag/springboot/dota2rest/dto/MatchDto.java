package ua.mlgmag.springboot.dota2rest.dto;

import lombok.Data;

@Data
public class MatchDto {
    private Long match_id;
    private Integer player_slot;
    private Boolean radiant_win;
    private Integer duration;
    private Integer game_mode;
    private Integer lobby_type;
    private Integer hero_id;
    private Integer start_time;
    private Integer version;
    private Integer kills;
    private Integer deaths;
    private Integer assists;
    private Boolean skill;
    private Integer leaver_status;
    private Integer party_size;
}
