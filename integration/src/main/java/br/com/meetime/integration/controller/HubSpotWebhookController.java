package br.com.meetime.integration.controller;


import br.com.meetime.integration.dto.ContactRequest;
import br.com.meetime.integration.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HubSpotWebhookController {
    private final ContactService contactService;

    public HubSpotWebhookController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/webhooks/contact-creation")
    public ResponseEntity<?> handleContactCreationWebhook(@RequestBody ContactRequest contactRequest) {
        try {
            contactService.createContact(contactRequest);
            return ResponseEntity.ok("Webhook processado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao processar Webhook: " + e.getMessage());
        }
    }
}

