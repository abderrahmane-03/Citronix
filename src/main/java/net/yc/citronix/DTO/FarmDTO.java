package net.yc.citronix.DTO;
import lombok.Data;

@Data
public class FarmDTO {
    private String id;
    private String name;
    private String location;
    private double size;
    private String creationDate;

}
