package com.blazarbit.neo4j.controller;

import com.blazarbit.neo4j.model.dto.PathfinderDTO;
import com.blazarbit.neo4j.service.ShortestPathService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/pathfinder")
@AllArgsConstructor
public class PathfinderController {
    private ShortestPathService shortestPathService;

    @GetMapping(value = "/{sourceDenom}/{destinationDenom}/{amount}/{address}", produces = "application/json;charset=UTF-8")
    private PathfinderDTO findTokenPath(@PathVariable String sourceDenom, @PathVariable String destinationDenom,
                                        @PathVariable Double amount, @PathVariable String address){
        return shortestPathService.getPathByDenoms(sourceDenom, destinationDenom, address);
    }
}
