package ua.mlgmag.springboot.dota2rest.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ua.mlgmag.springboot.dota2rest.definition.SecurityService;

@Service
@Slf4j
public class SecurityServiceImpl implements SecurityService {

    @Override
    public String findLoggedInUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof UserDetails)) {
            log.info("findLoggedInUsername {}", ((UserDetails) auth.getPrincipal()).getUsername());
            return ((UserDetails) auth.getPrincipal()).getUsername();
        }

        log.info("findLoggedInUsername {}", "null");
        return null;
    }

}
