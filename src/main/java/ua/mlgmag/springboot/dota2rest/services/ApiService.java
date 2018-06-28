package ua.mlgmag.springboot.dota2rest.services;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mlgmag.springboot.dota2rest.constants.PlayerConstants;
import ua.mlgmag.springboot.dota2rest.dto.PlayerDto;
import ua.mlgmag.springboot.dota2rest.dto.PlayerProfileDto;
import ua.mlgmag.springboot.dota2rest.dto.ProPlayerDto;
import ua.mlgmag.springboot.dota2rest.model.Player;
import ua.mlgmag.springboot.dota2rest.model.ProPlayer;
import ua.mlgmag.springboot.dota2rest.model.Team;
import ua.mlgmag.springboot.dota2rest.repository.PlayerRepository;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Service
public class ApiService {

    private final OpenDotaApiClient openDotaApiClient;

    private final PlayerRepository playerRepository;

    @Autowired
    public ApiService(OpenDotaApiClient openDotaApiClient, PlayerRepository playerRepository) {
        this.openDotaApiClient = openDotaApiClient;
        this.playerRepository = playerRepository;
    }

    public List<ProPlayer> findAllProPlayers() {
        return Arrays.stream(openDotaApiClient.findAllProPlayers())
                .map(this::toProPlayer).collect(collectingAndThen(toList(), ImmutableList::copyOf));
    }

    public Player findPlayerById(int id) {
        if (!playerRepository.existsById(id)) {
            playerRepository.save(toPlayer(openDotaApiClient.findPlayerById(id)));
        }
        return toPlayer(openDotaApiClient.findPlayerById(id));
    }

    private ProPlayer toProPlayer(@NonNull ProPlayerDto input) {
        return new ProPlayer(
                input.getAccount_id(),
                String.valueOf(PlayerConstants.ZERO + input.getAccount_id()),
                input.getAvatarmedium(),
                input.getProfileurl(),
                input.getPersonaname(),
                input.getLast_login(),
                input.getFull_history_time(),
                input.getCheese(),
                input.getFh_unavailable(),
                input.getLoccountrycode(),
                input.getName(),
                input.getCountry_code(),
                input.getFantasy_role(),
                new Team(input.getTeam_id(), input.getTeam_name(), input.getTeam_tag()),
                input.getIs_locked(),
                input.getIs_pro(),
                input.getLocked_until());
    }

    private Player toPlayer(@NonNull PlayerDto input) {

        PlayerProfileDto profileDto = input.getProfile();
        String steamId64 = String.valueOf(PlayerConstants.ZERO + profileDto.getAccount_id());
        String profileUrl = PlayerConstants.PLAYER_PROFILE_PREFIX.concat(steamId64);

        return new Player(
                profileDto.getAccount_id(),
                profileDto.getPersonaname(),
                profileDto.getName(),
                steamId64,
                profileDto.getAvatarmedium(),
                profileUrl,
                input.getSolo_competitive_rank(),
                input.getCompetitive_rank());
    }

//    public WinAndLoss findWindAndLoss() {
//        return openDotaApiClient.getWinAndLoss();
//    }
}
