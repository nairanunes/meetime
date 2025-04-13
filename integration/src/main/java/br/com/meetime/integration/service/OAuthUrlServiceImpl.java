package br.com.meetime.integration.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class OAuthUrlServiceImpl implements OAuthUrlService {

    @Value("${hubspot.client-id}")
    private String clientId;

    @Value("${hubspot.redirect-uri}")
    private String redirectUri;

    public String  generateAuthorizationUrl(){
        return UriComponentsBuilder
                .fromUriString("https://app.hubspot.com/oauth/authorize")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", "contacts")
                .queryParam("response_type", "code")
                .build()
                .toUriString();
    }
}
