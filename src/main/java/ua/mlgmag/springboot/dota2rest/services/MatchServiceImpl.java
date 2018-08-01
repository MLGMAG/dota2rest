package ua.mlgmag.springboot.dota2rest.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mlgmag.springboot.dota2rest.definition.MatchService;
import ua.mlgmag.springboot.dota2rest.definition.PlayerService;
import ua.mlgmag.springboot.dota2rest.model.Match;
import ua.mlgmag.springboot.dota2rest.model.Player;
import ua.mlgmag.springboot.dota2rest.repository.MatchRepository;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;

    private final PlayerService playerService;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository, PlayerService playerService) {
        this.matchRepository = matchRepository;
        this.playerService = playerService;
    }

    @Override
    public void save(Match match) {
        log.info("save {}" + match);
        matchRepository.save(match);
    }

    @Override
    public void delete(Match match) {
        log.info("delete {}" + match);
        matchRepository.delete(match);
    }

    @Override
    public Match findById(Integer id) {
        log.info("findById {}" + id);
        return matchRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Match with id \"" + id + "\" not found"));
    }

    @Override
    public List<Match> findAll() {
        log.info("findAll");
        return matchRepository.findAll();
    }

    @Override
    public void saveAllMatches(Set<Match> matches, Integer playerId) {
        Player player = playerService.findById(playerId);
        for (Match match : matches) {
            match.getPlayers().add(player);
        }
        log.info("saveAllMatches " + matches);
        matchRepository.saveAll(matches);
    }
}
