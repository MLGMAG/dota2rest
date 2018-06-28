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

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Service
public class ApiService {

    private final OpenDotaApiClient openDotaApiClient;

    @Autowired
    public ApiService(OpenDotaApiClient openDotaApiClient) {
        this.openDotaApiClient = openDotaApiClient;
    }

    public List<ProPlayer> findAllProPlayers() {
        return Arrays.stream(openDotaApiClient.findAllProPlayers())
                .map(this::toProPlayer).collect(collectingAndThen(toList(), ImmutableList::copyOf));
    }

    public Player findPlayerById(int id) {
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

        PlayerDto validateInput = playerDtoValidation(input);
        PlayerProfileDto profileDto = validateInput.getProfile();
        String steamId64 = String.valueOf(PlayerConstants.ZERO + profileDto.getAccount_id());
        String profileUrl = PlayerConstants.PLAYER_PROFILE_PREFIX.concat(steamId64);


        return new Player(
                profileDto.getAccount_id(),
                profileDto.getName(),
                steamId64,
                profileDto.getAvatarmedium(),
                profileUrl,
                validateInput.getSolo_competitive_rank(),
                validateInput.getCompetitive_rank());
    }

    private PlayerDto playerDtoValidation(PlayerDto input) {

        if (input.getSolo_competitive_rank() == null) {
            input.setSolo_competitive_rank("Not calibrated");
        }

        if (input.getCompetitive_rank() == null) {
            input.setCompetitive_rank("Not calibrated");
        }

        if (input.getProfile().getName().equals("")) {
            input.getProfile().setName("Not Set");
        }

        return input;
    }
}
