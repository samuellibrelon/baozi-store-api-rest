package br.com.uninter.baozistore;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class BaoziStoreApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void executaCrudCompletoDaBaoziStore() throws Exception {
        Long clienteId = extrairId(mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Samuel Librelon Pinheiro Lopes4676351",
                                  "clienteDesde": "2026-08-23"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Samuel Librelon Pinheiro Lopes4676351"))
                .andReturn());

        Long produtoId = extrairId(mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Baozi de carne suína",
                                  "preco": 12.50,
                                  "estoque": true
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estoque").value(true))
                .andReturn());

        Long pedidoId = extrairId(mockMvc.perform(post("/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "clienteId": %d,
                                  "produtoId": %d,
                                  "quantidade": 3
                                }
                                """.formatted(clienteId, produtoId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.quantidade").value(3))
                .andReturn());

        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        mockMvc.perform(get("/produtos/{id}", produtoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Baozi de carne suína"));
        mockMvc.perform(get("/pedidos/{id}", pedidoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clienteId").value(clienteId));

        mockMvc.perform(put("/pedidos/{id}", pedidoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "clienteId": %d,
                                  "produtoId": %d,
                                  "quantidade": 4
                                }
                                """.formatted(clienteId, produtoId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.quantidade").value(4));

        mockMvc.perform(delete("/pedidos/{id}", pedidoId))
                .andExpect(status().isNoContent());
        mockMvc.perform(delete("/produtos/{id}", produtoId))
                .andExpect(status().isNoContent());
        mockMvc.perform(delete("/clientes/{id}", clienteId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/pedidos/{id}", pedidoId))
                .andExpect(status().isNotFound());
    }

    private Long extrairId(MvcResult resultado) throws Exception {
        JsonNode json = objectMapper.readTree(resultado.getResponse().getContentAsString());
        return json.get("id").asLong();
    }
}
