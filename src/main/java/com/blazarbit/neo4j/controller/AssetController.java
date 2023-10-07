package com.blazarbit.neo4j.controller;

import com.blazarbit.neo4j.model.Asset;
import com.blazarbit.neo4j.service.AssetService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/assets")
@AllArgsConstructor
public class AssetController {
    private AssetService assetService;

    @GetMapping()
    private List<Asset> findAllAssets(){
        return assetService.findAllAssets();
    }
}
