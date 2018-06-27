package ua.mlgmag.springboot.dota2rest.services;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.mlgmag.springboot.dota2rest.dto.PlayerDto;
import ua.mlgmag.springboot.dota2rest.dto.ProPlayerDto;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class OpenDotaApiClient {

    private final RestTemplate restTemplate;

    public OpenDotaApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ProPlayerDto[] findAllProPlayers() {
        String url = "https://api.opendota.com/api/proPlayers";
        try {
            return restTemplate.getForObject(new URI(url), ProPlayerDto[].class);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public PlayerDto findPlayerById(int id) {
        StringBuilder url = new StringBuilder("https://api.opendota.com/api/players/");
        url.append(id);
        try {
            return restTemplate.getForObject(new URI(url.toString()), PlayerDto.class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

//    public WinAndLoss getWinAndLoss() {
//        String url = "https://api.opendota.com/api/players/88018/wl";
//        try {
//            return restTemplate.getForObject(new URI(url), WinAndLoss.class);
//        } catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
