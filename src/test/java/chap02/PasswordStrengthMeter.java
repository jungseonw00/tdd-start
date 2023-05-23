package chap02;

import static chap02.PasswordStrength.*;
import static java.lang.Character.isUpperCase;

public class PasswordStrengthMeter {

    public PasswordStrength meter(String s) {

        if (s == null || s.isEmpty()) {
            return INVALID;
        }

        int metCounts = getMetCriteriaCounts(s);

        if (metCounts <= 1) return WEAK;
        if (metCounts == 2) return NORMAL;

        return STRONG;
    }

    private int getMetCriteriaCounts(String s) {
        int metCounts = 0;

        if (s.length() >= 8) metCounts++;
        if (meetsContainingNumberCriteria(s)) metCounts++;
        if (meetsContainingUppercaseCriteria(s)) metCounts++;

        return metCounts;
    }

    private boolean meetsContainingUppercaseCriteria(String s) {
        for (char ch : s.toCharArray()) {
            if (isUpperCase(ch)) {
                return true;
            }
        }
        return false;
    }

    private boolean meetsContainingNumberCriteria(String s) {
        for (char ch : s.toCharArray()) {
            if (ch >= '0' && ch <= '9') {
                return true;
            }
        }
        return false;
    }
}
