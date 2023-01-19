package com.charset.rewardpoints;

import org.junit.jupiter.api.*;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RewardCalculationTest {
    private MockMvc mockMvc;
    @Autowired
    WebApplicationContext context;
    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    @DisplayName("Verify the reward point API is working as expected")
    // Testing our Microservice for customer 1002
    public void testCustomerRewardPoints() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customers/1002/rewards")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(1002))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastMonthRewardPoints").value(110))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastSecondMonthRewardPoints").value(354))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastThirdMonthRewardPoints").value(114))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalRewards").value(578));
        // Test passed. Microservice up and running and working as expected
    }

    @Test
    @DisplayName("Verify the over all health of the applications")
    // Testing our Microservice for customer 1002
    public void verifyOverallHealth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/health")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("UP"));

    }

    @Test
    @DisplayName("Verify the Database health of the applications")
    // Testing our Microservice for customer 1002
    public void verifyDatabaseHealth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/health")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.components.db.status").value("UP"));

    }

    @Test
    @DisplayName("Verify the Database name of the applications")
    // Testing our Microservice for customer 1002
    public void verifyDatabaseName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/health")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.components.db.details.database").value("H2"));

    }

    @Test
    @DisplayName("Verify the ping status of the applications")
    // Testing our Microservice for customer 1002
    public void verifyPingStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/health")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.components.ping.status").value("UP"));

    }
}
