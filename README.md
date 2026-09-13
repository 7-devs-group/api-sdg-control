# 🏛️ SDG Control — Administração e Controlo

API administrativa da plataforma SDG para gestão de **organizações**, **hotéis**, **slugs** e **provisionamento**.
Responsável pela base `sdg_control` no namespace `platform-system`.

## 🛠️ Stack

- **Java 21** (Corretto)
- **Spring Boot 3.3.2**
- **Spring Web / Actuator / Validation**
- **Lombok**
- **Springdoc OpenAPI** (Swagger UI)
- **Maven**

## ▶️ Como Executar

### Pré-requisitos

- JDK 21+
- Maven 3.9+

### Executar

```bash
cd sdg-control
mvn clean install
mvn spring-boot:run
```

### Acessos

| Recurso | URL |
| --- | --- |
| Health check | http://localhost:8082/api/health |
| Actuator Health | http://localhost:8082/actuator/health |
| Swagger UI | http://localhost:8082/swagger-ui.html |
| OpenAPI JSON | http://localhost:8082/api-docs |

## 📦 Escopo Futuro

- Organizações e hotéis (CRUD)
- Gestão de slugs e namespaces
- Orquestração de provisionamento (namespace, PostgreSQL, API por hotel)
- Planos e subscrições
- Auditoria administrativa
