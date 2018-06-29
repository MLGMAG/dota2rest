package ua.mlgmag.springboot.dota2rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Peer {
    private int steamId32;
    private int win;
    private int games;
    private int with_win;
    private int with_games;
    private String personaname;
    private String avatar;

    @Transient
    private Boolean isInDB;
}
