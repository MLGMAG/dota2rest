package ua.mlgmag.springboot.dota2rest.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.mlgmag.springboot.dota2rest.constants.PlayerConstants;
import ua.mlgmag.springboot.dota2rest.dto.HeroDto;
import ua.mlgmag.springboot.dota2rest.dto.MatchDto;
import ua.mlgmag.springboot.dota2rest.dto.PeerDto;
import ua.mlgmag.springboot.dota2rest.dto.PlayerDto;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@Component
@Slf4j
public class OpenDotaApiClient {

    private final RestTemplate restTemplate;

    @Autowired
    public OpenDotaApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public Optional<PlayerDto> findPlayerById(int id) {
        StringBuilder url = new StringBuilder(PlayerConstants.HTTP_REQUEST_PLAYERS);
        url.append(id);
        try {
            log.info("findPlayerById {}", id);
            return Optional.ofNullable(restTemplate.getForObject(new URI(url.toString()), PlayerDto.class));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public PeerDto[] findPeersByPlayerId(int id) {
        StringBuilder url = new StringBuilder(PlayerConstants.HTTP_REQUEST_PLAYERS);
        url.append(id).append("/peers");
        try {
            log.info("findPeersByPlayerId {}", id);
            return restTemplate.getForObject(new URI(url.toString()), PeerDto[].class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public MatchDto[] findMatchesByPlayerId(Integer id) {
        StringBuilder url = new StringBuilder(PlayerConstants.HTTP_REQUEST_PLAYERS);
        url.append(id).append("/matches");
        try {
            log.info("findMatchesByPlayerId {}", id);
            return restTemplate.getForObject(new URI(url.toString()), MatchDto[].class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public HeroDto[] findAllHeroes() {
        StringBuilder url = new StringBuilder(PlayerConstants.HTTP_REQUEST_OPEN_DOTA_API);
        url.append("heroes");
        log.info("findAllHeroes");
        try {
            return restTemplate.getForObject(new URI(url.toString()), HeroDto[].class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
