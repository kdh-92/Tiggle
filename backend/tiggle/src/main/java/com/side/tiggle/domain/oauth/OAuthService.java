package com.side.tiggle.domain.oauth;

import com.side.tiggle.domain.member.Member;
import com.side.tiggle.global.config.AppEnvironment;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class OAuthService {

    private final AppEnvironment appEnvironment;
    protected OAuthProvider provider;

    @Autowired
    protected OAuthService(AppEnvironment appEnvironment) {
        this.appEnvironment = appEnvironment;
    }

    protected abstract String getAccessToken(String authCode);
    protected abstract Member getUserResource(String authCode);

    public void login(String authCode){
        String accessToken = getAccessToken(authCode);
        /**
         * TODO : Process user info
         */
    }


}
