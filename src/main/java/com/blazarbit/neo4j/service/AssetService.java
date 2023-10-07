package com.blazarbit.neo4j.service;

import com.blazarbit.neo4j.model.Asset;
import com.blazarbit.neo4j.model.Zone;
import com.blazarbit.neo4j.repository.AssetRepository;
import com.blazarbit.neo4j.repository.ZoneRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
@AllArgsConstructor
public class AssetService {
    private AssetRepository assetRepository;
    private ZoneRepository zoneRepository;

    public List<Asset> findAllAssets() {
        log.info("Search all assets...");
        List<Map<String, Object>> response = assetRepository.findAll2();

        List<Asset> assets = new ArrayList<>();
        for (Map<String, Object> map : response) {
            Asset asset = new Asset();
            asset.setTicker((String) map.get("ticker"));
            asset.setLogoUrl((String) map.get("logoUrl"));
            asset.setDenom((String) map.get("denom"));
            asset.setDenomTrace((String) map.get("denomTrace"));
            asset.setOriginalTicker((String) map.get("originalTicker"));
            asset.setLocalTicker((String) map.get("localTicker"));

            Zone zone = zoneRepository.findZoneByLocatedNodeDenom(asset.getDenom());
            asset.setLocatedZone(zone);

            assets.add(asset);
        }

        return assets;
    }
}
