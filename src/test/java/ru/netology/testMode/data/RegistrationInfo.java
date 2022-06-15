package ru.netology.testMode.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Data
@Value
@AllArgsConstructor
public class RegistrationInfo {
    private String login;
    private String password;
    private String status;
}