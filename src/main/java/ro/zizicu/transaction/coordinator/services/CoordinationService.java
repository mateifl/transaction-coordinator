package ro.zizicu.transaction.coordinator.services;


import org.springframework.transaction.TransactionStatus;
import ro.zizicu.nwbase.transaction.TransactionMessage;
import ro.zizicu.nwbase.transaction.TransactionStatusMessage;
import ro.zizicu.transaction.coordinator.data.entities.MicroserviceTransaction;

public interface CoordinationService {

    MicroserviceTransaction createTransactionStep(TransactionMessage transactionMessage);

    TransactionStatusMessage getTransactionStatus(Long transactionId);

    void updateTransactionStatus();

}
