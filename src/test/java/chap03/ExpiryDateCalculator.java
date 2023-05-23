package chap03;

import java.time.LocalDate;
import java.time.YearMonth;

import static java.time.YearMonth.from;

public class ExpiryDateCalculator {

    public LocalDate calculateExpiryDate(PayData payData) {

        int addedMonths = payData.getPayAmount() / 10_000;

        if (payData.getFirstBillingDate() != null) {

            LocalDate candidateExp = payData.getBillingDate().plusMonths(addedMonths);

            if (payData.getFirstBillingDate().getDayOfMonth() != candidateExp.getDayOfMonth()) {
                if (from(candidateExp).lengthOfMonth() < payData.getFirstBillingDate().getDayOfMonth()) {
                    return candidateExp.withDayOfMonth(from(candidateExp).lengthOfMonth());
                }
            }
        }
        return payData.getBillingDate().plusMonths(addedMonths);
    }
}
