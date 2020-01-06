package com.frontend.rentacarfd.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentalDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long duration;
    private Double cost;
    private String carModel;
    private String userId;
}
