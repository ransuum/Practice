package com.example.practice.entity;

import lombok.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DateRange {
    @NotNull(message = "From date is required")
    private LocalDate fromDate;
    @NotNull(message = "To date is required")
    private LocalDate toDate;
}
