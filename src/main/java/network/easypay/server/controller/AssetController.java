package network.easypay.server.controller;

import network.easypay.server.model.Asset;
import network.easypay.server.service.AssetService;
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
