package ua.mlgmag.springboot.dota2rest.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mlgmag.springboot.dota2rest.constants.PlayerConstants;
import ua.mlgmag.springboot.dota2rest.constants.UrlMappingConstants;
import ua.mlgmag.springboot.dota2rest.definition.HeroService;
import ua.mlgmag.springboot.dota2rest.definition.PlayerService;
import ua.mlgmag.springboot.dota2rest.dto.*;
import ua.mlgmag.springboot.dota2rest.model.Hero;
import ua.mlgmag.springboot.dota2rest.model.Match;
import ua.mlgmag.springboot.dota2rest.model.Peer;
import ua.mlgmag.springboot.dota2rest.model.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApiService {

    private final OpenDotaApiClient openDotaApiClient;

    private final PlayerService playerService;

    private final HeroService heroService;

    @Autowired
    public ApiService(OpenDotaApiClient openDotaApiClient, PlayerService playerService, HeroService heroService) {
        this.openDotaApiClient = openDotaApiClient;
        this.playerService = playerService;
        this.heroService = heroService;
    }

    public Player findPlayerById(int id) {
        log.info("findPlayerById {}", id);
        return toPlayer(openDotaApiClient.findPlayerById(id).orElseThrow(() -> new IllegalStateException("Player not found")));
    }

    public List<Peer> findAllPeersByPlayerId(int id) {
        log.info("findAllPeersByPlayerId {}", id);
        return Arrays.stream(openDotaApiClient.findPeersByPlayerId(id)).map(this::toPeer).filter(peer -> peer.getGames() > 25)
                .collect(Collectors.toList());
    }

    public List<Match> findMatchesByPlayerId(int id) {
        List<Hero> heroes = heroService.findAll();
        log.info("findMatchesByPlayerId {}", id);
        return Arrays.stream(openDotaApiClient.findMatchesByPlayerId(id)).map(matchDto -> toMatch(matchDto, heroes)).limit(250)
                .collect(Collectors.toList());
    }

    public List<Hero> findAllHeroes() {
        log.info("findAllHeroes");
        return Arrays.stream(openDotaApiClient.findAllHeroes()).map(this::toHero)
                .collect(Collectors.toList());
    }

    private Peer toPeer(PeerDto input) {
        return new Peer(
                input.getAccount_id(),
                input.getWin(),
                input.getGames(),
                input.getWith_win(),
                input.getWith_games(),
                input.getPersonaname(),
                input.getAvatarfull(),
                playerService.existInDatabaseById(input.getAccount_id()));
    }

    private Player toPlayer(PlayerDto input) {

        if (input.getProfile() == null) return null;
        PlayerDto validateInput = playerDtoValidation(input);
        PlayerProfileDto profileDto = validateInput.getProfile();
        String steamId64 = String.valueOf(PlayerConstants.ZERO + profileDto.getAccount_id());
        log.info("toPlayer {}", input);
        return new Player(
                profileDto.getAccount_id(),
                profileDto.getName(),
                String.valueOf(PlayerConstants.ZERO + profileDto.getAccount_id()),
                profileDto.getAvatarfull(),
                PlayerConstants.PLAYER_PROFILE_PREFIX.concat(steamId64),
                validateInput.getSolo_competitive_rank(),
                validateInput.getCompetitive_rank(),
                null,
                null);
    }

    private Match toMatch(MatchDto input, List<Hero> heroes) {
        return new Match(
                input.getMatch_id(),
                input.getRadiant_win(),
                (input.getDuration() / 60) + " min",
                input.getGame_mode(),
                heroes.get(input.getHero_id() - 1).getName(),
                input.getVersion(),
                input.getKills(),
                input.getDeaths(),
                input.getAssists());
    }

    public Hero toHero(HeroDto input) {
        return new Hero(
                input.getId(),
                input.getLocalized_name(),
                UrlMappingConstants.DOTABUFF_URL_HEROES_ASSERTS +
                        input.getLocalized_name().toLowerCase().replace(" ", "-")
                        + ".jpg");
    }

    private PlayerDto playerDtoValidation(PlayerDto input) {

        if (input.getSolo_competitive_rank() == null) {
            input.setSolo_competitive_rank("Not calibrated");
        }

        if (input.getCompetitive_rank() == null) {
            input.setCompetitive_rank("Not calibrated");
        }

        if (input.getProfile().getName() == null || input.getProfile().getName().equals("")) {
            if (input.getProfile().getPersonaname() != null || !input.getProfile().getPersonaname().equals("")) {
                input.getProfile().setName(input.getProfile().getPersonaname());
            } else {
                input.getProfile().setName("Not Set");
            }
        }

        log.info("playerDtoValidation {}", input);
        return input;
    }
}
