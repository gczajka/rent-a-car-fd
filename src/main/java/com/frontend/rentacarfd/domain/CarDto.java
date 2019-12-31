package com.frontend.rentacarfd.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    private Long id;
    private String brand;
    private String model;
    private String colour;
    private String engineType;
    private Integer engineCapacity;
    private Integer productionYear;
    private Double costPerDay;
    private boolean available;
}
