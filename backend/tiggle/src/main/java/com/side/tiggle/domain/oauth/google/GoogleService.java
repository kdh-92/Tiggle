package com.side.tiggle.domain.oauth.google;

import com.side.tiggle.domain.member.Member;
import com.side.tiggle.domain.oauth.OAuthService;
import com.side.tiggle.global.config.AppEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoogleService extends OAuthService {

    @Autowired
    GoogleService(AppEnvironment appEnvironment) {
        super(appEnvironment);
        this.provider = appEnvironment.google;
    }
    @Override
    protected String getAccessToken(String authCode) {
        return null;
    }

    @Override
    protected Member getUserResource(String authCode) {
        return null;
    }
}
