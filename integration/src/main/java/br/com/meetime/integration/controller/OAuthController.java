package br.com.meetime.integration.controller;

import br.com.meetime.integration.dto.OAuthUrlResponse;
import br.com.meetime.integration.service.OAuthUrlService;
import br.com.meetime.integration.service.OAuthUrlServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class OAuthController {
    @Value("${hubspot.client-id}")
    private String clientId;

    @Value("${hubspot.redirect-uri}")
    private String redirectUri;
    private final OAuthUrlService oAuthUrlService;

    public OAuthController(OAuthUrlService oAuthUrlService) {
        this.oAuthUrlService = oAuthUrlService;
    }

 @GetMapping("/authorize-url")
 public OAuthUrlResponse generateAuthorizeUrl() {
     String authorizationUrl = "https://app.hubspot.com/oauth/authorize?" +
             "client_id=" + clientId +
             "&redirect_uri=" + redirectUri +
             "&scope=crm.objects.contacts.read" +  // Escopo correto de leitura de contatos
             "&response_type=code";

     return new OAuthUrlResponse(authorizationUrl);
 }
}
