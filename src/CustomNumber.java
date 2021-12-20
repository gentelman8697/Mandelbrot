import java.util.ArrayList;
import java.util.List;

public class CustomNumber {
    /* private */ int lengthBeforeComma;
    /* private */ int lengthAfterComma;
    /* private */ boolean sign;
    List<Byte> digitArray = new ArrayList<Byte>();

    public CustomNumber() {
        lengthBeforeComma = 0;
        lengthAfterComma = 0;
        sign = true;
    }

    public CustomNumber(String inputString) {
        setValue(inputString);
    }

    public void appendDataDigit(byte digit, String position) {
        String charAt = position.substring(0, 1);
        if (charAt.equals("L") || charAt.equals("l") || charAt.equals("B") || charAt.equals("b")) {
            digitArray.add(0, digit);
            lengthBeforeComma = lengthBeforeComma + 1;

        }
        if (charAt.equals("R") || charAt.equals("r") || charAt.equals("A") || charAt.equals("a")) {
            digitArray.add(digitArray.size(), digit);
            lengthAfterComma = lengthAfterComma + 1;
        }
    }

    public void cleanCustomNumber() {
        if (!isZero()) {
            int reduceBefore = 0;
            int reduceAfter = 0;
            for (int i = 0; i < digitArray.size(); ) {
                if (digitArray.get(0) == (byte) 0) {
                    digitArray.remove(0);
                    reduceBefore += 1;
                } else {
                    break;
                }
            }
            lengthBeforeComma = lengthBeforeComma - reduceBefore;
            for (int i = digitArray.size() - 1; i > 0; i--) {
                if (digitArray.get(i) == (byte) 0) {
                    digitArray.remove(i);
                    reduceAfter = reduceAfter + 1;
                } else {
                    break;
                }
            }
            lengthAfterComma = lengthAfterComma - reduceAfter;
        } else {
            setZero();
        }
    }

    public void eraseDigitArray() {
        for (int i = 0; i < digitArray.size(); ) {
            digitArray.remove(0);
        }
    }

    public void evenOutNumbers(CustomNumber evenOutNumber) {
        while (evenOutNumber.getlengthBeforeComman() > lengthBeforeComma) {
            digitArray.add(0, (byte) 0);
            lengthBeforeComma = lengthBeforeComma + 1;

        }
        while (evenOutNumber.getlengthAfterComman() > lengthAfterComma) {
            digitArray.add(getDataLengthTotal(), (byte) 0);
            lengthAfterComma = lengthAfterComma + 1;
        }
    }

    public String generateString() {
        StringBuilder returnString = new StringBuilder();

        for (int i = 0; i < getDataLengthTotal(); i++) {
            byte k = digitArray.get(i);
            String j = String.valueOf(k);
            returnString.append(j);
        }

        if (lengthBeforeComma < 0 || lengthAfterComma < 0) {

            if (lengthAfterComma < 0) {
                for (int i = 0; i < (lengthAfterComma * -1); i++) {
                    returnString.append("*");
                }
                returnString.append(".");
            }

            if (lengthBeforeComma < 0) {
                for (int i = 0; i < (lengthBeforeComma * -1); i++) {
                    returnString.insert(0, "*");
                }
                returnString.insert(0, ".");
            }
        } else {
            String b = returnString.substring(0, lengthBeforeComma);
            String c = returnString.substring(lengthBeforeComma, getDataLengthTotal());
            returnString = new StringBuilder(b + "." + c);
        }

        if (isPositive() || isZero()) {
            returnString.insert(0, "+");
        } else {
            returnString.insert(0, "-");
        }
        return returnString.toString();
    }

    public int getDataLengthTotal() {
        return lengthBeforeComma + lengthAfterComma;
    }

    public List<Byte> getDigitArray() {
        return digitArray;
    }

    public int getlengthAfterComman() {
        return lengthAfterComma;
    }

    public int getlengthBeforeComman() {
        return lengthBeforeComma;
    }

    public void invertSign() {
        if (!isZero()) {
            sign = !sign;
        } else {
            sign = true;
        }
    }

