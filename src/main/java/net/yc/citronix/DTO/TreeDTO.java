package net.yc.citronix.DTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import net.yc.citronix.model.Field;

import java.time.LocalDate;

@Data
public class TreeDTO {


    private Long id ;
    @JsonFormat(pattern = "yyyy-M-d")
    @PastOrPresent(message = "Creation date cannot be in the future.")
    @NotNull(message = "Plantation date is required.")
    @Column(name = "plantation_date", nullable = false)
    private LocalDate plantationDate;

    @Column(nullable = false)
    private int age;

    @NotNull(message = "Field ID is required.")
    private Field field;

    private boolean productive;
}