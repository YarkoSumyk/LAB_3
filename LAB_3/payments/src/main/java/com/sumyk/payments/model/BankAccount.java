package com.sumyk.payments.model;

public class BankAccount {
    private Integer accountId;
    private  Integer  clientId;
    private double accountBalance;
    private boolean blocking = false;

    public BankAccount(Integer clientId, double accountBalance) {
        this.clientId = clientId;
        this.accountBalance = accountBalance;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public void setBLocking(boolean locking) {
        this.blocking = locking;
    }

    public boolean isBlocking() {
        return blocking;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }


}
