package net.yc.citronix.DTO;
import lombok.Data;
import java.time.LocalDate;

@Data
public class TreeDTO {


    private String id;
    private LocalDate plantationDate;
    private int age;
    private String fieldId;
    private boolean productive;
}
