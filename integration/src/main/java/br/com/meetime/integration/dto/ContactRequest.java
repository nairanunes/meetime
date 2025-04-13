package br.com.meetime.integration.dto;

import lombok.Data;

@Data
public class ContactRequest {
    private String event;
    private ContactData data;

    @Data
    public static class ContactData {
        private String email;
        private String firstName;
        private String lastName;
    }
}