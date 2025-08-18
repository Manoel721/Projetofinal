package tech.ada.dto;
import jakarta.validation.constraints.NotBlank;

public record AddAccessoryRequest(
        @NotBlank
        String name) { }
