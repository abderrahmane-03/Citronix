package net.yc.citronix.controller;

import lombok.RequiredArgsConstructor;
import net.yc.citronix.DTO.FieldDTO;
import net.yc.citronix.model.Field;
import net.yc.citronix.service.FieldService;
import net.yc.citronix.serviceInterface.FieldServiceINF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/field")
public class FieldController {


    private final FieldServiceINF fieldService;


    @PostMapping("/create")
    public ResponseEntity<String> createField(@RequestBody @Validated FieldDTO field) {
        fieldService.save(field);
        return ResponseEntity.ok("Field created successfully!");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateField(@PathVariable Long id, @RequestBody @Validated FieldDTO field) {
        fieldService.update(id, field);
        return ResponseEntity.ok("Field updated successfully!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteField(@PathVariable Long id) {
        fieldService.delete(id);
        return ResponseEntity.ok("Field deleted successfully!");
    }

    @GetMapping("/AllFields")
    public List<FieldDTO> showFields() {
        return fieldService.show();
    }
}
