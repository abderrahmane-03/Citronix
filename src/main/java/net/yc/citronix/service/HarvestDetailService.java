package net.yc.citronix.service;

import lombok.RequiredArgsConstructor;
import net.yc.citronix.DTO.HarvestDetailDTO;
import net.yc.citronix.mapper.HarvestDetailMapper;
import net.yc.citronix.model.HarvestDetail;
import net.yc.citronix.repository.HarvestDetailRepository;
import net.yc.citronix.serviceInterface.HarvestDetailServiceINF;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HarvestDetailService implements HarvestDetailServiceINF {


    private final HarvestDetailRepository harvestDetailRepository;


    private final HarvestDetailMapper harvestDetailMapper;

    public HarvestDetailDTO save(HarvestDetailDTO harvestDetailDTO) {
        HarvestDetail harvestDetail = harvestDetailMapper.toEntity(harvestDetailDTO);
        HarvestDetail savedHarvestDetail = harvestDetailRepository.save(harvestDetail);
        return harvestDetailMapper.toDTO(savedHarvestDetail);
    }

    public List<HarvestDetailDTO> show() {
        return harvestDetailRepository.findAll()
                .stream()
                .map(harvestDetailMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        Optional<HarvestDetail> harvestDetail = harvestDetailRepository.findById(id);

        if (harvestDetail.isPresent()) {
            harvestDetailRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("HarvestDetail with ID " + id + " not found.");
        }
    }
}
