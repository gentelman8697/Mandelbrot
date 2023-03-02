package datatypes;

import org.junit.jupiter.api.function.Executable;

//import java.lang.reflect.Executable;
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
        set(InputString);
    }

    public CustomNumber(CustomNumber number) {
        lengthBeforeComma = number.lengthBeforeComma;
        lengthAfterComma = number.lengthAfterComma;
        sign = number.sign;
        digitArray = new ArrayList<>(number.digitArray);
    }

    public void appendDigitLeft(byte digit) {
        digitArray.add(0, digit);
        lengthBeforeComma = lengthBeforeComma + 1;
    }

    public void appendDigitRight(byte digit) {
        digitArray.add(digitArray.size(), digit);
        lengthAfterComma = lengthAfterComma + 1;
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

            if (lengthBeforeComma == lengthAfterComma * -1) {
                lengthBeforeComma = 0;
                lengthAfterComma = 0;
            }
        }
    }

    public void cropRelevant(int relevant) {
        removeNDigitsRight(digitArray.size() - relevant);
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

    public int getDataLength() {
        return lengthBeforeComma + lengthAfterComma;
    }

    public Executable invertSign() {
        if (!isZero()) {
            sign = !sign;
        } else {
            throw new RuntimeException("invertSign but number is Zero!");
        }
        return null;
    }

    public boolean isNeg() {
        return !sign;
    }

    public boolean isPos() {
        return sign;
    }

    public boolean isZero() {
        /* This if is never triggered(cant prove, found out by testing), and so the second if with reval is not needed.
        I assume handling LBC and LAC regarding the handling of zeroes is consistent:
        That means: If a number is Zero, both ways of finding out if a number is zero are both true.

        if(digitArray.size() == 0 && (lengthBeforeComma != 0 || lengthAfterComma != 0))
            throw new RuntimeException("oh no :(");

        boolean retVal = (lengthBeforeComma == lengthAfterComma * -1 || digitArray.size() == 0);
        if (retVal) {
            lengthAfterComma = 0;
            lengthBeforeComma = 0;
        }*/


        //This is also not needed because the code seems to be consistent!
        /*
        if((lengthBeforeComma == lengthAfterComma && lengthBeforeComma == 0) && digitArray.size() == 0)
            return true;
        if(lengthBeforeComma != lengthAfterComma*-1  && digitArray.size() != 0)
            return false;
        throw new RuntimeException("oh no :(");
        */
        return (lengthBeforeComma == lengthAfterComma * -1 || digitArray.size() == 0);
    }

    public void removeDigitLeft() {
        if (!isZero()) {
            digitArray.remove(0);
            lengthBeforeComma -= 1;
        } else {
            throw new RuntimeException("removeDigitLeft but number is Zero!");
        }
    }

    public void removeDigitRight() {
        if (!isZero()) {
            digitArray.remove(getDataLength() - 1);
            lengthAfterComma -= 1;
        } else {
            throw new RuntimeException("removeDigitRight but number is Zero!");
        }
    }

    public void removeNDigitsLeft(int n) {
        if (!isZero() && n >= 0 && n < digitArray.size()) {
            digitArray.subList(0, n).clear();
            lengthBeforeComma -= n;
        } else if (digitArray.size() == n) {
            setZero();
        }
        // TODO: Change logic to make removeNDigits consistent with removeDigit, in regards to exceptions
    }

    public void removeNDigitsRight(int n) {
        if (!isZero() && n >= 0 && n < digitArray.size()) {
            digitArray.subList(digitArray.size() - n, digitArray.size()).clear();
            lengthAfterComma -= n;
        } else if (digitArray.size() == n) {
            setZero();
        }
        // TODO: Change logic to make removeNDigits consistent with removeDigit, in regards to exceptions
    }

    public void set(CustomNumber number) {
        sign = number.sign;
        lengthBeforeComma = number.lengthBeforeComma;
        lengthAfterComma = number.lengthAfterComma;
        digitArray = new ArrayList<>(number.digitArray);
    }

    public void setPhantomZeros() {
        if (!isZero()) {
            while (lengthAfterComma < 0) {
                digitArray.add(digitArray.size(), (byte) 0);
                lengthAfterComma += 1;
            }
        } else {
            throw new RuntimeException("setPhantomZeros but number is Zero!");
        }
    }

    public void set(String inputString) {
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
        } else {
            throw new RuntimeException("shiftLeft but number is Zero!");
        }
    }

    public void shiftLeft(int iterations) {
        if (!isZero()) {
            lengthBeforeComma += iterations;
            lengthAfterComma -= iterations;
        } else {
            throw new RuntimeException("shiftLeft but number is Zero!");
        }
    }

    public void shiftRight() {
        if (!isZero()) {
            lengthBeforeComma -= 1;
            lengthAfterComma += 1;
        } else {
            throw new RuntimeException("shiftRight but number is Zero!");
        }
    }

    public void shiftRight(int iterations) {
        if (!isZero()) {
            lengthBeforeComma -= iterations;
            lengthAfterComma += iterations;
        } else {
            throw new RuntimeException("shiftRight but number is Zero!");
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

    public void print() {
        System.out.println("Zahl ist Null:          " + isZero());
        System.out.println("Zahl ist Positiv:       " + isPos());
        if (getDigitArray().size() != 0) {
            System.out.println("Array.get(0) in var:    " + getDigitArray().get(0));
        } else {
            System.out.println("Array.get(0) in var:    IST NULL");
        }
        System.out.println("Stellen vor dem Komma:  " + getLBC());
        System.out.println("Stellen nach dem Komma: " + getLAC());
        System.out.println("Position des Kommas:    nach der " + getLBC() + ". Ziffer");
        System.out.println("Anzahl der Ziffern:     " + getDataLength());
        System.out.println("Zahl:                   " + this);
        System.out.println("-------------------------------------------------------");
    }

    public void incLBC(int iterations) {
        if (!isZero()) {
            lengthBeforeComma += iterations;
        } else {
            throw new RuntimeException("incLBC but number is Zero!");
        }
    }

    public void incLBC() {
        if (!isZero()) {
            lengthBeforeComma += 1;
        } else {
            throw new RuntimeException("incLBC but number is Zero!");
        }
    }

    public void incLAC(int iterations) {
        if (!isZero()) {
            lengthAfterComma += iterations;
        } else {
            throw new RuntimeException("incLAC but number is Zero!");
        }
    }

    public void incLAC() {
        if (!isZero()) {
            lengthAfterComma += 1;
        } else {
            throw new RuntimeException("incLAC but number is Zero!");
        }
    }

    public void decLBC(int iterations) {
        if (!isZero()) {
            lengthBeforeComma -= iterations;
        } else {
            throw new RuntimeException("decLBC but number is Zero!");
        }
    }

    public void decLBC() {
        if (!isZero()) {
            lengthBeforeComma -= 1;
        } else {
            throw new RuntimeException("decLBC but number is Zero!");
        }
    }

    public void decLAC(int iterations) {
        if (!isZero()) {
            lengthAfterComma -= iterations;
        } else {
            throw new RuntimeException("decLAC but number is Zero!");
        }
    }

    public void decLAC() {
        if (!isZero()) {
            lengthAfterComma -= 1;
        } else {
            throw new RuntimeException("decLAC but number is Zero!");
        }
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
}