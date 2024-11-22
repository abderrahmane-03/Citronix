package net.yc.citronix.serviceInterface;

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


public interface SaleServiceINF {

     SaleDTO save(SaleDTO saleDTO);

     List<SaleDTO> show();

     SaleDTO update(Long id, SaleDTO updatedSaleDTO);

     void delete(Long id);

}
