package net.yc.citronix.controller;

import net.yc.citronix.DTO.FarmDTO;
import net.yc.citronix.service.FarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/farm")
public class FarmController {

    @Autowired
    private FarmService farmService;


    @PostMapping("/create")
    public ResponseEntity<String> createFarm(@RequestBody @Validated FarmDTO farm) {
        farmService.save(farm);
        return ResponseEntity.ok("Farm created successfully!");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateFarm(@PathVariable Long id, @RequestBody @Validated FarmDTO farm) {
        farmService.update(id, farm);
        return ResponseEntity.ok("Farm updated successfully!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFarm(@PathVariable Long id) {
        farmService.delete(id);
        return ResponseEntity.ok("Farm deleted successfully!");
    }

    @GetMapping("/AllFarms")
    public List<FarmDTO> showFarms() {
        return farmService.show();
    }
    @GetMapping("/search")
    public List<FarmDTO> searchFarms(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Double minSize,
            @RequestParam(required = false) Double maxSize,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        return farmService.searchFarms(name, location, minSize, maxSize, startDate, endDate);
    }
}
