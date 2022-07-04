import java.util.ArrayList;
import java.util.List;

public class CustomNumber {
    private int lengthBeforeComma;
    private int lengthAfterComma;
    private boolean sign;
    private List<Byte> digitArray = new ArrayList<Byte>();

    public CustomNumber() {
        lengthBeforeComma = 0;
        lengthAfterComma = 0;
        sign = true;
    }

    public CustomNumber(String InputString) {
        setValue(InputString);
    }

    public void appendDigit(byte digit, String position) {
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

    public void clean() {
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

    private void eraseDigitArray() {
        digitArray = new ArrayList<>();
    }

    public void evenOut(CustomNumber evenOutNumber) {
        while (evenOutNumber.lengthBeforeComma > lengthBeforeComma) {
            digitArray.add(0, (byte) 0);
            lengthBeforeComma = lengthBeforeComma + 1;

        }
        while (evenOutNumber.lengthAfterComma > lengthAfterComma) {
            digitArray.add(getDataLength(), (byte) 0);
            lengthAfterComma = lengthAfterComma + 1;
        }
    }
    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder();

        for (int i = 0; i < getDataLength(); i++) {
            byte k = digitArray.get(i);
            String j = String.valueOf(k);
            returnString.append(j);
        }

        if (lengthBeforeComma < 0 || lengthAfterComma < 0) {

            if (lengthAfterComma < 0) {
                returnString.append("*".repeat(Math.max(0, (lengthAfterComma * -1))));
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
            String c = returnString.substring(lengthBeforeComma, getDataLength());
            returnString = new StringBuilder(b + "." + c);
        }

        if (isPos() || isZero()) {
            returnString.insert(0, "+");
        } else {
            returnString.insert(0, "-");
        }
        return returnString.toString();
    }

    public int getDataLength() {
        return lengthBeforeComma + lengthAfterComma;
    }

    public List<Byte> getDigitArray() {
        return digitArray;
    }

    public int getLAC() {
        return lengthAfterComma;
    }

    public int getLBC() {
        return lengthBeforeComma;
    }

    public void invertSign() {
        if (!isZero()) {
            sign = !sign;
        } else {
            sign = true;
        }
    }

    public void set(CustomNumber number) {
        sign = number.isPos();
        lengthBeforeComma = number.lengthBeforeComma;
        lengthAfterComma = number.lengthAfterComma;
        digitArray = new ArrayList<>(number.digitArray);
    }

    public boolean isNeg() {
        return !sign;
    }

    public boolean isPos() {
        return sign;
    }

    public boolean isZero() {
        return ((lengthBeforeComma + lengthAfterComma) == 0 || digitArray.size() == 0);
    }

    public void printDigitArray() {
        StringBuilder retString = new StringBuilder();
        for (int i = 0; i < getDataLength(); i++) {
            byte k = digitArray.get(i);
            String j = String.valueOf(k);
            retString.append(j);
        }
        System.out.println("DigitArrayString: " + retString);
    }

    public void removeDataDigit(String pos) {
        if (!isZero()) {
            String charAt = pos.substring(0, 1);
            if (charAt.equals("L") || charAt.equals("l") || charAt.equals("B") || charAt.equals("b")) {
                digitArray.remove(0);
                lengthBeforeComma -= 1;

            }
            if (charAt.equals("R") || charAt.equals("r") || charAt.equals("A") || charAt.equals("a")) {

                digitArray.remove(getDataLength() - 1);
                lengthAfterComma -= 1;
            }
        }
    }

    public void setLAC(int length) {
        lengthAfterComma = length;
    }

    public void setLBC(int length) {
        lengthBeforeComma = length;
    }

    public void setNeg() {
        sign = false;
    }

    public void setPos() {
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
            clean();
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
        }
    }

    public void shiftLeft(int iterations) {
        if (!isZero()) {
            lengthBeforeComma += iterations;
            lengthAfterComma -= iterations;
        }
    }

    public void shiftRight() {
        if (!isZero()) {
            lengthBeforeComma -= 1;
            lengthAfterComma += 1;
        }
    }

    public void shiftRight(int iterations) {
        if (!isZero()) {
            lengthBeforeComma -= iterations;
            lengthAfterComma += iterations;
        }
    }

    public void incLBC(int iterations) {
        if (!isZero()) {
            lengthBeforeComma += iterations;
        }
    }

    public void incLBC() {
        if (!isZero()) {
            lengthBeforeComma += 1;
        }
    }

    public void incLAC(int iterations) {
        if (!isZero()) {
            lengthAfterComma += iterations;
        }
    }

    public void incLAC() {
        if (!isZero()) {
            lengthAfterComma += 1;
        }
    }

    public void decLBC(int iterations) {
        if (!isZero()) {
            lengthBeforeComma -= iterations;
        }
    }

    public void decLBC() {
        if (!isZero()) {
            lengthBeforeComma -= 1;
        }
    }

    public void decLAC(int iterations) {
        if (!isZero()) {
            lengthAfterComma -= iterations;
        }
    }

    public void decLAC() {
        if (!isZero()) {
            lengthAfterComma -= 1;
        }
    }

    public void setPhantomZeros() {
        if (!isZero()) {
            while (lengthAfterComma < 0) {
                digitArray.add(digitArray.size(), (byte) 0);
                lengthAfterComma += 1;
            }
        }
    }
}