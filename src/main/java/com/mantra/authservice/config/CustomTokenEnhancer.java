package com.mantra.authservice.config;


import java.util.LinkedHashMap;
import java.util.Map;

import com.mantra.authservice.model.CustomUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class CustomTokenEnhancer extends JwtAccessTokenConverter  {
    public static final String USER_ID = "userId";
    public static final String PERSON_ID = "personId";
    public static final String FIRST_NAME = "name";
    public static final String USER_NAME = "userName";
    public static final String MANTRA_KEY = "mantraKey";
    public static final String COMPANY_ID = "companyId";


    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        CustomUser user = (CustomUser) authentication.getPrincipal();
        Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());
        if (user.getUserId() != null)
            info.put(USER_ID, user.getUserId());
        if (user.getPersonId() != null)
            info.put(PERSON_ID, user.getPersonId());
        if (user.getName() != null)
            info.put(FIRST_NAME, user.getName());
        if (user.getUsername() != null)
            info.put(USER_NAME, user.getUsername());

        if (user.getMantraKey() != null)
            info.put(MANTRA_KEY, user.getMantraKey());

        if (user.getCompanyId() != null)
            info.put(COMPANY_ID, user.getCompanyId());

        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(info);
        return super.enhance(customAccessToken, authentication);
    }
}
