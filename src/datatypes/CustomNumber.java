package datatypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public CustomNumber(CustomNumber number) {
        this.lengthBeforeComma = number.lengthBeforeComma;
        this.lengthAfterComma = number.lengthAfterComma;
        this.sign = number.sign;
        this.digitArray = new ArrayList<>(number.digitArray);
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

    public void removeNDataDigitsLeft(int n) {
        if (!isZero() && n > 0 && n < this.digitArray.size()) {
            digitArray.subList(0, n).clear();
            lengthBeforeComma -= n;
        } else if (this.digitArray.size() == n) {
            this.lengthAfterComma = 0;
            this.lengthBeforeComma = 0;
            this.sign = true;
            this.digitArray = new ArrayList<>();
        }
    }

    public void cropRelevant(int relevant) {
        this.removeNDataDigitsRight(this.digitArray.size() - relevant);
    }

    public void removeNDataDigitsRight(int n) {
        if (!isZero() && n > 0 && n < this.digitArray.size()) {
            digitArray.subList(digitArray.size() - n, digitArray.size()).clear();
            lengthAfterComma -= n;
        } else if (this.digitArray.size() == n) {
            this.lengthAfterComma = 0;
            this.lengthBeforeComma = 0;
            this.sign = true;
            this.digitArray = new ArrayList<>();
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
            digitArray = new ArrayList<>();
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
        digitArray = new ArrayList<>();
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

    public boolean areDigitArraysEqual(CustomNumber number1) {
        if (!(number1.getDataLength() == this.getDataLength())) {
            return false;
        }

        return Objects.equals(number1.getDigitArray(), this.getDigitArray());
    }

    public void print() {
        System.out.println("Zahl ist Null:          " + this.isZero());
        System.out.println("Zahl ist Positiv:       " + this.isPos());
        if (this.getDigitArray().size() != 0) {
            System.out.println("Array.get(0) in var:    " + this.getDigitArray().get(0));
        } else {
            System.out.println("Array.get(0) in var:    IST NULL");
        }
        System.out.println("Stellen vor dem Komma:  " + this.getLBC());
        System.out.println("Stellen nach dem Komma: " + this.getLAC());
        System.out.println("Position des Kommas:    nach der " + this.getLBC() + ". Ziffer");
        System.out.println("Anzahl der Ziffern:     " + this.getDataLength());
        System.out.println("Zahl:                   " + this);
        System.out.println("-------------------------------------------------------");
    }
}