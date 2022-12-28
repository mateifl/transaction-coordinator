package ro.zizicu.transaction.coordinator.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ro.zizicu.nwbase.transaction.TransactionMessage;
import ro.zizicu.nwbase.transaction.TransactionStatusMessage;
import ro.zizicu.transaction.coordinator.data.entities.MicroserviceTransaction;
import ro.zizicu.transaction.coordinator.services.CoordinationService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(value = "/transactions")
public class TransactionController {

    private final CoordinationService coordinationService;

    @PostMapping
    public ResponseEntity<?> createTransactionStep(@RequestBody TransactionMessage message) {
    	log.debug("transaction message {}", message.toString());
        MicroserviceTransaction microserviceTransaction = coordinationService.createTransactionStep(message);
        log.debug("transaction step created {}", message.getTransactionId());
        TransactionStatusMessage statusMessage = TransactionStatusMessage.builder()
        		.status( microserviceTransaction.getTransaction().getStatus() )
        		.transactionId(microserviceTransaction.getTransaction().getTransactionId())
        		.build();
        
        return ResponseEntity.ok().body(statusMessage);
    }

    @GetMapping(value = "/{transactionId}")
    public ResponseEntity<?> getTransactionStatus(@PathVariable Long transactionId ) {
        return ResponseEntity.ok().body(coordinationService.getTransactionStatus(transactionId));
    }

}
