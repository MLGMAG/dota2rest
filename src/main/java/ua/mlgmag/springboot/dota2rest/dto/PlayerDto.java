package ua.mlgmag.springboot.dota2rest.dto;

import lombok.Data;

@Data
public class PlayerDto {
    private String tracked_until;
    private String solo_competitive_rank;
    private String competitive_rank;
    private int rank_tier;
    private int leaderboard_rank;
    private PlayerMmrEstimateDto mmr_estimate;
    private PlayerProfileDto profile;
}
