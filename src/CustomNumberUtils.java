import javax.sound.midi.Soundbank;
import java.util.Objects;

public class CustomNumberUtils {
    public static void print(CustomNumber NumberToPrint) {
        //NumberToPrint.cleanCustomNumber();

        System.out.println("Zahl ist Null:          " + NumberToPrint.isZero());
        System.out.println("Zahl ist Positiv:       " + NumberToPrint.isPositive());
        if (NumberToPrint.getDigitArray().size() != 0) {
            System.out.println("Array.get(0) in var:    " + NumberToPrint.getDigitArray().get(0));
        } else {
            System.out.println("Array.get(0) in var:    IST NULL");
        }
        System.out.println("Stellen vor dem Komma:  " + NumberToPrint.getlengthBeforeComma());
        System.out.println("Stellen nach dem Komma: " + NumberToPrint.getlengthAfterComma());
        System.out.println("Position des Kommas:    nach der " + NumberToPrint.getlengthBeforeComma() + ". Ziffer");
        System.out.println("Anzahl der Ziffern:     " + NumberToPrint.getDataLengthTotal());
        System.out.println("Zahl:                   " + NumberToPrint.generateString());
        System.out.println("-------------------------------------------------------");
    }

    public static CustomNumber addUp(CustomNumber Summand1, CustomNumber Summand2) {
        CustomNumber returnCustomNumber = new CustomNumber();
        returnCustomNumber = addUpSubstractionLogic(Summand1, Summand2, true);
        return returnCustomNumber;
    }

    public static CustomNumber subtract(CustomNumber Minuend, CustomNumber Subtrahend) {
        CustomNumber returnCustomNumber = new CustomNumber();
        returnCustomNumber = addUpSubstractionLogic(Minuend, Subtrahend, false);
        return returnCustomNumber;
    }

    public static CustomNumber multiply(CustomNumber Factor1, CustomNumber Factor2) {
        CustomNumber returnCustomNumber = new CustomNumber();

        if (Factor1.isZero() || Factor2.isZero()) {
            returnCustomNumber.setZero();
        } else if (Factor1.getDataLengthTotal() == 1) {
            switch (Factor1.getDigitArray().get(0)) {
                case (byte)1 -> {
                    returnCustomNumber.is(Factor2);
                    if (Factor1.getlengthBeforeComma() > 1) {
                        returnCustomNumber.shiftLeft(Factor1.getlengthBeforeComma() - 1);
                    } else if (Factor1.getlengthBeforeComma() < 1) {
                        returnCustomNumber.shiftRight((Factor1.getlengthBeforeComma() * -1) + 1);
                    }
                }
                case (byte)2 -> {
                    returnCustomNumber = addUp(Factor2, Factor2);
                    if (Factor1.getlengthBeforeComma() > 1) {
                        returnCustomNumber.shiftLeft(Factor1.getlengthBeforeComma() - 1);
                    } else if (Factor1.getlengthBeforeComma() < 1) {
                        returnCustomNumber.shiftRight((Factor1.getlengthBeforeComma() * -1) + 1);
                    }
                }
                default -> returnCustomNumber = internalMultiplication(Factor2, Factor1);
            }
        } else if (Factor2.getDataLengthTotal() == 1) {
            switch (Factor2.getDigitArray().get(0)) {
                case (byte)1 -> {
                    returnCustomNumber.is(Factor1);
                    if (Factor2.getlengthBeforeComma() > 1) {
                        returnCustomNumber.shiftLeft(Factor2.getlengthBeforeComma() - 1);
                    } else if (Factor2.getlengthBeforeComma() < 1) {
                        returnCustomNumber.shiftRight((Factor2.getlengthBeforeComma() * -1) + 1);
                    }
                }
                case (byte)2 -> {
                    returnCustomNumber = addUp(Factor1, Factor1);
                    if (Factor2.getlengthBeforeComma() > 1) {
                        returnCustomNumber.shiftLeft(Factor2.getlengthBeforeComma() - 1);
                    } else if (Factor2.getlengthBeforeComma() < 1) {
                        returnCustomNumber.shiftRight((Factor2.getlengthBeforeComma() * -1) + 1);
                    }
                }
                default -> returnCustomNumber = internalMultiplication(Factor1, Factor2);
            }
        } else {
            if (Factor2.getDataLengthTotal() > Factor1.getDataLengthTotal()) {
                returnCustomNumber = internalMultiplication(Factor2, Factor1);
            } else {
                returnCustomNumber = internalMultiplication(Factor1, Factor2);
            }
        }

        if (Factor1.isPositive() != Factor2.isPositive()) {
            returnCustomNumber.setNegative();
        }
        else
        {
            returnCustomNumber.setPositive();
        }

        //returnCustomNumber.cleanCustomNumber();

        return returnCustomNumber;
    }


