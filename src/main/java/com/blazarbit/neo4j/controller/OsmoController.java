package com.blazarbit.neo4j.controller;

import com.blazarbit.neo4j.model.Token;
import com.blazarbit.neo4j.service.OsmoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/osmo")
@AllArgsConstructor
public class OsmoController {
    private OsmoService osmoService;

    @GetMapping()
    private List<Token> findAll(){
        return osmoService.findAll();
    }

    @GetMapping("/denom")
    private Token findTokenByDenom(@RequestParam String denomName){
        return osmoService.findTokenByDenom(denomName);
    }
}
