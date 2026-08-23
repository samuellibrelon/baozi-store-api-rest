# Baozi Store - API REST

Trabalho de Desenvolvimento Web Back-End de **Samuel Librelon Pinheiro Lopes (RU 4676351)**.

## Tecnologias

- Java 17
- Spring Boot 3.5.16
- Spring Web
- Spring Data JPA
- Banco relacional H2
- Maven Wrapper

## Como executar

Pré-requisito: JDK 17 ou JDK 21 configurado no computador.

No Windows, abra um terminal na pasta do projeto e execute:

```powershell
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

A API ficará disponível em `http://localhost:8080`.

## Banco H2

O banco é criado automaticamente na pasta `data`. O console está disponível em:

- Endereço: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:file:./data/baozistore`
- Usuário: `sa`
- Senha: deixar em branco

## Endpoints

| Entidade | Criar | Listar | Consultar | Atualizar | Apagar |
|---|---|---|---|---|---|
| Cliente | `POST /clientes` | `GET /clientes` | `GET /clientes/{id}` | `PUT /clientes/{id}` | `DELETE /clientes/{id}` |
| Produto | `POST /produtos` | `GET /produtos` | `GET /produtos/{id}` | `PUT /produtos/{id}` | `DELETE /produtos/{id}` |
| Pedido | `POST /pedidos` | `GET /pedidos` | `GET /pedidos/{id}` | `PUT /pedidos/{id}` | `DELETE /pedidos/{id}` |

## Dados usados no estudo de caso

Cliente:

```json
{
  "nome": "Samuel Librelon Pinheiro Lopes4676351",
  "clienteDesde": "2026-08-23"
}
```

Produto:

```json
{
  "nome": "Baozi de carne suína",
  "preco": 12.50,
  "estoque": true
}
```

Pedido, considerando que o cliente e o produto receberam ID 1:

```json
{
  "clienteId": 1,
  "produtoId": 1,
  "quantidade": 3
}
```

## Postman

Importe o arquivo `postman/Baozi-Store.postman_collection.json`. Execute as pastas numeradas na ordem apresentada. Os testes dos três cadastros salvam automaticamente os IDs retornados pela API.

Para os prints do relatório, deixe visíveis:

- método e URL da requisição;
- corpo JSON, quando houver;
- status HTTP da resposta;
- JSON retornado pela API.

Execute a pasta de exclusões somente depois de capturar os demais prints.

## Testes automatizados

```powershell
.\mvnw.cmd test
```

O teste de integração cria um cliente, um produto e um pedido; consulta e atualiza os dados; apaga os registros; e verifica a resposta de recurso não encontrado.

## Materiais do relatório

- `docs/roteiro-relatorio.md`: texto personalizado e estrutura do PDF.
- `docs/diagrama-casos-de-uso.png`: diagrama UML pronto para o relatório.
- `docs/diagrama-casos-de-uso.puml`: código-fonte editável do diagrama UML.