    public static boolean areEqual(CustomNumber Number1, CustomNumber Number2) {
        if (!((Number1.getlengthBeforeComma() == Number2.getlengthBeforeComma()) && (Number1.getlengthAfterComma() == Number2.getlengthAfterComma()) && (Number1.getDataLengthTotal() == Number2.getDataLengthTotal()))) {
            return false;
        }

        if (Number1.isPositive() == Number2.isNegative())
            return false;

        for (int i = 0; i < Number1.getDataLengthTotal(); i++)
            if (!Objects.equals(Number1.getDigitArray().get(i), Number2.getDigitArray().get(i))) {
                return false;
            }

        return true;
    }

    public static boolean areDigitArraysEqual(CustomNumber Number1, CustomNumber Number2) {
        if (!(Number1.getDataLengthTotal() == Number2.getDataLengthTotal())) {
            return false;
        }

        for (int i = 0; i < Number1.getDataLengthTotal(); i++)
            if (!Objects.equals(Number1.getDigitArray().get(i), Number2.getDigitArray().get(i))) {
                return false;
            }
        return true;
    }

    public static boolean isGreater(CustomNumber Number1, CustomNumber Number2) {
        boolean retVal = !Number1.isZero() || !Number2.isZero();
        if (Number1.isZero() && !Number2.isZero()) {
            retVal = !Number2.isPositive();
        }
        if (!Number1.isZero() && Number2.isZero()) {
            retVal = Number1.isPositive();
        }
        if (!Number1.isZero() && !Number2.isZero()) {
            if (Number1.isPositive() && Number2.isPositive()) {
                if (Number1.getlengthBeforeComma() < Number2.getlengthBeforeComma()) {
                    retVal = false;
                }
                if (Number1.getlengthBeforeComma() == Number2.getlengthBeforeComma()) {
                    if (areEqual(Number1, Number2)) {
                        retVal = false;
                    }

                    Number1.evenOutNumbers(Number2);
                    Number2.evenOutNumbers(Number1);

                    for (int i = 0; i < Number1.getDigitArray().size(); i++) {
                        if (Number1.getDigitArray().get(i) > Number2.getDigitArray().get(i)) {
                            retVal = true;
                            break;
                        }
                        if (Number1.getDigitArray().get(i) < Number2.getDigitArray().get(i)) {
                            retVal = false;
                            break;
                        }
                    }
                }
            }
            if (Number1.isPositive() && Number2.isNegative()) {
                retVal = true;
            }
            if (Number1.isNegative() && Number2.isPositive()) {
                retVal = false;
            }
            if (Number1.isNegative() && Number2.isNegative()) {
                if (Number1.getlengthBeforeComma() > Number2.getlengthBeforeComma()) {
                    retVal = false;
                }

                if (Number1.getlengthBeforeComma() == Number2.getlengthBeforeComma()) {
                    if (areEqual(Number1, Number2)) {
                        retVal = false;
                    }

                    Number1.evenOutNumbers(Number2);
                    Number2.evenOutNumbers(Number1);

                    for (int i = 0; i < Number1.getDigitArray().size(); i++) {
                        if (Number1.getDigitArray().get(i) > Number2.getDigitArray().get(i)) {
                            retVal = false;
                            break;
                        }
                        if (Number1.getDigitArray().get(i) < Number2.getDigitArray().get(i)) {
                            retVal = true;
                            break;
                        }
                    }
                }
            }
        }
        Number1.cleanCustomNumber();
        Number2.cleanCustomNumber();
        return retVal;
    }

