package org.example.api;

import org.example.dao.entity.Account;
import org.example.dao.entity.User;
import org.example.dao.repository.AccountRepository;
import org.example.dao.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private UserRepository userRepo;

    @BeforeEach
    void setUp() {

        User user1 = new User(1L, "user1", LocalDate.of(1990, 10, 20), "Password", null, null, null, null);
        User user2 = new User(2L, "user2", LocalDate.of(1990, 10, 20), "Password", null, null, null, null);
        userRepo.saveAll(List.of(user1, user2));


        Account sender = new Account(1L, new BigDecimal("100.00"), user1, new BigDecimal("20.00"));

        Account receiver = new Account(2L, new BigDecimal("10.00"), user1, new BigDecimal("0.00"));
        accountRepo.saveAll(List.of(sender, receiver));
    }

    @Test
    @WithMockUser("user1")
    void transferMoney_success() throws Exception {
        String requestBody = """
                {
                  "receiverId": 2,
                  "amount": 30
                }
                """;

        mockMvc.perform(post("/api/v1/account/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());

        Account sender = accountRepo.findByUserId(1L).orElseThrow();
        Account receiver = accountRepo.findByUserId(2L).orElseThrow();

        assertEquals(new BigDecimal("70"), sender.getBalance());
        assertEquals(new BigDecimal("40"), receiver.getBalance());
    }
}
