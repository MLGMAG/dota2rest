package ua.mlgmag.springboot.dota2rest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.mlgmag.springboot.dota2rest.dto.PeerDto;
import ua.mlgmag.springboot.dota2rest.dto.PlayerDto;

import java.net.URI;
import java.net.URISyntaxException;

@Component
public class OpenDotaApiClient {

    private final RestTemplate restTemplate;

    @Autowired
    public OpenDotaApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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

    public PeerDto[] findPeersByPlayerId(int id) {
        StringBuilder url = new StringBuilder("https://api.opendota.com/api/players/");
        url.append(id).append("/peers");
        try {
            return restTemplate.getForObject(new URI(url.toString()), PeerDto[].class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
