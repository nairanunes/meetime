package br.com.meetime.integration.service;

import br.com.meetime.integration.dto.ContactRequest;

public interface ContactService {
    void createContact(ContactRequest request);
}
