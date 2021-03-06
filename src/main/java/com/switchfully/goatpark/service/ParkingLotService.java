package com.switchfully.goatpark.service;

import com.switchfully.goatpark.repository.parkinglot.ParkingLotRepository;
import com.switchfully.goatpark.domain.parkinglot.ParkingLot;
import com.switchfully.goatpark.service.dto.parkinglot.CreateParkingLotDto;
import com.switchfully.goatpark.service.dto.parkinglot.ParkingLotDto;
import com.switchfully.goatpark.service.dto.parkinglot.ParkingLotOverviewDto;
import com.switchfully.goatpark.service.mapper.ParkingLotMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingLotService {
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingLotMapper parkingLotMapper;

    public ParkingLotService(ParkingLotRepository parkingLotRepository, ParkingLotMapper parkingLotMapper) {
        this.parkingLotRepository = parkingLotRepository;
        this.parkingLotMapper = parkingLotMapper;
    }

    public ParkingLotDto createParkingLot(CreateParkingLotDto parkingLotToCreate) {
        AssertFieldService.assertParkingLotIsValid(parkingLotToCreate);
        ParkingLot parkingLotToSave = parkingLotMapper.mapCreateParkingLotDtoToParkingLot(parkingLotToCreate);
        parkingLotRepository.save(parkingLotToSave);
        return parkingLotMapper.mapParkingLotToParkingLotDto(parkingLotToSave);
    }

    public List<ParkingLotOverviewDto> getAllParkingLots() {
        return parkingLotRepository.getAll().stream()
                .map(parkingLotMapper::mapParkingLotToParkingLotOverviewDto).toList();
    }
}