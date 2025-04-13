package br.com.meetime.integration.service;

import br.com.meetime.integration.dto.OAuthTokenResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

@Service
public class TokenCacheService {

    private static final Logger log = LoggerFactory.getLogger(TokenCacheService.class);

    private String refreshToken;
    private Instant expiresAt;

    @CachePut(value = "accessToken", key = "'token'")
    public String saveToken(String token, String refreshToken, int expiresIn) {
        this.refreshToken = refreshToken;
        this.expiresAt = Instant.now().plusSeconds(expiresIn);
        log.info("Token atualizado, expiração em: {}", expiresAt);
        return token;
    }

    @Cacheable(value = "accessToken", key = "'token'")
    public String getToken() {
        return null;
    }

    @Cacheable(value = "refreshToken", key = "'token'")
    public String getRefreshToken() {
        return refreshToken;
    }

    @CacheEvict(value = "accessToken", key = "'token'")
    public void clearAccessTokenCache() {
        log.info("Access token limpo do cache.");
    }

    @CacheEvict(value = "refreshToken", key = "'token'")
    public void clearRefreshTokenCache() {
        log.info("Refresh token limpo do cache.");
    }

    public String getValidTokenOrRefresh(OAuthTokenServiceImpl oAuthTokenService) {
        String token = getToken();

        if (token == null || isExpired()) {
            if (refreshToken == null) {
                log.warn("Refresh token não encontrado ou expirado.");
                return null;
            }

            log.info("Token expirado ou ausente. Tentando refresh com o refresh token.");
            OAuthTokenResponse refreshed = oAuthTokenService.refreshAccessToken(refreshToken);


            clearAccessTokenCache();
            clearRefreshTokenCache();

            return saveToken(refreshed.getAccessToken(), refreshed.getRefreshToken(), refreshed.getExpiresIn());
        }

        log.info("Token válido encontrado.");
        return token;
    }

    private boolean isExpired() {
        return expiresAt == null || Instant.now().isAfter(expiresAt);
    }
}
