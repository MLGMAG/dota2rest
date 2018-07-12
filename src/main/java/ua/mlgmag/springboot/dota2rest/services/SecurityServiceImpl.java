package ua.mlgmag.springboot.dota2rest.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ua.mlgmag.springboot.dota2rest.definition.SecurityService;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Override
    public String findLoggedInUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof UserDetails)) {
            return ((UserDetails) auth.getPrincipal()).getUsername();
        }

        return null;
    }

}
