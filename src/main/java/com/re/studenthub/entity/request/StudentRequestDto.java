package com.re.studenthub.entity.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
public class StudentRequestDto {
    private String name;
    private String email;
    private Double gpa;
}
