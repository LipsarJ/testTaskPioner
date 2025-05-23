package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;


@SpringBootTest(classes = TestTaskPionerApplication.class)
@ActiveProfiles("test")
@Testcontainers
class TestTaskPionerApplicationTests {
    @Test
    void contextLoads() {
    }

}


