package com.example.client;

import com.example.dto.StudentDto;
import com.example.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class StudentServiceClient {
    private WebClient webClient;

    public Boolean checkStudentValidity(Long studentId) {
        return webClient.get()
                .uri("/api/students/{studentId}", studentId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                {
                    return Mono.error(new ResourceNotFoundException("Student not found with ID: " + studentId));
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    return Mono.error(new RuntimeException("Student Service is unavailable. Please try again later."));
                })
                .bodyToMono(StudentDto.class)
                .map(Objects::nonNull)
                .block();

    }

    public List<StudentDto> getAllStudents(){
        return webClient.get()
                .uri("/api/students")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                {
                    return Mono.error(new ResourceNotFoundException("Something went wrong, try again ! "));
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
                    return Mono.error(new RuntimeException("Student Service is unavailable. Please try again later."));
                })
                .bodyToMono(new ParameterizedTypeReference<List<StudentDto>>(){})
                .block();
    }
}