    public static boolean isGreaterOrEqual(CustomNumber Number1, CustomNumber Number2) {
        if (areEqual(Number1, Number2)) {
            return true;
        }
        return isGreater(Number1, Number2);
    }

    public static boolean isSmaller(CustomNumber Number1, CustomNumber Number2) {
        return !isGreaterOrEqual(Number1, Number2);
    }

    public static boolean isSmallerOrEqual(CustomNumber Number1, CustomNumber Number2) {
        return (areEqual(Number1, Number2) || isGreater(Number2, Number1));
    }

    /*
    A = B
(+3) + (+3)	    (+A) + (+B)		        (+3) + (+3)		        6	AddUp(A,B)		        case1
(+3) + (-3)	    0			            (+3) - (+3)		        0	Zero			        case0
(+3) - (+3)	    0			            (+3) - (+3)		        0	Zero			        case0
(+3) - (-3)	    (+A) + (+B)		        (+3) + (+3)		        6	AddUp(A,B)		        case1
(-3) + (+3)	    0			            (+3) - (+3)		        0	Zero			        case0
(-3) + (-3)	    ((+A) + (+B)) * (-1)	((+3) + (+3)) * (-1)	-6	AddUp(A,B) * (-1)	    case2
(-3) - (+3)	    ((+A) + (+B)) * (-1)	((+3) + (+3)) * (-1)	-6	AddUp(A,B) * (-1)	    case2
(-3) - (-3)	    0			            (+3) - (+3)		        0	Zero			        case0

    A > B
(+4) + (+3)	    (+A) + (+B)		        (+4) + (+3)		        7	AddUp(A,B)		        case1
(+4) + (-3)	    (+A) - (+B)		        (+4) - (+3)		        1	Subtract(A,B)		    case3
(+4) - (+3)	    (+A) - (+B)		        (+4) - (+3)		        1	Subtract(A,B)		    case3
(+4) - (-3)	    (+A) + (+B)		        (+4) + (+3)		        7	AddUp(A,B)		        case1
(-4) + (+3)	    ((+A) - (+B)) * (-1)	((+4) - (+3)) * (-1)	-1	Subtract(A,B) * (-1)	case4
(-4) + (-3)	    ((+A) + (+B)) * (-1)	((+4) + (+3)) * (-1)	-7	AddUp(A,B) * (-1)	    case2
(-4) - (+3)	    ((+A) + (+B)) * (-1)	((+4) + (+3)) * (-1)	-7	AddUp(A,B) * (-1)	    case2
(-4) - (-3)	    ((+A) - (+B)) * (-1)	((+4) - (+3)) * (-1)	-1	Subtract(A,B) * (-1)	case4

    B > A
(+3) + (+4)	    (+A) + (+B)		        (+3) + (+4)		        7	AddUp(A,B)		        case1
(+3) + (-4)	    ((+B) - (+A)) * (-1)	((+4) - (+3)) * (-1)	-1	Subtract(B,A) * (-1)	case6
(+3) - (+4)	    ((+B) - (+A)) * (-1)	((+4) - (+3)) * (-1)	-1	Subtract(B,A) * (-1)	case6
(+3) - (-4)	    (+A) + (+B)		        (+3) + (+4)		        7	AddUp(A,B)		        case1
(-3) + (+4)	    (+B) - (+A)		        (+4) - (+3)		        1	Subtract(B,A)		    case5
(-3) + (-4)	    ((+A) + (+B)) * (-1)	((+3) + (+4)) * (-1)	-7	AddUp(A,B) * (-1)	    case2
(-3) - (+4)	    ((+A) + (+B)) * (-1)	((+3) + (+4)) * (-1)	-7	AddUp(A,B) * (-1)	    case2
(-3) - (-4)	    (+B) - (+A)		        (+4) - (+3)		        1	Subtract(B,A)		    case5
     */

