package com.blazarbit.neo4j.controller;

import com.blazarbit.neo4j.model.dto.PathfinderDTO;
import com.blazarbit.neo4j.service.ShortestPathService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/pathfinder")
@AllArgsConstructor
public class PathfinderController {
    private ShortestPathService shortestPathService;

    @GetMapping(value = "/{sourceDenom}/{destinationDenom}/{amount}/{address}", produces = "application/json;charset=UTF-8")
    private PathfinderDTO findTokenPath(@PathVariable String sourceDenom, @PathVariable String destinationDenom,
                                        @PathVariable Double amount, @PathVariable String address){
        if (sourceDenom.startsWith("ibcslash"))
            sourceDenom = "ibc/" + sourceDenom.substring("ibcslash".length());
        if (destinationDenom.startsWith("ibcslash"))
            destinationDenom = "ibc/" + destinationDenom.substring("ibcslash".length());

        return shortestPathService.getPathByDenoms(sourceDenom, destinationDenom, address);
    }

    @GetMapping(value = "try")
    private PathfinderDTO findTokenPath2(
            @RequestParam String sourceDenom,
            @RequestParam String destinationDenom,
            @RequestParam Double amount,
            @RequestParam String address
    ){
        // via query param because of hack. we need to configure spring to support %2F symbol later
        PathfinderDTO path = shortestPathService.getPathByDenoms(sourceDenom, destinationDenom, address);
        path.setFeeAmount(0L);
        path.setDestinationTokenAmount(amount);
        return path;
    }
}
