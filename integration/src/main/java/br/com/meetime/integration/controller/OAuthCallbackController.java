package br.com.meetime.integration.controller;


import br.com.meetime.integration.dto.OAuthTokenResponse;
import br.com.meetime.integration.service.OAuthTokenService;
import br.com.meetime.integration.service.TokenCacheService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class OAuthCallbackController {
    private final OAuthTokenService oAuthTokenService;
    private final TokenCacheService tokenCacheService;


    public OAuthCallbackController(OAuthTokenService oAuthTokenService, TokenCacheService tokenCacheService) {
        this.oAuthTokenService = oAuthTokenService;
        this.tokenCacheService = tokenCacheService;
    }


    @GetMapping("/callback")
    public ResponseEntity<?> handleOAuthCallback(@RequestParam("code") String code) {
      try {
          OAuthTokenResponse tokenResponse = oAuthTokenService.exchangeCodeForToken(code);
          tokenCacheService.saveToken(tokenResponse.getAccessToken(), tokenResponse.getRefreshToken(), tokenResponse.getExpiresIn());
          return ResponseEntity.ok(tokenResponse);
      } catch (Exception e) {
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
      }
    }
}
