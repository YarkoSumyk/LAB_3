package com.sumyk.payments.service;

import com.sumyk.payments.model.BankAccount;
import com.sumyk.payments.model.Payment;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@org.springframework.stereotype.Service
public class BankAccountService {
    private static final Map<Integer, BankAccount> ACCOUNT_REPOSITORY_MAP = new HashMap<>();

    private static final AtomicInteger ACCOUNT_ID_HOLDER = new AtomicInteger();
    public void create(BankAccount account, Integer  clientId) {
        final int accountId = ACCOUNT_ID_HOLDER.incrementAndGet();
        account.setAccountId(accountId);
        account.setClientId(clientId);

        ACCOUNT_REPOSITORY_MAP.put(accountId, account);
    }
    public List<BankAccount> readByClientId(int clientId) {
        List<BankAccount> accounts = new ArrayList<>(ACCOUNT_REPOSITORY_MAP.values());
        List<BankAccount> accountsById = new ArrayList<BankAccount>();
        accounts.forEach(account -> {
            if(account.getClientId()==clientId){
                accountsById.add(account);
            }
        });
        return accountsById;
    }
    public BankAccount read(int accountId) {
        return ACCOUNT_REPOSITORY_MAP.get(accountId);
    }


    public boolean update(BankAccount account, int accountId) {
        if (ACCOUNT_REPOSITORY_MAP.containsKey(accountId)) {
            account.setAccountId(accountId);
            ACCOUNT_REPOSITORY_MAP.put(accountId, account);
            return true;
        }

        return false;
    }

    public boolean delete(int accountId) {
        return ACCOUNT_REPOSITORY_MAP.remove(accountId) != null;
    }

    public boolean block(int accountId) {
        if (ACCOUNT_REPOSITORY_MAP.containsKey(accountId)) {
            BankAccount account = ACCOUNT_REPOSITORY_MAP.get(accountId);
            account.setBLocking(true);
            ACCOUNT_REPOSITORY_MAP.put(accountId, account);
            return true;
        }
        return false;
    }

    public boolean unblock(int accountId) {
        if (ACCOUNT_REPOSITORY_MAP.containsKey(accountId)) {
            BankAccount account = ACCOUNT_REPOSITORY_MAP.get(accountId);
            account.setBLocking(false);
            ACCOUNT_REPOSITORY_MAP.put(accountId, account);
            return true;
        }
        return false;
    }

    public boolean debiting(Payment payment) {
        if (ACCOUNT_REPOSITORY_MAP.containsKey(payment.getSenderAccountId()) ) {
            int senderAccountId = payment.getSenderAccountId();
            BankAccount senderAccount = ACCOUNT_REPOSITORY_MAP.get(senderAccountId);
            if(!senderAccount.isBlocking()){
                double newAccountBalance = senderAccount.getAccountBalance() - payment.getPaymentAmount();
                senderAccount.setAccountBalance(newAccountBalance);
                ACCOUNT_REPOSITORY_MAP.put(senderAccountId, senderAccount);
                return true;
            }
        }
        return false;
    }
    public boolean refill(Payment payment) {
        if (ACCOUNT_REPOSITORY_MAP.containsKey(payment.getRecipientAccountId())) {
            int recipientId = payment.getRecipientAccountId();
            BankAccount recipientAccount = ACCOUNT_REPOSITORY_MAP.get(recipientId);
            double ammount = payment.getPaymentAmount() + recipientAccount.getAccountBalance();
            recipientAccount.setAccountBalance(ammount);
            ACCOUNT_REPOSITORY_MAP.put(recipientId, recipientAccount);
            return true;
        }
        return false;
    }

}