    public void is(CustomNumber Number) {
        setValue(Number.generateString());
    }

    public boolean isNegative() {
        return !sign;
    }

    public boolean isPositive() {
        return sign;
    }

    public boolean isZero() {
        return ((lengthBeforeComma + lengthAfterComma) == 0 || digitArray.size() == 0 );
    }

    public void printDiggitArray() {
        String diggitArrayString = "";
        for (int i = 0; i < getDataLengthTotal(); i++) {
            byte k = digitArray.get(i);
            String j = String.valueOf(k);
            diggitArrayString = diggitArrayString + j;
        }
        System.out.println("DiggitArrayString: " + diggitArrayString);
    }


    public void removeDataDigit(String position) {
        if (!isZero()) {
            String charAt = position.substring(0, 1);
            if (charAt.equals("L") || charAt.equals("l") || charAt.equals("B") || charAt.equals("b")) {
                digitArray.remove(0);
                lengthBeforeComma -= 1;

            }
            if (charAt.equals("R") || charAt.equals("r") || charAt.equals("A") || charAt.equals("a")) {

                digitArray.remove(getDataLengthTotal() - 1);
                lengthAfterComma -= 1;
            }
        }
    }

    public void setLengthAfterComma(int length) {
        lengthAfterComma = length;
    }

    public void setLengthBeforeComma(int length) {
        lengthBeforeComma = length;
    }

    public void setNegative() {
        sign = false;
    }

    public void setPositive() {
        sign = true;
    }

    public void setSign(boolean singVar) {
        sign = singVar;
    }

    public void setValue(String inputString) {
        int lengthTotal;
        if ((inputString.charAt(0) == '+' || inputString.charAt(0) == '-') && inputString.length() > 1) {
            if (inputString.charAt(0) == '+') {
                sign = true;
            }
            if (inputString.charAt(0) == '-') {
                sign = false;
            }
            inputString = inputString.substring(1);
        } else {
            sign = true;
        }

        inputString = inputString.replaceFirst("^0+(?!$)", "");
        if (inputString.length() > 0) {
            while (inputString.endsWith("0")) {
                inputString = inputString.substring(0, inputString.length() - 1);
            }
        }

        if (inputString.equals(".")) {
            setZero();
        } else {
            eraseDigitArray();
            lengthBeforeComma = inputString.indexOf(".");

            lengthTotal = inputString.length() - 1;
            lengthAfterComma = lengthTotal - lengthBeforeComma;

            for (int i = 0; i < lengthBeforeComma; i++) {
                char k = inputString.charAt(i);
                String j = String.valueOf(k);
                if (j.equals("*")) {
                    digitArray.add((byte) 0);
                } else {
                    digitArray.add((byte) Integer.parseInt(j));
                }

            }

            for (int b = lengthBeforeComma + 1; b < lengthTotal + 1; b++) {
                char k = inputString.charAt(b);
                String j = String.valueOf(k);
                if (j.equals("*")) {
                    digitArray.add((byte) 0);
                } else {
                    digitArray.add((byte) Integer.parseInt(j));
                }
            }
            cleanCustomNumber();
        }
    }

    public void setZero() {
        sign = true;
        lengthAfterComma = 0;
        lengthBeforeComma = 0;
        eraseDigitArray();
    }

    public void shiftLeft() {
        if (!isZero()) {
            lengthBeforeComma += 1;
            lengthAfterComma -= 1;
        } else {
            setZero();
        }
    }

    public void shiftLeft(int iterations) {
        if (!isZero()) {
            lengthBeforeComma += iterations;
            lengthAfterComma -= iterations;
        } else {
            setZero();
        }
    }

    public void shiftRight() {
        if (!isZero()) {
            lengthBeforeComma -= 1;
            lengthAfterComma += 1;
        } else {
            setZero();
        }
    }

    public void shiftRight(int iterations) {
        if (!isZero()) {
            lengthBeforeComma -= iterations;
            lengthAfterComma += iterations;
        } else {
            setZero();
        }
    }
}