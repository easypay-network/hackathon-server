package network.easypay.server.controller;

import network.easypay.server.model.Zone;
import network.easypay.server.service.ZoneService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/zones")
@AllArgsConstructor
public class ZoneController {
    private ZoneService zoneService;

    @GetMapping()
    private List<Zone> findAll(){
        return zoneService.findAll();
    }
}
