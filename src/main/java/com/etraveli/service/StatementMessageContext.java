package com.etraveli.service;

import com.etraveli.domain.Customer;

import java.util.List;

public class StatementMessageContext {
    private final Customer customer;
    private final double totalAmount;
    private final int frequentEnterPoints;
    private final List<TitleAmount> titleAmounts;

    public StatementMessageContext(Customer customer, double totalAmount, int frequentEnterPoints, List<TitleAmount> titleAmounts) {
        this.customer = customer;
        this.totalAmount = totalAmount;
        this.frequentEnterPoints = frequentEnterPoints;
        this.titleAmounts = titleAmounts;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public int getFrequentEnterPoints() {
        return frequentEnterPoints;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<TitleAmount> getTitleAmounts() {
        return titleAmounts;
    }

    static class TitleAmount {
        private final String title;
        private final double amount;

        public TitleAmount(String title, double amount) {
            this.title = title;
            this.amount = amount;
        }

        public String getTitle() {
            return title;
        }

        public double getAmount() {
            return amount;
        }
    }
}

