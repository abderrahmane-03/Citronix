package net.yc.citronix.service;

import net.yc.citronix.DTO.FieldDTO;
import org.mapstruct.factory.Mappers;

import net.yc.citronix.DTO.HarvestDTO;
import net.yc.citronix.enums.Season;
import net.yc.citronix.mapper.HarvestMapper;
import net.yc.citronix.model.Field;
import net.yc.citronix.model.Harvest;
import net.yc.citronix.model.Tree;
import net.yc.citronix.repository.FieldRepository;
import net.yc.citronix.repository.HarvestRepository;
import net.yc.citronix.serviceInterface.FieldServiceINF;
import net.yc.citronix.serviceInterface.HarvestServiceINF;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HarvestServiceTest {

    @InjectMocks
    private HarvestService harvestService;

    @Mock
    private FieldServiceINF fieldService;

    @Mock
    private HarvestRepository harvestRepository;

    @Mock
    private HarvestMapper harvestMapper;

    @Mock
    private FieldRepository fieldRepository;

    private HarvestDTO harvestDTO;
    private FieldDTO field;
    private Tree tree1, tree2;

    @BeforeEach
    void setUp() {
        harvestDTO = new HarvestDTO();
        harvestDTO.setField(field);
        harvestDTO.setHarvestDate(LocalDate.of(2024, 11, 21));

        field = new FieldDTO();
        field.setId(1L);

        tree1 = new Tree();
        tree1.setId(1L);
        tree1.setAge(5);

        tree2 = new Tree();
        tree2.setId(2L);
        tree2.setAge(12);
    }

    @Test
    void testGenerateHarvest() {
        // Arrange
        Field mockField = new Field();
        mockField.setId(1L);

        FieldDTO mockFieldDTO = new FieldDTO();
        mockFieldDTO.setId(1L);

        HarvestDTO harvestDTO = new HarvestDTO();
        harvestDTO.setField(mockFieldDTO);
        harvestDTO.setSeason(Season.SUMMER);
        harvestDTO.setTotalQuantity(32.0);

        when(fieldRepository.findById(1L)).thenReturn(Optional.of(mockField));
        when(harvestRepository.existsByFieldAndSeason(mockField, Season.SUMMER)).thenReturn(false);

        when(harvestMapper.toEntity(any(HarvestDTO.class))).thenAnswer(invocation -> {
            HarvestDTO dto = invocation.getArgument(0);
            Harvest harvest = new Harvest();
            harvest.setSeason(dto.getSeason());
            harvest.setTotalQuantity(dto.getTotalQuantity());
            harvest.setField(mockField);
            return harvest;
        });

        when(harvestRepository.save(any(Harvest.class))).thenAnswer(invocation -> {
            Harvest harvest = invocation.getArgument(0);
            harvest.setId(100L); // Simulate generated ID
            return harvest;
        });

        when(harvestMapper.toDTO(any(Harvest.class))).thenAnswer(invocation -> {
            Harvest harvest = invocation.getArgument(0);
            HarvestDTO dto = new HarvestDTO();
            dto.setId(harvest.getId());
            dto.setSeason(harvest.getSeason());
            dto.setTotalQuantity(harvest.getTotalQuantity());
            dto.setField(mockFieldDTO);
            return dto;
        });

        // Act
        HarvestDTO result = harvestService.save(harvestDTO);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(32.0, result.getTotalQuantity());
        assertEquals(1L, result.getField().getId());
        assertEquals(Season.SUMMER, result.getSeason());

        verify(harvestRepository, times(1)).save(any(Harvest.class));
        verify(fieldRepository, times(1)).findById(1L);
    }

    @Test
    void testCalculateTreeProductivity() {
        assertEquals(2.5, harvestService.calculateTreeProductivity(2));
        assertEquals(12.0, harvestService.calculateTreeProductivity(5));
        assertEquals(20.0, harvestService.calculateTreeProductivity(15));
        assertEquals(0.0, harvestService.calculateTreeProductivity(25));
    }

    @Test
    void testDetermineSeason() {
        assertEquals(Season.WINTER, harvestService.determineSeason(1));
        assertEquals(Season.SPRING, harvestService.determineSeason(4));
        assertEquals(Season.SUMMER, harvestService.determineSeason(7));
        assertEquals(Season.AUTUMN, harvestService.determineSeason(10));
    }

    @Test
    void testSaveHarvest() {
        // Arrange
        Field mockField = new Field();
        mockField.setId(1L);

        FieldDTO mockFieldDTO = new FieldDTO();
        mockFieldDTO.setId(1L);

        harvestDTO.setField(mockFieldDTO); // Set the field in the HarvestDTO
        harvestDTO.setSeason(Season.WINTER);

        when(fieldRepository.findById(1L)).thenReturn(Optional.of(mockField));

        // Pass Field object directly instead of Optional<Field>
        when(harvestRepository.existsByFieldAndSeason(mockField, Season.WINTER)).thenReturn(false);

        when(harvestMapper.toEntity(any(HarvestDTO.class))).thenAnswer(invocation -> {
            HarvestDTO dto = invocation.getArgument(0);
            Harvest harvest = new Harvest();
            harvest.setSeason(dto.getSeason());
            harvest.setTotalQuantity(dto.getTotalQuantity());
            return harvest;
        });

        when(harvestRepository.save(any(Harvest.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(harvestMapper.toDTO(any(Harvest.class))).thenAnswer(invocation -> {
            Harvest harvest = invocation.getArgument(0);
            HarvestDTO dto = new HarvestDTO();
            dto.setSeason(harvest.getSeason());
            dto.setTotalQuantity(harvest.getTotalQuantity());
            return dto;
        });

        // Act
        HarvestDTO result = harvestService.save(harvestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(Season.WINTER, result.getSeason());
        verify(harvestRepository, times(1)).save(any(Harvest.class));
    }

    @Test
    void testDeleteHarvest() {
        // Arrange
        Harvest harvest = new Harvest();
        harvest.setId(1L);  // Mocked harvest with id 1
        when(harvestRepository.findById(1L)).thenReturn(Optional.of(harvest));  // Mock repository to return the harvest

        // Act
        harvestService.delete(1L);  // Call delete on the service

        // Assert
        verify(harvestRepository, times(1)).deleteById(1L);  // Ensure deleteById was called exactly once
    }

}
