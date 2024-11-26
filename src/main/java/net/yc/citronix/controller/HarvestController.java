package net.yc.citronix.controller;

import lombok.RequiredArgsConstructor;
import net.yc.citronix.DTO.HarvestDTO;
import net.yc.citronix.model.Harvest;
import net.yc.citronix.service.HarvestService;
import net.yc.citronix.serviceInterface.HarvestServiceINF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/harvest")
public class HarvestController {


    private final HarvestServiceINF harvestService;


    @PostMapping("/create")
    public ResponseEntity<String> createHarvest(@RequestBody @Validated HarvestDTO harvest) {
        harvestService.generateHarvest(harvest);
        return ResponseEntity.ok("Harvest created successfully!");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateHarvest(@PathVariable Long id, @RequestBody @Validated HarvestDTO harvest) {
        harvestService.update(id, harvest);
        return ResponseEntity.ok("Harvest updated successfully!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHarvest(@PathVariable Long id) {
        harvestService.delete(id);
        return ResponseEntity.ok("Harvest deleted successfully!");
    }

    @GetMapping("/AllHarvests")
    public List<HarvestDTO> showHarvests() {
        return harvestService.show();
    }
}