    private static CustomNumber addUpSubstractionLogic(CustomNumber Number1, CustomNumber Number2, boolean Mode) {
        CustomNumber returnCustomNumber = new CustomNumber();
        boolean number1Positive = Number1.isPositive();
        boolean number1Negative = Number1.isNegative();
        boolean number2Positive = Number2.isPositive();
        boolean number2Negative = Number2.isNegative();
        Number1.setPositive();
        Number2.setPositive();

        if (areEqual(Number1, Number2)) {
            if (number1Positive && Mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase1(Number1, Number2);
            } else if (number1Positive && Mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase0();
            } else if (number1Positive && !Mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase0();
            } else if (number1Positive && !Mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase1(Number1, Number2);
            } else if (number1Negative && Mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase0();
            } else if (number1Negative && Mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase2(Number1, Number2);
            } else if (number1Negative && !Mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase2(Number1, Number2);
            } else if (number1Negative && !Mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase0();
            }
        } else if (isGreater(Number1, Number2)) {
            if (number1Positive && Mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase1(Number1, Number2);
            } else if (number1Positive && Mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase3(Number1, Number2);
            } else if (number1Positive && !Mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase3(Number1, Number2);
            } else if (number1Positive && !Mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase1(Number1, Number2);
            } else if (number1Negative && Mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase4(Number1, Number2);
            } else if (number1Negative && Mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase2(Number1, Number2);
            } else if (number1Negative && !Mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase2(Number1, Number2);
            } else if (number1Negative && !Mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase4(Number1, Number2);
            }
        } else if (isSmaller(Number1, Number2)) {
            if (number1Positive && Mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase1(Number1, Number2);
            } else if (number1Positive && Mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase6(Number1, Number2);
            } else if (number1Positive && !Mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase6(Number1, Number2);
            } else if (number1Positive && !Mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase1(Number1, Number2);
            } else if (number1Negative && Mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase5(Number1, Number2);
            } else if (number1Negative && Mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase2(Number1, Number2);
            } else if (number1Negative && !Mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase2(Number1, Number2);
            } else if (number1Negative && !Mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase5(Number1, Number2);
            }
        }
        Number1.setSign(number1Positive);
        Number2.setSign(number2Positive);

        return returnCustomNumber;
    }

    private static CustomNumber addUpSubtractionLogicCase0() {
        CustomNumber returnCustomNumber = new CustomNumber();
        returnCustomNumber.setZero();
        return returnCustomNumber;
    }

    private static CustomNumber addUpSubtractionLogicCase1(CustomNumber Number1, CustomNumber Number2) {
        CustomNumber returnCustomNumber = new CustomNumber();
        returnCustomNumber = internalAddition(Number1, Number2);
        return returnCustomNumber;
    }

    private static CustomNumber addUpSubtractionLogicCase2(CustomNumber Number1, CustomNumber Number2) {
        CustomNumber returnCustomNumber = new CustomNumber();
        returnCustomNumber = internalAddition(Number1, Number2);
        returnCustomNumber.setNegative();
        return returnCustomNumber;
    }

    private static CustomNumber addUpSubtractionLogicCase3(CustomNumber Number1, CustomNumber Number2) {
        CustomNumber returnCustomNumber = new CustomNumber();
        returnCustomNumber = internalSubtraction(Number1, Number2);
        return returnCustomNumber;
    }

    private static CustomNumber addUpSubtractionLogicCase4(CustomNumber Number1, CustomNumber Number2) {
        CustomNumber returnCustomNumber = new CustomNumber();
        returnCustomNumber = internalSubtraction(Number1, Number2);
        returnCustomNumber.setNegative();
        return returnCustomNumber;
    }

    private static CustomNumber addUpSubtractionLogicCase5(CustomNumber Number1, CustomNumber Number2) {
        CustomNumber returnCustomNumber = new CustomNumber();
        returnCustomNumber = internalSubtraction(Number2, Number1);
        return returnCustomNumber;
    }

    private static CustomNumber addUpSubtractionLogicCase6(CustomNumber Number1, CustomNumber Number2) {
        CustomNumber returnCustomNumber = new CustomNumber();
        returnCustomNumber = internalSubtraction(Number2, Number1);
        returnCustomNumber.setNegative();
        return returnCustomNumber;
    }

