Instruções de Execução da Aplicação

1. **Geração da Authorization URL**:

- Acesse o endpoint '/oauth/authorize-url' para gerar a URL de autorização.
- Use as seguintes credenciais:
  user: meetime
  senha: 123456
- Exemplo de Request: GET http://localhost:8080/oauth/authorize-url
- Exemplo de Response:
  {"authorizationUrl":"https://app.hubspot.com/oauth/authorize?client_id=ce0601ef-03b6-4684-b25b-671d91e7a514&redirect_uri=http://localhost:8080/oauth/callback&scope=crm.objects.contacts.read&response_type=code"}
- Cole a URL no navegador e siga as intruções do HubSpot
- Você vai receber um response como o seguinte formato:
  {"access_token":"CI2U5ojjMhIHAAEAQAAAARiSn9cXIJy85iUo9rKPBTIUJQhIyuKtoZfXL3-5WGg1r07bFgg6MAAAAEEAAAAAAAAAAAAAAAAAgAAAAAAAAAAAACAAAAAAAOABAAAAAAAAAAAAAAAQAkIUGLdXCxIWgQektdgNpK46rFC6DHlKA25hMVIAWgBgAGicvOYlcAA","expires_in":1800,"refresh_token":"na1-70ed-7ac1-436d-9051-d89f0d3059ee","token_type":"bearer","scope":null}
- Guarde o toke, ele será necessário para autentição nas resquisições.

2. **Criação de Contatos**:

- Acesse o endpoint '/contacts' para criar um novo contato no HubSpot.
- Insira o token para se autenticar.
- Exemplo de Request: POST https://api.hubapi.com/contacts/v1/contact
  Body:
  {
  "email": "teste@example.com",
  "firstName": "João",
  "lastName": "Silva"
  }
- Exemplo de Response:
  {
  "status": "success",
  "message": "Contato criado com sucesso!"
  }

4. **Recebimento de Webhook para Criação de Contatos**:

- O HubSpot enviará notificações de criação de contatos via webhook para o endpoint
  '/webhooks/contact-creation'.
- Exemplo de Request (POST):
  Body:
  {
  "event": "contact.creation",
  "data": {
  "email": "teste@example.com",
  "firstName": "João",
  "lastName": "Silva"
  }
  }
- Exemplo de Response:
  {
  "status": "success",
  "message": "Webhook processado com sucesso."
  }# meetime
