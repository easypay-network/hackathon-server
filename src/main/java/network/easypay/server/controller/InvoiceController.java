package network.easypay.server.controller;

import network.easypay.server.model.Invoice;
import network.easypay.server.service.InvoiceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/invoices")
@AllArgsConstructor
public class InvoiceController {
    private InvoiceService invoiceService;

    @GetMapping()
    private List<Invoice> findAll(
            @RequestParam(name = "requesterEmail", required = false) String requesterEmail,
            @RequestParam(name = "requesterWalletAddress", required = false) String requesterWalletAddress){
        List<Invoice> invoices = invoiceService.findAll();
        for (Invoice invoice : invoices) {
            if (invoice.getReceiver() != null && invoice.getReceiver().getAddress() != null && invoice.getReceiver().getAddress().equalsIgnoreCase(requesterWalletAddress))
                invoice.setDirection(Invoice.Direction.OUTGOING);
            else if (invoice.getRequester() != null && invoice.getRequester().getAddress() != null &&
                    (invoice.getRequester().getAddress().equalsIgnoreCase(requesterEmail) ||
                    invoice.getRequester().getAddress().equalsIgnoreCase(requesterWalletAddress)))
                invoice.setDirection(Invoice.Direction.NEUTRAL);
            else invoice.setDirection(Invoice.Direction.INCOMING);
        }
        return invoices;
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> findInvoiceById(@PathVariable Long id){
        Optional<Invoice> invoice = invoiceService.findInvoiceById(id);
        return ResponseEntity.ok(invoice);
    }

    @PostMapping()
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        Invoice savedInvoice = invoiceService.saveInvoice(invoice);

        if (savedInvoice != null) {
            return new ResponseEntity<>(savedInvoice, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