    /* Minuend and Subtrahend must be positive
       Minuend must be bigger than the Subtrahend
     */
    private static CustomNumber internalSubtraction(CustomNumber Minuend, CustomNumber Subtrahend) {
        CustomNumber returnCustomNumber = new CustomNumber();
        byte carry = 0;

        if(!Subtrahend.isZero()) {
            Minuend.evenOutNumbers(Subtrahend);
            Subtrahend.evenOutNumbers(Minuend);
            byte tempDigitResult = 0;
            for (int i = Minuend.getDigitArray().size() - 1; i >= 0; i--) {
                if ((Subtrahend.getDigitArray().get(i) + carry) <= (Minuend.getDigitArray().get(i))) {
                    tempDigitResult = (byte) (Minuend.getDigitArray().get(i) - (Subtrahend.getDigitArray().get(i) + carry));
                    carry = 0;
                    //System.out.println("norm:  " + tempDigitResult);
                }
                if ((Subtrahend.getDigitArray().get(i) + carry) > (Minuend.getDigitArray().get(i))) {
                    tempDigitResult = (byte) ((Minuend.getDigitArray().get(i) + (byte) 10) - (Subtrahend.getDigitArray().get(i) + carry));
                    carry = 1;
                    //System.out.println("carry: " + tempDiggitResult);
                }

                returnCustomNumber.appendDataDigit(tempDigitResult, "l");
                //System.out.println(Minuend.getDigitArray().get(i));

            }

            if (Minuend.getlengthAfterComma() > 0) {
                returnCustomNumber.shiftRight(Minuend.getlengthAfterComma());
            }
            if (Minuend.getlengthAfterComma() < 0) {
                returnCustomNumber.shiftLeft(Minuend.getlengthAfterComma() * -1);
            }

            Minuend.cleanCustomNumber();
            Subtrahend.cleanCustomNumber();
            returnCustomNumber.cleanCustomNumber();
        }
        else
        {
            returnCustomNumber.is(Minuend);
        }

        return returnCustomNumber;
    }

    /* Adds up the absolute values of two Summa-nds
        Sign of the returnNumber always positive
     */
    private static CustomNumber internalAddition(CustomNumber Summand1, CustomNumber Summand2) {
        CustomNumber returnCustomNumber = new CustomNumber();

        if (!Summand1.isZero() && !Summand2.isZero()) {
            byte carry = 0;
            if (!(Summand1.getDataLengthTotal() == Summand2.getDataLengthTotal() && Summand1.getlengthBeforeComma() == Summand2.getlengthBeforeComma() && Summand1.getlengthAfterComma() == Summand2.getlengthAfterComma())) {
                Summand1.evenOutNumbers(Summand2);
                Summand2.evenOutNumbers(Summand1);
            }

            returnCustomNumber.setLengthBeforeComma(Summand1.getlengthBeforeComma());
            returnCustomNumber.setLengthAfterComma(Summand1.getlengthAfterComma());

            for (int i = Summand1.getDataLengthTotal() - 1; i >= 0 || carry != 0; i--) {
                byte tempDigitResult = 0;
                tempDigitResult = (byte) (Summand1.getDigitArray().get(i) + Summand2.getDigitArray().get(i) + carry);
                if (tempDigitResult >= 10) {
                    tempDigitResult = (byte) (tempDigitResult - 10);
                    carry = 1;
                } else {
                    carry = 0;
                }

                returnCustomNumber.digitArray.add(0, tempDigitResult);
                if (i == 0 && carry != 0) {
                    returnCustomNumber.digitArray.add(0, carry);
                    returnCustomNumber.lengthBeforeComma += 1;
                    carry = 0;
                }
            }
        } else {
            if (Summand2.isZero() && !Summand1.isZero()) {
                returnCustomNumber.is(Summand1);
            }
            if (Summand1.isZero() && !Summand2.isZero()) {
                returnCustomNumber.is(Summand2);
            }
            if (Summand1.isZero() && Summand2.isZero()) {
                returnCustomNumber.setZero();
            }
        }
        Summand1.cleanCustomNumber();
        Summand2.cleanCustomNumber();
        returnCustomNumber.cleanCustomNumber();
        return returnCustomNumber;
    }

