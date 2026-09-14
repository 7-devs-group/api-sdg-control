# Collection Bruno — fluxo de identidade

Abra esta pasta no Bruno e selecione o ambiente `local`.

1. Inicie dois port-forwards:
   - `kubectl -n platform-system port-forward svc/api-sdg-control 18080:80`
   - `kubectl -n platform-system port-forward svc/api-auth-identity-core 18081:8080`
2. Execute os requests 01 e 02 apenas se os hotéis ainda não existirem.
3. Execute o request 03 e copie os UUIDs para `hotelTestId` e `newHotelId` no ambiente.
4. Preencha as senhas locais com pelo menos 12 caracteres.
5. Execute 04, 05 e 06 para criar os vínculos.
6. Execute 07, copie `selectionToken`, execute 08 e copie `accessToken`.
7. Execute 09. A resposta deve mostrar o utilizador, o `hotelId` selecionado e a role.

O mesmo admin fica vinculado aos dois hotéis, demonstrando o login em duas etapas. O utilizador de teste fica apenas no Hotel Teste.
