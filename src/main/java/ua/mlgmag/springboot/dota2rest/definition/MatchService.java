package ua.mlgmag.springboot.dota2rest.definition;

import ua.mlgmag.springboot.dota2rest.model.Match;

import java.util.Set;

public interface MatchService extends GenericService<Match, Integer> {
    void saveAllMatches(Set<Match> matches, Integer playerId);
}