    /* multiplies 2 Numbers; always returns a positive result */
    private static CustomNumber internalMultiplication(CustomNumber Factor1, CustomNumber Factor2) {
        CustomNumber returnCustomNumber = new CustomNumber();

        CustomNumber tempFactor1 = new CustomNumber();
        CustomNumber tempFactor2 = new CustomNumber();

        CustomNumber lineResult = new CustomNumber();

        int digitsCountToShiftRight = 0;
        returnCustomNumber.setZero();

        if (Factor1.getlengthAfterComma() > 0) {
            digitsCountToShiftRight = digitsCountToShiftRight + Factor1.getlengthAfterComma();
        }

        if (Factor2.getlengthAfterComma() > 0) {
            digitsCountToShiftRight = digitsCountToShiftRight + Factor2.getlengthAfterComma();
        }

        tempFactor1.is(Factor1);
        tempFactor2.is(Factor2);

        if (tempFactor1.getlengthAfterComma() > 0) {
            tempFactor1.setLengthBeforeComma(tempFactor1.getlengthBeforeComma() + tempFactor1.getlengthAfterComma());
            tempFactor1.setLengthAfterComma(0);
        }
        if (tempFactor2.getlengthAfterComma() > 0) {
            tempFactor2.setLengthBeforeComma(tempFactor2.getlengthBeforeComma() + tempFactor2.getlengthAfterComma());
            tempFactor2.setLengthAfterComma(0);
        }

        for (int i = 0; i < tempFactor2.getDataLengthTotal(); i++) {
            switch (tempFactor2.getDigitArray().get(i)) {
                case 0 -> lineResult.setZero();
                case 1 -> lineResult.is(tempFactor1);
                case 2 -> lineResult = internalAddition(tempFactor1, tempFactor1);
                default -> {
                    lineResult = oneDiggitCustomNumberMultiplication(tempFactor1, tempFactor2.getDigitArray().get(i));
                    lineResult.cleanCustomNumber();
                }
            }

            lineResult.shiftLeft(tempFactor2.getlengthBeforeComma() - i - 1);
            returnCustomNumber = internalAddition(lineResult, returnCustomNumber);
        }
        if (digitsCountToShiftRight > 0) {
            returnCustomNumber.shiftRight(digitsCountToShiftRight);
        }
        return returnCustomNumber;
    }

    /*
       Hilfsfunktion fuer die Multiplikation:
       Es wird Ziffer fuer Ziffer multipliziert, dann in eine Liste von CustomNumbers gespeichert
       richtig geshiftet und dann addiert --> GANZE MULTIPLIKATION
        123*456
        492
      +  615
      +   738
      ----------
        56088

        Rückgabewerte sind immer ganze Zahlen ohne Nachkommastellen.
    */

    private static CustomNumber oneDiggitCustomNumberMultiplication(CustomNumber Factor, byte DigitFactor) {
        CustomNumber returnCustomNumber = new CustomNumber();
        if (Factor.isZero() || DigitFactor == 0) {
            returnCustomNumber.setZero();
        } else {

            while (Factor.getDigitArray().size() < Factor.getlengthBeforeComma()) {
                Factor.getDigitArray().add(Factor.getDigitArray().size(), (byte) 0);
                Factor.lengthAfterComma += 1;
            }

            byte carry = 0;
            for (int i = Factor.getDataLengthTotal() - 1; i >= 0 || carry != 0; i--) {
                byte tempDigitResult = 0;
                byte digitResult = 0;
                tempDigitResult = (byte) (Factor.getDigitArray().get(i) * DigitFactor + carry);
                digitResult = (byte) ((tempDigitResult) % 10);
                carry = (byte) (tempDigitResult / 10);

                returnCustomNumber.appendDataDigit(digitResult, "L");
                if (i == 0 && carry != 0) {
                    returnCustomNumber.digitArray.add(0, carry);
                    returnCustomNumber.lengthBeforeComma += 1;
                    carry = 0;
                }
            }
        }
        return returnCustomNumber;
    }

