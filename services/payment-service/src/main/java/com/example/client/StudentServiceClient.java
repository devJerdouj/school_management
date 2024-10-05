package com.example.client;

import com.example.dto.StudentDto;
import com.example.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class StudentServiceClient {
    private WebClient webClient;


    public Boolean checkStudentValidity(Long studentId) {
        return webClient.get()
                .uri("api/students/{studentId}", studentId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                {
                    return Mono.error(new ResourceNotFoundException("Student not found with ID: " + studentId));
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    return Mono.error(new RuntimeException("Student Service is unavailable. Please try again later."));
                })
                .bodyToMono(StudentDto.class)
                .map(studentDto -> studentDto.id() != null)
                .block();

    }
}
