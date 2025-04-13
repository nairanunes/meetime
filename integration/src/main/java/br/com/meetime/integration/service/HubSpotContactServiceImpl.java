package br.com.meetime.integration.service;

import br.com.meetime.integration.dto.ContactRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class HubSpotContactServiceImpl implements ContactService{

    private final RestTemplate restTemplate = new RestTemplate();

    private final TokenCacheService tokenCacheService;
    private final  OAuthTokenService oAuthTokenService;
    private final RateLimitService rateLimitService;

    public HubSpotContactServiceImpl(TokenCacheService tokenCacheService, OAuthTokenService oAuthTokenService, RateLimitService rateLimitService) {
        this.tokenCacheService = tokenCacheService;
        this.oAuthTokenService = oAuthTokenService;
        this.rateLimitService = rateLimitService;
    }
    @Value("${hubspot.api.contacts-url}")
    private String contactsUrl;



    @Override
    public void createContact(ContactRequest request) {
        if (!rateLimitService.checkAndUpdateRateLimit()) {
            throw new RuntimeException("Rate limit excedido.");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String accessToken = tokenCacheService.getValidTokenOrRefresh((OAuthTokenServiceImpl) oAuthTokenService);
        if (accessToken == null) {
            throw new IllegalStateException("Token de acesso inválido ou não disponível.");
        }

        headers.setBearerAuth(accessToken);

        Map<String, Object> properties = new HashMap<>();
        properties.put("email", request.getData().getEmail());
        properties.put("firstname", request.getData().getFirstName());
        properties.put("lastname", request.getData().getLastName());

        Map<String, Object> body = new HashMap<>();
        body.put("properties", properties);

        HttpEntity<Map<String, Object>> entity =  new HttpEntity<>(body, headers);

        restTemplate.postForEntity(contactsUrl, entity, String.class);

    }
}
