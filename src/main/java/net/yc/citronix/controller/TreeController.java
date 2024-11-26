package net.yc.citronix.controller;

import lombok.RequiredArgsConstructor;
import net.yc.citronix.DTO.TreeDTO;
import net.yc.citronix.model.Tree;
import net.yc.citronix.service.TreeService;
import net.yc.citronix.serviceInterface.TreeServiceINF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tree")
public class TreeController {


    private final TreeServiceINF treeService;


    @PostMapping("/create")
    public ResponseEntity<String> createTree(@RequestBody @Validated TreeDTO tree) {
        treeService.save(tree);
        return ResponseEntity.ok("Tree created successfully!");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTree(@PathVariable Long id, @RequestBody @Validated TreeDTO tree) {
        treeService.update(id, tree);
        return ResponseEntity.ok("Tree updated successfully!");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTree(@PathVariable Long id) {
        treeService.delete(id);
        return ResponseEntity.ok("Tree deleted successfully!");
    }

    @GetMapping("/AllTrees")
    public List<TreeDTO> showTrees() {
        return treeService.show();
    }
}
