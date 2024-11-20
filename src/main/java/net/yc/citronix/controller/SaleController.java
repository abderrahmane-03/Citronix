package net.yc.citronix.controller;

import net.yc.citronix.DTO.SaleDTO;
import net.yc.citronix.model.Sale;
import net.yc.citronix.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private SaleService saleService;


    @PostMapping("/create")
    public ResponseEntity<String> createSale(@RequestBody @Validated SaleDTO sale) {
        saleService.save(sale);
        return ResponseEntity.ok("Sale created successfully!");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSale(@PathVariable UUID id, @RequestBody @Validated SaleDTO sale) {
        saleService.update(id, sale);
        return ResponseEntity.ok("Sale updated successfully!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSale(@PathVariable UUID id) {
        saleService.delete(id);
        return ResponseEntity.ok("Sale deleted successfully!");
    }

    @GetMapping("/AllSales")
    public List<SaleDTO> showSales() {
        return saleService.show();
    }
}
