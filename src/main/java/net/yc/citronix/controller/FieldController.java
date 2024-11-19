package net.yc.citronix.controller;

import net.yc.citronix.DTO.FieldDTO;
import net.yc.citronix.model.Field;
import net.yc.citronix.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/field")
public class FieldController {

    @Autowired
    private FieldService fieldService;


    @PostMapping("/create")
    public ResponseEntity<String> createField(@RequestBody @Validated FieldDTO field) {
        fieldService.save(field);
        return ResponseEntity.ok("Field created successfully!");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateField(@PathVariable String id, @RequestBody @Validated FieldDTO field) {
        fieldService.update(id, field);
        return ResponseEntity.ok("Field updated successfully!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteField(@PathVariable String id) {
        fieldService.delete(id);
        return ResponseEntity.ok("Field deleted successfully!");
    }

    @GetMapping("/AllFields")
    public List<FieldDTO> showFields() {
        return fieldService.show();
    }
}
