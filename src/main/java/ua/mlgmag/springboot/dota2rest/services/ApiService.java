package ua.mlgmag.springboot.dota2rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.mlgmag.springboot.dota2rest.constants.PlayerConstants;
import ua.mlgmag.springboot.dota2rest.dto.PlayerDto;
import ua.mlgmag.springboot.dota2rest.dto.PlayerProfileDto;
import ua.mlgmag.springboot.dota2rest.model.Player;

@Service
public class ApiService {

    private final OpenDotaApiClient openDotaApiClient;

    @Autowired
    public ApiService(OpenDotaApiClient openDotaApiClient) {
        this.openDotaApiClient = openDotaApiClient;
    }

    public Player findPlayerById(int id) {
        return toPlayer(openDotaApiClient.findPlayerById(id));
    }

    private Player toPlayer(PlayerDto input) {

        PlayerDto validateInput = playerDtoValidation(input);
        PlayerProfileDto profileDto = validateInput.getProfile();
        String steamId64 = String.valueOf(PlayerConstants.ZERO + profileDto.getAccount_id());

        return new Player(
                profileDto.getAccount_id(),
                profileDto.getName(),
                String.valueOf(PlayerConstants.ZERO + profileDto.getAccount_id()),
                profileDto.getAvatarmedium(),
                PlayerConstants.PLAYER_PROFILE_PREFIX.concat(steamId64),
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

        if (input.getProfile().getName() == null || input.getProfile().getName().equals("")) {
            input.getProfile().setName("Not Set");
        }

        return input;
    }
}
