package ua.mlgmag.springboot.dota2rest.services.OpenDotaApi;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OpenDotaApiService {

    private final OpenDotaApiClient openDotaApiClient;

    private final PlayerService playerService;

    private final HeroService heroService;

    @Autowired
    public OpenDotaApiService(OpenDotaApiClient openDotaApiClient, PlayerService playerService, HeroService heroService) {
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

    public Set<Match> findRecentMatchesByPlayerId(int id) {
        log.info("findMatchesByPlayerId {}", id);
        return Arrays.stream(openDotaApiClient.findRecentMatchesByPlayerId(id)).map(this::toMatch).limit(10)
                .collect(Collectors.toSet());
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
                new HashSet<>(),
                new HashSet<>(),
                null);
    }

    private Match toMatch(MatchDto input) {
        Hero hero = heroService.findById(input.getHero_id());
        return new Match(
                input.getMatch_id(),
                isRadiantWin(input.getRadiant_win()),
                (input.getDuration() / 60) + " min",
                input.getGame_mode(),
                hero.getName(),
                hero.getIconUrl(),
                input.getVersion(),
                input.getKills(),
                input.getDeaths(),
                input.getAssists());
    }

    private Hero toHero(HeroDto input) {
        return new Hero(
                input.getId(),
                input.getLocalized_name(),
                UrlMappingConstants.DOTABUFF_URL_HEROES_ASSERTS +
                        input.getLocalized_name().toLowerCase()
                                .replace(" ", "-")
                                .replace("'", "")
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

    private String isRadiantWin(Boolean input) {
        if (input) {
            return "Radiant Victory";
        } else {
            return "Dire Victory";
        }
    }
}
