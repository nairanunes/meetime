package br.com.meetime.integration.controller;

import br.com.meetime.integration.dto.ContactRequest;
import br.com.meetime.integration.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hubspot")
public class ContactController {
    private  final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("/contacts")
    public ResponseEntity<String> createContact(@RequestBody ContactRequest request){
        try {

            contactService.createContact(request);
            return ResponseEntity.ok("Contato criado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao criar contato: " + e.getMessage());
        }
    }
}
