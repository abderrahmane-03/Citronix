package net.yc.citronix.service;

import lombok.RequiredArgsConstructor;
import net.yc.citronix.DTO.SaleDTO;
import net.yc.citronix.exceptions.FieldNotFoundException;
import net.yc.citronix.exceptions.SaleNotFoundException;
import net.yc.citronix.mapper.SaleMapper;
import net.yc.citronix.model.Harvest;
import net.yc.citronix.model.Sale;
import net.yc.citronix.repository.HarvestRepository;
import net.yc.citronix.repository.SaleRepository;
import net.yc.citronix.serviceInterface.SaleServiceINF;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService implements SaleServiceINF {


    private final SaleRepository saleRepository;


    private final SaleMapper saleMapper;


    private final HarvestRepository harvestRepository; // Assume this exists to interact with Harvest data

    public SaleDTO save(SaleDTO saleDTO) {

        Long harvestId = saleDTO.getHarvestId();
        Harvest harvest = harvestRepository.findById(harvestId)
                .orElseThrow(() -> new FieldNotFoundException("Harvest with ID " + harvestId + " not found."));

        double TotalQuantity = harvest.getTotalQuantity();

        saleDTO.setQuantity(TotalQuantity);
        double revenue = TotalQuantity * saleDTO.getUnitPrice();
        saleDTO.setRevenue(revenue);

        Sale sale = saleMapper.toEntity(saleDTO);
        Sale savedSale = saleRepository.save(sale);

        return saleMapper.toDTO(savedSale);
    }



    public List<SaleDTO> show() {
        return saleRepository.findAll()
                .stream()
                .map(saleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SaleDTO update(Long id, SaleDTO updatedSaleDTO) {
        Sale existingSale = saleRepository.findById(id)
                .orElseThrow(() -> new SaleNotFoundException("Sale with ID " + id + " not found."));

        Long harvestId = updatedSaleDTO.getHarvestId();
        Harvest harvest = harvestRepository.findById(harvestId)
                .orElseThrow(() -> new FieldNotFoundException("Harvest with ID " + harvestId + " not found."));

        double totalQuantity = harvest.getTotalQuantity();
        updatedSaleDTO.setQuantity(totalQuantity);

        double revenue = totalQuantity * updatedSaleDTO.getUnitPrice();
        updatedSaleDTO.setRevenue(revenue);

        existingSale.setClientName(updatedSaleDTO.getClientName());
        existingSale.setSaleDate(updatedSaleDTO.getSaleDate());
        existingSale.setUnitPrice(updatedSaleDTO.getUnitPrice());
        existingSale.setHarvestId(updatedSaleDTO.getHarvestId());

        Sale updatedSale = saleRepository.save(existingSale);
        return saleMapper.toDTO(updatedSale);
    }


    public void delete(Long id) {
        Optional<Sale> sale = saleRepository.findById(id);

        if (sale.isPresent()) {
            saleRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Sale with ID " + id + " not found.");
        }
    }

}
