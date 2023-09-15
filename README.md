# Pages

Backend para gerenciamento de sites da Vai De Digital.

## Inicialização

```bash
# Inicializar o banco de dados
docker-compose up -d

# Inicializar o servidor
./mvnw spring-boot:run
```

A aplicação estará disponível em <http://localhost:8080>.

A documentação dos endpoints está em <http://localhost:8080/swagger-ui/index.html>.

## Rodar os testes com geração do relatório de cobertura e mutações

```bash
./mvnw clean jacoco:prepare-agent install jacoco:report test-compile org.pitest:pitest-maven:mutationCoverage
```

Os relatórios estarão disponíveis em:

- Testes de cobertura: [`target/site/jacoco/index.html`](target/site/jacoco/index.html)
- Testes de mutação: [`target/pit-reports/index.html`](target/pit-reports/index.html)
