package net.yc.citronix.DTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class FarmDTO {
    private Long id;
    private String name;
    private String location;
    private double size;
    private LocalDate creationDate;

}
