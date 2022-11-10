package ro.zizicu.transaction.coordinator.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.zizicu.nwbase.transaction.TransactionMessage;
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

        MicroserviceTransaction microserviceTransaction = coordinationService.createTransactionStep(message);

        return ResponseEntity.ok().body(microserviceTransaction);
    }

    @GetMapping
    public void getTransactionStatus() {

    }



}
