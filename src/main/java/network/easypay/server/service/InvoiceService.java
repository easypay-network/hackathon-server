package network.easypay.server.service;

import network.easypay.server.model.*;
import network.easypay.server.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Log4j2
@AllArgsConstructor
public class InvoiceService {
    private InvoiceRepository invoiceRepository;
    private AssetRepository assetRepository;
    private ZoneRepository zoneRepository;
    private AddressRepository addressRepository;
    private EmailRepository emailRepository;

    public List<Invoice> findAll(){
        List<Invoice> invoices = invoiceRepository.findAll();

        for (Invoice invoice : invoices) {
            List<Map<String, Object>> assetResponse = assetRepository.findAssetByInvoiceId(invoice.getIdentity());
            Asset asset = new Asset();

            if (!assetResponse.isEmpty()) {
                asset.setTicker((String) assetResponse.get(0).get("ticker"));
                asset.setLogoUrl((String) assetResponse.get(0).get("logoUrl"));
                asset.setDenom((String) assetResponse.get(0).get("denom"));
                asset.setDenomTrace((String) assetResponse.get(0).get("denomTrace"));
                asset.setOriginalTicker((String) assetResponse.get(0).get("originalTicker"));
                asset.setLocalTicker((String) assetResponse.get(0).get("localTicker"));
            }

            Zone zone = zoneRepository.findZoneByLocatedNodeDenom(asset.getDenom());
            asset.setLocatedZone(zone);

            invoice.setRequestedAsset(asset);
        }

        return invoices;
    }

    public Optional<Invoice> findInvoiceById(Long id) {
        Optional<Invoice> invoice = invoiceRepository.findByIdentity(id);

        if (invoice.isPresent()) {
            List<Map<String, Object>> assetResponse = assetRepository.findAssetByInvoiceId(invoice.get().getIdentity());
            Asset asset = new Asset();

            if (!assetResponse.isEmpty()) {
                asset.setTicker((String) assetResponse.get(0).get("ticker"));
                asset.setLogoUrl((String) assetResponse.get(0).get("logoUrl"));
                asset.setDenom((String) assetResponse.get(0).get("denom"));
                asset.setDenomTrace((String) assetResponse.get(0).get("denomTrace"));
                asset.setOriginalTicker((String) assetResponse.get(0).get("originalTicker"));
                asset.setLocalTicker((String) assetResponse.get(0).get("localTicker"));
            }

            Zone zone = zoneRepository.findZoneByLocatedNodeDenom(asset.getDenom());
            asset.setLocatedZone(zone);
            invoice.get().setRequestedAsset(asset);
        }

        return invoice;
    }

    public Invoice saveInvoice(Invoice invoice) {
        Email requester = invoice.getRequester();
        Address receiver = invoice.getReceiver();
        Asset requestedAsset = invoice.getRequestedAsset();
        Address payerWallet = invoice.getPayerWallet();
        Email payerEmail = invoice.getPayerEmail();

        Optional<Address> addressResult = addressRepository.findByAddress(receiver.getAddress());
        if (addressResult.isPresent()) {
            Address zoneLinkedReceiver = addressResult.get();
//            zoneLinkedReceiver.setRelatedZone(null);
            invoice.setReceiver(zoneLinkedReceiver);
        }

        Optional<Email> emailResult = emailRepository.findByAddress(requester.getAddress());
        if (emailResult.isPresent()) {
            Email linkedRequesterEmail = emailResult.get();
            invoice.setRequester(linkedRequesterEmail);
        }

        Optional<Address> payerAddressResult = addressRepository.findByAddress(payerWallet.getAddress());
        if (payerAddressResult.isPresent()) {
            Address linkedPayerWalletAddress = payerAddressResult.get();
//            linkedPayerWalletAddress.setRelatedZone(null);
            invoice.setPayerWallet(linkedPayerWalletAddress);
        }

        Optional<Email> payerEmailResult = emailRepository.findByAddress(payerEmail.getAddress());
        if (payerEmailResult.isPresent())
            invoice.setPayerEmail(payerEmailResult.get());

        List<Map<String, Object>> assetResult = assetRepository.findOneByDenom(requestedAsset.getDenom());
        if (assetResult.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "wrong requestedAsset");

        String denom = (String) assetResult.get(0).get("denom");
        Long requestedAssetId = assetRepository.findOneIdentityByDenom(denom);
        invoice.getRequestedAsset().setLocatedZone(null);
        Invoice invoiceResult = invoiceRepository.save(invoice);

        invoiceRepository.createRelationship(invoiceResult.getIdentity(),requestedAssetId);

        return invoiceResult;
    }
}
