package chap02;

import org.junit.jupiter.api.Test;

import static chap02.PasswordStrength.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordStrengthMeterTest {

    private PasswordStrengthMeter meter = new PasswordStrengthMeter();

    private void assertStrength(String s, PasswordStrength expStr) {
        PasswordStrength result = meter.meter(s);
        assertEquals(expStr, result);
    }

    /**
     * 검사 규칙 세 가지
     * 1. 길이가 8글자 이상
     * 2. 0부터 9 사이의 숫자를 포함
     * 3. 대문자 포함
     */

    // 모두 만족
    @Test
    void meetsAllCriteria_Then_Strong() {
        assertStrength("ab12!@AB", STRONG);
        assertStrength("abc1!Add", STRONG);
    }

    // 하나만 만족
    @Test
    void meetsOtherCriteria_except_for_Length_Then_Normal() {
        assertStrength("ab12!@A", NORMAL);
        assertStrength("Ab12!c", NORMAL);
    }

    // 두 개를 만족
    @Test
    void meetsOtherCriteria_except_for_number_Then_Normal() {
        assertStrength("ab!@ABqwer", NORMAL);
    }

    @Test
    void nullInput_Then_Invalid() {
        assertStrength("", INVALID);
    }

    @Test
    void meetsOtherCriteria_except_for_Uppercase_Then_Normal() {
        assertStrength("ab12!@df", NORMAL);
    }

    @Test
    void meetsOnlyLengthCriteria_Then_Weak() {
        assertStrength("abdefghi", WEAK);
    }

    @Test
    void meetsOnlyNumCriteria_Then_Weak() {
        assertStrength("12345", WEAK);
    }

    @Test
    void meetsOnlyUpperCriteria_Then_Weak() {
        assertStrength("ABZEF", WEAK);
    }

    @Test
    void meetsNoCriteria_Then_Weak() {
        assertStrength("abc", WEAK);
    }
}
