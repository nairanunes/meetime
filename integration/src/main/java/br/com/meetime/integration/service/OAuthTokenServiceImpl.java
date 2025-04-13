package br.com.meetime.integration.service;

import br.com.meetime.integration.dto.OAuthTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class OAuthTokenServiceImpl implements OAuthTokenService {
    @Value("${hubspot.client-id}")
    private String clientId;

    @Value("${hubspot.client-secret}")
    private String clientSecret;

    @Value("${hubspot.token-url}")
    private String tokenUrl;
    @Value("${hubspot.redirect-uri}")
    private String redirectUri;


    public OAuthTokenResponse exchangeCodeForToken(String code) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("redirect_uri", redirectUri);
        map.add("code", code);


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        try {
            ResponseEntity<OAuthTokenResponse> response = restTemplate.exchange(
                    tokenUrl, HttpMethod.POST, entity, OAuthTokenResponse.class);
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Erro ao trocar código por token: " + e.getMessage(), e);
        }
    }

    @Override
    public OAuthTokenResponse refreshAccessToken(String refreshToken) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "refresh_token");
        map.add("client_id", clientId);
        map.add("client_secret", clientSecret);
        map.add("refresh_token", refreshToken);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        try {
            ResponseEntity<OAuthTokenResponse> response = restTemplate.exchange(
                    tokenUrl, HttpMethod.POST, entity, OAuthTokenResponse.class);
            return response.getBody();
        } catch (RestClientException e) {
            throw new RuntimeException("Erro ao renovar token de acesso: " + e.getMessage(), e);
        }
    }

}