    public static void test(int Iterations) {
        CustomNumber ctValA = new CustomNumber();
        CustomNumber ctValB = new CustomNumber();
        double dbValA;
        double dbValB;


        for(double i = Iterations * -1; i <= Iterations;i+=1)
        {
            for(double k = Iterations * -1; k <= Iterations; k+= 1)
            {
                for(double j = Iterations * -1; j <= Iterations; j+= 1)
                {
                    dbValA = j + i;
                    dbValB = k - i;
                    ctValA.setValue(String.valueOf(dbValA));
                    ctValB.setValue(String.valueOf(dbValB));
                    if(!testCase(ctValA,ctValB,dbValA,dbValB))
                    {
                        System.out.println(i);
                        System.out.println(k);
                        System.out.println(j);
                        return;
                    }
                }
            }
        }
        System.out.println("No Error in occurred while testing!");
    }

    private static boolean testCase(CustomNumber CtValA, CustomNumber CtValB, double DbValA, double DbValB) {
        CustomNumber ctValRes = new CustomNumber();
        CustomNumber ctDbTempRes = new CustomNumber();
        double dbValRes;

        ctValRes = addUp(CtValA, CtValB);
        dbValRes = DbValA + DbValB;

        ctDbTempRes.setValue(String.valueOf(dbValRes));
        if (!ctValRes.generateString().equals(ctDbTempRes.generateString())) {
            testCaseErrorMessage(ctValRes, ctDbTempRes, dbValRes, CtValA, CtValB, DbValA, DbValB);
            return false;
        }

        ctValRes = subtract(CtValA, CtValB);
        dbValRes = DbValA - DbValB;

        ctDbTempRes.setValue(String.valueOf(dbValRes));
        if (!ctValRes.generateString().equals(ctDbTempRes.generateString())) {
            testCaseErrorMessage(ctValRes, ctDbTempRes, dbValRes, CtValA, CtValB, DbValA, DbValB);
            return false;
        }

        ctValRes = multiply(CtValA, CtValB);
        dbValRes = DbValA * DbValB;

        ctDbTempRes.setValue(String.valueOf(dbValRes));
        if (!ctValRes.generateString().equals(ctDbTempRes.generateString())) {
            testCaseErrorMessage(ctValRes, ctDbTempRes, dbValRes, CtValA, CtValB, DbValA, DbValB);
            return false;
        }

        CustomNumber tmpCtValA = new CustomNumber();
        CustomNumber tmpCtValB = new CustomNumber();

        double tmpDbValA = DbValA * 10;
        double tmpDbValB = DbValB * 10;

        tmpCtValA.is(CtValA);
        tmpCtValB.is(CtValB);

        tmpCtValA.shiftLeft();
        tmpCtValB.shiftLeft();

        ctValRes = multiply(tmpCtValA, tmpCtValB);
        dbValRes = tmpDbValA * tmpDbValB;

        ctDbTempRes.setValue(String.valueOf(dbValRes));
        if (!ctValRes.generateString().equals(ctDbTempRes.generateString())) {
            testCaseErrorMessage(ctValRes, ctDbTempRes, dbValRes, tmpCtValA, tmpCtValB, tmpDbValA, tmpDbValB);
            return false;
        }
        return true;
    }

    private static void testCaseErrorMessage(CustomNumber CtValRes, CustomNumber CtDbTempRes, double DbValRes, CustomNumber CtValA, CustomNumber CtValB, double DbValA, double DbValB) {
        System.out.println("========================= ERROR =========================");
        System.out.println("Compared Strings:");
        System.out.println("ct:    " + CtValRes.generateString());
        System.out.println("ctTmp: " + CtDbTempRes.generateString());
        System.out.println("db:    " + String.valueOf(DbValRes));
        System.out.println("Custom Number Calculated Value: ");
        print(CtValRes);
        System.out.println("Double Calculated Value: ");
        print(CtDbTempRes);
        System.out.println("Custom Number Input: ");
        print(CtValA);
        print(CtValB);
        System.out.println("Double Input:");
        System.out.println(DbValA);
        System.out.println(DbValB);
        System.out.println("======================= ERROR END =======================");
    }
}