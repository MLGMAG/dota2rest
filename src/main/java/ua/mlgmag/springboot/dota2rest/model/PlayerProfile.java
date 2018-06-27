package ua.mlgmag.springboot.dota2rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerProfile {
    private int steamId32;
    private String personaName;
    private String name;
    private String steamId64;
    private String avatar;
    private String steamUrl;
}
