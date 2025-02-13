//package com.neslihansezen.ipcs.api.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.neslihansezen.ipcs.api.dto.TransactionRequest;
//import com.neslihansezen.ipcs.api.dto.TransactionResponse;
//import com.neslihansezen.ipcs.api.service.TransactionService;
//import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@AutoConfigureMockMvc
//@RequiredArgsConstructor
//@WebMvcTest(CalculationApiController.class)
//public class CalculationApiControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockitoBean
//    private TransactionService transactionService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    public void shouldReturnOk_ValidRequest() throws Exception {
//        TransactionRequest request = TransactionRequest.builder()
//                .vehicle("PKW")
//                .mileage("12345")
//                .postcode("56075")
//                .build();
//
//        TransactionResponse response = TransactionResponse.builder()
//                .id(1L)
//                .insurancePremium(9.75)
//                .build();
//
//        Mockito.when(transactionService.saveUserInput(any(TransactionRequest.class), eq("TestSource")))
//                .thenReturn(response);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/public")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("X-Source", "TestSource")
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.isSuccess").value(true))
//                .andExpect(jsonPath("$.message").value("Calculation successful"))
//                .andExpect(jsonPath("$.data.id").value(1L))
//                .andExpect(jsonPath("$.data.insurancePremium").value(9.75));
//    }
//
//    @Test
//    public void shouldReturnBadRequest_InvalidRequest() throws Exception {
//        TransactionRequest request = TransactionRequest.builder()
//                .vehicle("")
//                .mileage("1234567")
//                .postcode("abcde")
//                .build();
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/public")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .header("X-Source", "TestSource")
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.isSuccess").value(false));
//    }
//
//    @Test
//    public void shouldReturnBadRequest_HeaderMissing() throws Exception {
//        TransactionRequest request = TransactionRequest.builder()
//                .vehicle("LKW")
//                .mileage("12345")
//                .postcode("56075")
//                .build();
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/public")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest());
//    }
//}