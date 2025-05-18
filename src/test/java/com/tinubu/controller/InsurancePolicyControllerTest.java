package com.tinubu.controller;

import com.tinubu.config.ClockConfig;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(ClockConfig.class)
@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
class InsurancePolicyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    @DisplayName("Should return all insurance policies with expected JSON structure")
    void shouldListAllPolicies() throws Exception {
        mockMvc.perform(get("/api/policies")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    {"content":[{"id":100,"name":"Tinubu Basic Plan","status":"ACTIVE","coverageStartDate":"2024-01-01T00:00:00","coverageEndDate":"2025-01-01T00:00:00","createdAt":"2025-01-01T00:00:00","updatedAt":"2025-01-01T00:00:00"},{"id":101,"name":"Tinubu Premium Plan","status":"ACTIVE","coverageStartDate":"2024-06-01T00:00:00","coverageEndDate":"2025-06-01T00:00:00","createdAt":"2025-06-01T00:00:00","updatedAt":"2025-06-01T00:00:00"},{"id":102,"name":"Tinubu Inactive Plan","status":"INACTIVE","coverageStartDate":"2023-01-01T00:00:00","coverageEndDate":"2024-01-01T00:00:00","createdAt":"2024-01-01T00:00:00","updatedAt":"2024-01-01T00:00:00"}],"pageable":{"pageNumber":0,"pageSize":10,"sort":{"sorted":true,"empty":false,"unsorted":false},"offset":0,"paged":true,"unpaged":false},"last":true,"totalPages":1,"totalElements":3,"first":true,"size":10,"number":0,"sort":{"sorted":true,"empty":false,"unsorted":false},"numberOfElements":3,"empty":false}
                """));
    }

    @Test
    @Order(1)
    @DisplayName("Should return empty content when requesting an out-of-bounds page")
    void shouldReturnEmptyPageWhenOffsetIsTooHigh() throws Exception {
        mockMvc.perform(get("/api/policies")
                        .param("page", "999")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    {"content":[],"pageable":{"pageNumber":999,"pageSize":10,"sort":{"sorted":true,"empty":false,"unsorted":false},"offset":9990,"paged":true,"unpaged":false},"last":true,"totalPages":1,"totalElements":3,"first":false,"size":10,"number":999,"sort":{"sorted":true,"empty":false,"unsorted":false},"numberOfElements":0,"empty":true}
                """));
    }


    @Test
    @Order(2)
    @DisplayName("Should return specific insurance policy by ID")
    void shouldReturnPolicyById() throws Exception {
        final Long existingPolicyId = 100L;

        mockMvc.perform(get("/api/policies/{id}", existingPolicyId))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        {"id":100,"name":"Tinubu Basic Plan","status":"ACTIVE","coverageStartDate":"2024-01-01T00:00:00","coverageEndDate":"2025-01-01T00:00:00","createdAt":"2025-01-01T00:00:00","updatedAt":"2025-01-01T00:00:00"}
                        """));
    }

    @Test
    @Order(3)
    @DisplayName("Should return 404 for non-existent insurance policy ID")
    void shouldReturn404ForNonexistentPolicy() throws Exception {
        final Long nonExistentPolicyId = 999L;

        mockMvc.perform(get("/api/policies/{id}", nonExistentPolicyId))
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {"error":"Resource not found by id: 999","status":"404"}
                        """));
    }

    @Test
    @Order(5)
    @DisplayName("Should create a new insurance policy successfully")
    void shouldCreateNewPolicy() throws Exception {
        String newPolicyJson = """
        {
          "name": "Tinubu New Plan",
          "status": "ACTIVE",
          "coverageStartDate": "2025-01-01T00:00:00",
          "coverageEndDate": "2026-01-01T00:00:00"
        }
        """;

        mockMvc.perform(post("/api/policies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPolicyJson))
                .andExpect(status().isCreated())
                .andExpect(content().json("""
                    {"id":1,"name":"Tinubu New Plan","status":"ACTIVE","coverageStartDate":"2025-01-01T00:00:00","coverageEndDate":"2026-01-01T00:00:00","createdAt":"2024-05-18T05:00:00","updatedAt":"2024-05-18T05:00:00"}
                """));
    }

    @Test
    @Order(5)
    @DisplayName("Should create a new insurance policy successfully")
    void shouldCreateReturn400NewPolicy() throws Exception {
        String newPolicyJson = """
        {
          "name": ""
        }
        """;

        mockMvc.perform(post("/api/policies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPolicyJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                    {"coverageStartDate":"Coverage activation date is required","name":"Policy name must not be blank","coverageEndDate":"Coverage expiration date is required","status":"Status is required"}
                """));
    }


    @Test
    @Order(6)
    @DisplayName("Should update an existing insurance policy successfully")
    void shouldUpdateExistingPolicy() throws Exception {
        Long existingPolicyId = 100L;

        String updatePolicyJson = """
        {
          "name": "Tinubu Updated Plan",
          "status": "INACTIVE",
          "coverageStartDate": "2024-01-01T00:00:00",
          "coverageEndDate": "2025-01-01T00:00:00"
        }
        """;

        mockMvc.perform(put("/api/policies/{id}", existingPolicyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePolicyJson))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                    {"id":100,"name":"Tinubu Updated Plan","status":"INACTIVE","coverageStartDate":"2024-01-01T00:00:00","coverageEndDate":"2025-01-01T00:00:00","createdAt":"2025-01-01T00:00:00","updatedAt":"2024-05-18T05:00:00"}
                """));
    }

    @Test
    @Order(6)
    @DisplayName("Should update an existing insurance policy successfully")
    void shouldNotUpdateUnFoundPolicy() throws Exception {
        Long existingPolicyId = 999L;

        String updatePolicyJson = """
        {
          "name": "Tinubu Updated Plan",
          "status": "INACTIVE",
          "coverageStartDate": "2024-01-01T00:00:00",
          "coverageEndDate": "2025-01-01T00:00:00"
        }
        """;

        mockMvc.perform(put("/api/policies/{id}", existingPolicyId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePolicyJson))
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                    {"error":"Resource not found by id: 999","status":"404"}
                """));
    }

}
