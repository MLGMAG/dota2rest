package ua.mlgmag.springboot.dota2rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private PlayerProfile playerProfile;
    private String solo_competitive_rank;
    private String competitive_rank;
}
