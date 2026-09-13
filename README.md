# sdg-control

API de Administração e Controlo da plataforma SDG. É a fonte oficial de hotéis, slugs e estado do hotel, ligada ao PostgreSQL `sdg_control` no namespace `platform-system`.

Usa Java 25, Spring Boot 4.1.1, Spring WebMVC, JPA, PostgreSQL, Validation, Actuator e OpenAPI.

## Fluxo Control → Auth

Ao criar um hotel, a API grava o hotel e o evento `HotelCreated` na outbox na mesma transação. Em seguida sincroniza a projeção mínima do hotel na `api-auth-identity-core`. A Auth mantém `auth_hotels` e pode então criar vínculos locais de utilizador por `hotel_id`.

As APIs não partilham acesso SQL. A comunicação interna usa a chave `CONTROL_TO_AUTH_KEY`, injetada por Secret no Kubernetes.

## Endpoints iniciais

| Método | Rota | Finalidade |
| --- | --- | --- |
| `POST` | `/api/control/hotels` | Criar hotel com `name` e `slug`. |
| `GET` | `/api/control/hotels` | Listar hotéis. |
| `GET` | `/api/control/hotels/{hotelId}` | Consultar hotel. |
| `POST` | `/api/control/hotels/{hotelId}/memberships` | Criar vínculo de utilizador na Auth. |

## Execução local

```bash
export DB_PASSWORD='...'
export CONTROL_TO_AUTH_KEY='...'
mvn test
mvn spring-boot:run
```

Por padrão, a aplicação procura PostgreSQL em `localhost:5432/sdg_control`. Para integração local, defina também `AUTH_API_URL`.
