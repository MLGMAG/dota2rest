package ua.mlgmag.springboot.dota2rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "players")
public class Player {

    @Id
    private int steamId32;
    private String personaName;
    private String name;
    @Indexed(unique = true)
    private String steamId64;
    private String avatar;
    private String steamUrl;
    private String solo_competitive_rank;
    private String competitive_rank;

}
