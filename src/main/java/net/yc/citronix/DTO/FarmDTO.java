package net.yc.citronix.DTO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FarmDTO {
    private String id;
    private String name;
    private String location;
    private double size;
    private LocalDate creationDate;

}
