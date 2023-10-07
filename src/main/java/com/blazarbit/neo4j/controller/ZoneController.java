package com.blazarbit.neo4j.controller;

import com.blazarbit.neo4j.model.Zone;
import com.blazarbit.neo4j.service.ZoneService;
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
