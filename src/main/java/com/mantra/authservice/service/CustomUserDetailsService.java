package com.mantra.authservice.service;


import com.mantra.authservice.exception.exception.ClientException;
import com.mantra.authservice.feign.FinanceFeign;
import com.mantra.authservice.model.CustomUser;

import com.mantra.authservice.model.LoginDTO;
import com.mantra.authservice.tools.Print;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final FinanceFeign financeFeign;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{


        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.
                getRequestAttributes())).getRequest();

        int mantraKey = Integer.parseInt(request.getParameter("mantraKey"));
        List<LoginDTO> loginDTO;

        try {
            try {

                loginDTO = financeFeign.getLoginDto(username, mantraKey);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClientException("error.feign.connection.refused", "Finance-Service");
            }
            if (loginDTO != null && !loginDTO.isEmpty()) {
                return new CustomUser(loginDTO);
            } else {
                throw new UsernameNotFoundException("User " + username + " was not found in the database");
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

    }


}
