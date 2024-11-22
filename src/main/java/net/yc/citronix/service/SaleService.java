package net.yc.citronix.service;

import net.yc.citronix.DTO.SaleDTO;
import net.yc.citronix.mapper.SaleMapper;
import net.yc.citronix.model.Sale;
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
    public SaleDTO save(SaleDTO saleDTO) {
        Sale sale = saleMapper.toEntity(saleDTO);
        Sale savedSale = saleRepository.save(sale);
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
