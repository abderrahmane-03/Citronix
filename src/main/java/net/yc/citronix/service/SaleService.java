package net.yc.citronix.service;

import net.yc.citronix.DTO.SaleDTO;
import net.yc.citronix.mapper.SaleMapper;
import net.yc.citronix.model.Harvest;
import net.yc.citronix.model.Sale;
import net.yc.citronix.repository.HarvestRepository;
import net.yc.citronix.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SaleService {


    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleMapper saleMapper;

    // Save Sale using DTO
    @Autowired
    private HarvestRepository harvestRepository; // Assume this exists to interact with Harvest data

    public SaleDTO save(SaleDTO saleDTO) {
        // Validate Harvest ID
        Long harvestId = saleDTO.getHarvestId();
        if (harvestId == null) {
            throw new IllegalArgumentException("Harvest ID is required.");
        }

        // Fetch the total quantity for the given harvest
        Optional<Harvest> optionalHarvest = harvestRepository.findById(harvestId);
        if (optionalHarvest.isEmpty()) {
            throw new IllegalArgumentException("Harvest not found for ID: " + harvestId);
        }

        Harvest harvest = optionalHarvest.get();
        double quantity = harvest.getTotalQuantity();


        // Calculate revenue
        double revenue = quantity * saleDTO.getUnitPrice();
        saleDTO.setQuantity(quantity);
        saleDTO.setRevenue(revenue);

        // Map DTO to entity and save
        Sale sale = saleMapper.toEntity(saleDTO);
        Sale savedSale = saleRepository.save(sale);

        // Update the remaining quantity in the harvest
        harvestRepository.save(harvest);

        // Return the saved entity as a DTO
        return saleMapper.toDTO(savedSale);
    }


    // Get all Sales and return as DTOs
    public List<SaleDTO> show() {
        return saleRepository.findAll()
                .stream()
                .map(saleMapper::toDTO) // Convert each Sale entity to DTO
                .collect(Collectors.toList());
    }

    // Update Sale using DTO
    public SaleDTO update(Long id, SaleDTO updatedSaleDTO) {
        Optional<Sale> existingSaleOpt = saleRepository.findById(id);

        if (existingSaleOpt.isPresent()) {
            Sale existingSale = existingSaleOpt.get();
            existingSale.setClientName(updatedSaleDTO.getClientName());
            existingSale.setSaleDate(updatedSaleDTO.getSaleDate());
            existingSale.setUnitPrice(updatedSaleDTO.getUnitPrice());
            existingSale.setQuantity(updatedSaleDTO.getQuantity());
            existingSale.setHarvestId(updatedSaleDTO.getHarvestId());

            Sale updatedSale = saleRepository.save(existingSale);
            return saleMapper.toDTO(updatedSale);
        } else {
            throw new IllegalArgumentException("Sale with ID " + id + " not found.");
        }
    }

    // Delete Sale by ID
    public void delete(Long id) {
        Optional<Sale> sale = saleRepository.findById(id);

        if (sale.isPresent()) {
            saleRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Sale with ID " + id + " not found.");
        }
    }

}
