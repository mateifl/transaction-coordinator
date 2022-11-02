package ro.zizicu.transaction.coordinator.services;


import ro.zizicu.nwbase.transaction.TransactionMessage;

public interface CoordinationService {

    void processMessage(TransactionMessage message);



}
