package br.com.meetime.integration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OAuthUrlResponse {
    private  String authorizationUrl;
}
