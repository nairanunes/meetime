package br.com.meetime.integration.service;

import br.com.meetime.integration.dto.OAuthTokenResponse;

public interface OAuthTokenService {
    OAuthTokenResponse exchangeCodeForToken(String code);
    OAuthTokenResponse refreshAccessToken(String refreshToken);
}
