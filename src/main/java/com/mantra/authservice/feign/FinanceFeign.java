package com.mantra.authservice.feign;

import com.mantra.authservice.model.LoginDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("finance-service")
public interface FinanceFeign {

    String prefix = "/user/";

    @GetMapping(prefix + "get-login-dto")
    List<LoginDTO> getLoginDto(@RequestParam String username,@RequestParam int mantraKey);
}
