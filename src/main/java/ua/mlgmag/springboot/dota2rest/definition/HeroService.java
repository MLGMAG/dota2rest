package ua.mlgmag.springboot.dota2rest.definition;

import ua.mlgmag.springboot.dota2rest.model.Hero;

import java.util.List;

public interface HeroService extends GenericService<Hero, Integer> {
    void saveAllHeroes(List<Hero> heroes);
}
