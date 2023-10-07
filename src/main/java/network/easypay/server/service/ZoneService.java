package network.easypay.server.service;

import network.easypay.server.model.Zone;
import network.easypay.server.repository.ZoneRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class ZoneService {
    private ZoneRepository zoneRepository;

    public List<Zone> findAll(){
        return zoneRepository.findAll();
    }
}
