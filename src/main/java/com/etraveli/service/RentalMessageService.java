package com.etraveli.service;

import java.util.List;

public class RentalMessageService {

    public String createStatementMessage(StatementMessageContext context) {
        var result = new StringBuilder();
        result.append("Rental Record for ")
                .append(context.getCustomer().getName())
                .append("\n");

        List<StatementMessageContext.TitleAmount> titleAmounts = context.getTitleAmounts();
        for (StatementMessageContext.TitleAmount titleAmount : titleAmounts) {
            String title = titleAmount.getTitle();
            double amount = titleAmount.getAmount();
            result.append("\t")
                    .append(title)
                    .append("\t")
                    .append(amount)
                    .append("\n");
        }

        result.append("Amount owed is ")
                .append(context.getTotalAmount())
                .append("\n")
                .append("You earned ")
                .append(context.getFrequentEnterPoints())
                .append(" frequent points\n");

        return result.toString();
    }
}
