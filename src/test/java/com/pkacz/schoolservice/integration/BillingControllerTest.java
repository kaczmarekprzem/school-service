package com.pkacz.schoolservice.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = {"file:src/test/resources/insert_test_data.sql"},
        config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"file:src/test/resources/clear_test_data.sql"},
        config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED),
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@AutoConfigureMockMvc
@SpringBootTest
public class BillingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldGetSchoolBilling() throws Exception {
        mockMvc.perform(get("/school/1/billing?year=2024&month=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalFees").value(170.00))
                .andExpect(jsonPath("$.parentBillingReports[0].totalFees").value(120.00));
    }

    @Test
    void shouldGetParentBillingReportDto() throws Exception {
        mockMvc.perform(get("/school/1/parent/1/billing?year=2024&month=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalFees").value(120.00));
    }
}
