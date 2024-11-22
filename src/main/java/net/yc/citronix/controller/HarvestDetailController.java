package net.yc.citronix.controller;

import net.yc.citronix.DTO.HarvestDetailDTO;
import net.yc.citronix.model.HarvestDetail;
import net.yc.citronix.service.HarvestDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/harvestDetails")
public class HarvestDetailController {

    @Autowired
    private HarvestDetailService harvestDetailsService;


    @PostMapping("/create")
    public ResponseEntity<String> createHarvestDetail(@RequestBody @Validated HarvestDetailDTO harvestDetails) {
        harvestDetailsService.save(harvestDetails);
        return ResponseEntity.ok("HarvestDetail created successfully!");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateHarvestDetail(@PathVariable Long id, @RequestBody @Validated HarvestDetailDTO harvestDetails) {
        harvestDetailsService.update(id, harvestDetails);
        return ResponseEntity.ok("HarvestDetail updated successfully!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHarvestDetail(@PathVariable Long id) {
        harvestDetailsService.delete(id);
        return ResponseEntity.ok("HarvestDetail deleted successfully!");
    }

    @GetMapping("/AllHarvestDetails")
    public List<HarvestDetailDTO> showHarvestDetails() {
        return harvestDetailsService.show();
    }
}
