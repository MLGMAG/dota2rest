package ua.mlgmag.springboot.dota2rest.definition;

import ua.mlgmag.springboot.dota2rest.model.Player;

public interface PlayerService extends GenericService<Player, Integer> {

    Boolean existById(Integer id);

}
