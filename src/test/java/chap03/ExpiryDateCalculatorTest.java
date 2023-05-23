package chap03;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static chap03.PayData.builder;
import static java.time.LocalDate.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpiryDateCalculatorTest {

    /**
     * 1. 서비스 이용을 위해서 매달 1만원을 선불로 납부
     * 2. 2개월 이상 요금을 납부할 수 있다.
     * 3. 10만원을 납부하면 1년 서비스를 제공한다.
     */
    @Test
    void 만원_납부하면_한달_뒤가_만료일이_됨() {
        assertExpiryDate(
                 builder()
                .billingDate(of(2019, 3, 1))
                .payAmount(10_000)
                .build(),
        of(2019, 4, 1));
    }

    @Test
    void 납부일과_한달_뒤_일자가_같지_않음() {
        assertExpiryDate(
                builder()
                        .billingDate(of(2019, 1, 31))
                        .payAmount(10_000)
                        .build(),
                of(2019, 2, 28));
    }

    @Test
    void 첫_납부일과_만료일_일자가_다를때_만원_납부() {
        PayData payData = builder()
                .firstBillingDate(of(2019, 1, 31))
                .billingDate(of(2019,2, 28))
                .payAmount(10_000)
                .build();
        assertExpiryDate(payData, of(2019, 3, 31));

        PayData payData3 = builder()
                .firstBillingDate(of(2019, 5, 31))
                .billingDate(of(2019,6, 30))
                .payAmount(10_000)
                .build();
        assertExpiryDate(payData3, of(2019, 7, 31));
    }

    @Test
    void 이만원_이상_납부하면_비례해서_만료일_계산() {
        assertExpiryDate(
                builder()
                        .billingDate(of(2019, 3, 1))
                        .payAmount(20_000)
                        .build(),
                of(2019, 5, 1));

        assertExpiryDate(
                builder()
                        .billingDate(of(2019, 3, 1))
                        .payAmount(30_000)
                        .build(),
                of(2019, 6, 1));
    }

    @Test
    void 첫_납부일과_만료일_일자가_다를때_이만원_이상_납부() {
        assertExpiryDate(
                builder()
                        .firstBillingDate(of(2019, 1, 31))
                        .billingDate(of(2019, 2, 28))
                        .payAmount(40_000)
                        .build(),
                of(2019, 6, 30));

        assertExpiryDate(
                builder()
                        .firstBillingDate(of(2019, 3, 31))
                        .billingDate(of(2019, 4, 30))
                        .payAmount(30_000)
                        .build(),
                of(2019, 7, 31));
    }

    private void assertExpiryDate(PayData payData, LocalDate expectedExpiryDate) {
        ExpiryDateCalculator cal = new ExpiryDateCalculator();
        LocalDate realExpiryDate = cal.calculateExpiryDate(payData);
        assertEquals(expectedExpiryDate, realExpiryDate);
    }
}
