public class CustomNumberUtils {
    public static void print(CustomNumber CustomNumberToPrint) {
        //CustomNumberToPrint.cleanCustomNumber();

        System.out.println("Zahl ist Null:          " + CustomNumberToPrint.isZero());
        System.out.println("Zahl ist Positiv:       " + CustomNumberToPrint.isPositive());
        if (CustomNumberToPrint.digitArray.size() != 0) {
            System.out.println("Array.get(0) in var:    " + CustomNumberToPrint.digitArray.get(0));
        } else {
            System.out.println("Array.get(0) in var:    IST NULL");
        }
        System.out.println("Stellen vor dem Komma:  " + CustomNumberToPrint.lengthBeforeComma);
        System.out.println("Stellen nach dem Komma: " + CustomNumberToPrint.lengthAfterComma);
        System.out.println("Position des Kommas:    nach der " + CustomNumberToPrint.lengthBeforeComma + ". Ziffer");
        System.out.println("Anzahl der Ziffern:     " + CustomNumberToPrint.getDataLengthTotal());
        System.out.println("Zahl:                   " + CustomNumberToPrint.generateString());
        System.out.println("-------------------------------------------------------");
    }

    public static CustomNumber addUp(CustomNumber Summand1, CustomNumber Summand2) {
        CustomNumber returnCustomNumber = new CustomNumber();
        returnCustomNumber = addUpSubstractionLogic(Summand1, Summand2, true);
        return returnCustomNumber;
    }

    public static CustomNumber subtract(CustomNumber Summand1, CustomNumber Summand2) {
        CustomNumber returnCustomNumber = new CustomNumber();
        returnCustomNumber = addUpSubstractionLogic(Summand1, Summand2, false);
        return returnCustomNumber;
    }

    public static CustomNumber multiply(CustomNumber Faktor1, CustomNumber Faktor2) {
        CustomNumber returnCustomNumber = new CustomNumber();
        CustomNumber numberOne = new CustomNumber();
        numberOne.setValue("+1.");

        if (Faktor1.isZero() || Faktor2.isZero()) {
            returnCustomNumber.setZero();
        } else if (areDigitArraysEqual(Faktor1, numberOne)) {
            returnCustomNumber.is(Faktor2);

            if (Faktor1.getlengthBeforeComman() > 1) {
                returnCustomNumber.shiftLeft(Faktor1.getlengthBeforeComman() - 1);
            } else if (Faktor1.getlengthBeforeComman() < 1) {
                returnCustomNumber.shiftRight((Faktor1.getlengthBeforeComman() * -1) + 1);
            }
        } else if (areDigitArraysEqual(Faktor2, numberOne)) {
            returnCustomNumber.is(Faktor1);

            if (Faktor2.getlengthBeforeComman() > 1) {
                returnCustomNumber.shiftLeft(Faktor2.getlengthBeforeComman() - 1);
            } else if (Faktor2.getlengthBeforeComman() < 1) {
                returnCustomNumber.shiftRight((Faktor2.getlengthBeforeComman() * -1) + 1);
            }
        } else {
            if (Faktor2.getDataLengthTotal() > Faktor1.getDataLengthTotal()) {
                returnCustomNumber = internalMultiplication(Faktor2, Faktor1);
            } else {
                returnCustomNumber = internalMultiplication(Faktor1, Faktor2);
            }
        }
        if (Faktor1.isPositive() != Faktor2.isPositive()) {
            returnCustomNumber.setNegative();
        }

        return returnCustomNumber;
    }

    public static boolean areEqual(CustomNumber Number1, CustomNumber Number2) {
        if (!((Number1.getlengthBeforeComman() == Number2.getlengthBeforeComman()) && (Number1.getlengthAfterComman() == Number2.getlengthAfterComman()) && (Number1.getDataLengthTotal() == Number2.getDataLengthTotal()))) {
            return false;
        }

        if (Number1.isPositive() == Number2.isNegative())
            return false;

        for (int i = 0; i < Number1.getDataLengthTotal(); i++)
            if (Number1.getDigitArray().get(i) != Number2.getDigitArray().get(i)) {
                return false;
            }

        return true;
    }

    public static boolean areDigitArraysEqual(CustomNumber Number1, CustomNumber Number2) {
        if (!(Number1.getDataLengthTotal() == Number2.getDataLengthTotal())) {
            return false;
        }

        for (int i = 0; i < Number1.getDataLengthTotal(); i++)
            if (Number1.getDigitArray().get(i) != Number2.getDigitArray().get(i)) {
                return false;
            }
        return true;
    }

    public static boolean isGreater(CustomNumber Number1, CustomNumber Number2) {
        boolean retVal = true;
        if (Number1.isZero() && Number2.isZero()) {
            retVal = false;
        }
        if (Number1.isZero() && !Number2.isZero()) {
            if (Number2.isPositive()) {
                retVal = false;
            } else {
                retVal = true;
            }
        }
        if (!Number1.isZero() && Number2.isZero()) {
            if (Number1.isPositive()) {
                retVal = true;
            } else {
                retVal = false;
            }
        }
        if (!Number1.isZero() && !Number2.isZero()) {
            if (Number1.isPositive() && Number2.isPositive()) {
                if (Number1.getlengthBeforeComman() < Number2.getlengthBeforeComman()) {
                    retVal = false;
                }
                if (Number1.getlengthBeforeComman() == Number2.getlengthBeforeComman()) {
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
                if (Number1.getlengthBeforeComman() > Number2.getlengthBeforeComman()) {
                    retVal = false;
                }

                if (Number1.getlengthBeforeComman() == Number2.getlengthBeforeComman()) {
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
                returnCustomNumber = addUpSubstractionLogicCase1(Number1, Number2);
            } else if (number1Positive && Mode && number2Negative) {
                returnCustomNumber = addUpSubstractionLogicCase0();
            } else if (number1Positive && !Mode && number2Positive) {
                returnCustomNumber = addUpSubstractionLogicCase0();
            } else if (number1Positive && !Mode && number2Negative) {
                returnCustomNumber = addUpSubstractionLogicCase1(Number1, Number2);
            } else if (number1Negative && Mode && number2Positive) {
                returnCustomNumber = addUpSubstractionLogicCase0();
            } else if (number1Negative && Mode && number2Negative) {
                returnCustomNumber = addUpSubstractionLogicCase2(Number1, Number2);
            } else if (number1Negative && !Mode && number2Positive) {
                returnCustomNumber = addUpSubstractionLogicCase2(Number1, Number2);
            } else if (number1Negative && !Mode && number2Negative) {
                returnCustomNumber = addUpSubstractionLogicCase0();
            }
        } else if (isGreater(Number1, Number2)) {
            if (number1Positive && Mode && number2Positive) {
                returnCustomNumber = addUpSubstractionLogicCase1(Number1, Number2);
            } else if (number1Positive && Mode && number2Negative) {
                returnCustomNumber = addUpSubstractionLogicCase3(Number1, Number2);
            } else if (number1Positive && !Mode && number2Positive) {
                returnCustomNumber = addUpSubstractionLogicCase3(Number1, Number2);
            } else if (number1Positive && !Mode && number2Negative) {
                returnCustomNumber = addUpSubstractionLogicCase1(Number1, Number2);
            } else if (number1Negative && Mode && number2Positive) {
                returnCustomNumber = addUpSubstractionLogicCase4(Number1, Number2);
            } else if (number1Negative && Mode && number2Negative) {
                returnCustomNumber = addUpSubstractionLogicCase2(Number1, Number2);
            } else if (number1Negative && !Mode && number2Positive) {
                returnCustomNumber = addUpSubstractionLogicCase2(Number1, Number2);
            } else if (number1Negative && !Mode && number2Negative) {
                returnCustomNumber = addUpSubstractionLogicCase4(Number1, Number2);
            }
        } else if (isSmaller(Number1, Number2)) {
            if (number1Positive && Mode && number2Positive) {
                returnCustomNumber = addUpSubstractionLogicCase1(Number1, Number2);
            } else if (number1Positive && Mode && number2Negative) {
                returnCustomNumber = addUpSubstractionLogicCase6(Number1, Number2);
            } else if (number1Positive && !Mode && number2Positive) {
                returnCustomNumber = addUpSubstractionLogicCase6(Number1, Number2);
            } else if (number1Positive && !Mode && number2Negative) {
                returnCustomNumber = addUpSubstractionLogicCase1(Number1, Number2);
            } else if (number1Negative && Mode && number2Positive) {
                returnCustomNumber = addUpSubstractionLogicCase5(Number1, Number2);
            } else if (number1Negative && Mode && number2Negative) {
                returnCustomNumber = addUpSubstractionLogicCase2(Number1, Number2);
            } else if (number1Negative && !Mode && number2Positive) {
                returnCustomNumber = addUpSubstractionLogicCase2(Number1, Number2);
            } else if (number1Negative && !Mode && number2Negative) {
                returnCustomNumber = addUpSubstractionLogicCase5(Number1, Number2);
            }
        }
        Number1.setSign(number1Positive);
        Number2.setSign(number2Positive);

        return returnCustomNumber;
    }

    private static CustomNumber addUpSubstractionLogicCase0() {
        CustomNumber returnCustomNumber = new CustomNumber();
        returnCustomNumber.setZero();
        return returnCustomNumber;
    }

    private static CustomNumber addUpSubstractionLogicCase1(CustomNumber Number1, CustomNumber Number2) {
        CustomNumber returnCustomNumber = new CustomNumber();
        returnCustomNumber = internalAddition(Number1, Number2);
        return returnCustomNumber;
    }

    private static CustomNumber addUpSubstractionLogicCase2(CustomNumber Number1, CustomNumber Number2) {
        CustomNumber returnCustomNumber = new CustomNumber();
        returnCustomNumber = internalAddition(Number1, Number2);
        returnCustomNumber.setNegative();
        return returnCustomNumber;
    }

    private static CustomNumber addUpSubstractionLogicCase3(CustomNumber Number1, CustomNumber Number2) {
        CustomNumber returnCustomNumber = new CustomNumber();
        returnCustomNumber = internalSubstraction(Number1, Number2);
        return returnCustomNumber;
    }

    private static CustomNumber addUpSubstractionLogicCase4(CustomNumber Number1, CustomNumber Number2) {
        CustomNumber returnCustomNumber = new CustomNumber();
        returnCustomNumber = internalSubstraction(Number1, Number2);
        returnCustomNumber.setNegative();
        return returnCustomNumber;
    }

    private static CustomNumber addUpSubstractionLogicCase5(CustomNumber Number1, CustomNumber Number2) {
        CustomNumber returnCustomNumber = new CustomNumber();
        returnCustomNumber = internalSubstraction(Number2, Number1);
        return returnCustomNumber;
    }

    private static CustomNumber addUpSubstractionLogicCase6(CustomNumber Number1, CustomNumber Number2) {
        CustomNumber returnCustomNumber = new CustomNumber();
        returnCustomNumber = internalSubstraction(Number2, Number1);
        returnCustomNumber.setNegative();
        return returnCustomNumber;
    }

    /* Minuend and Subtrahend must be positive
       Minuend must be bigger than the Subtrahend
     */
    private static CustomNumber internalSubstraction(CustomNumber Minuend, CustomNumber Subtrahend) {
        CustomNumber returnCustomNumber = new CustomNumber();
        byte carry = 0;

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

        if (Minuend.getlengthAfterComman() > 0) {
            returnCustomNumber.shiftRight(Minuend.getlengthAfterComman());
        }
        if (Minuend.getlengthAfterComman() < 0) {
            returnCustomNumber.shiftLeft(Minuend.getlengthAfterComman() * -1);
        }

        Minuend.cleanCustomNumber();
        Subtrahend.cleanCustomNumber();
        returnCustomNumber.cleanCustomNumber();
        return returnCustomNumber;
    }

    /* Adds up the absolute values of two Summands
        Sign of the returnNumber always positive
     */
    private static CustomNumber internalAddition(CustomNumber Summand1, CustomNumber Summand2) {
        CustomNumber returnCustomNumber = new CustomNumber();

        if (!Summand1.isZero() && !Summand2.isZero()) {
            byte carry = 0;
            if (!(Summand1.getDataLengthTotal() == Summand2.getDataLengthTotal() && Summand1.getlengthBeforeComman() == Summand2.getlengthBeforeComman() && Summand1.getlengthAfterComman() == Summand2.getlengthAfterComman())) {
                Summand1.evenOutNumbers(Summand2);
                Summand2.evenOutNumbers(Summand1);
            }

            returnCustomNumber.lengthBeforeComma = Summand1.getlengthBeforeComman();
            returnCustomNumber.lengthAfterComma = Summand1.getlengthAfterComman();

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
                    returnCustomNumber.lengthBeforeComma = returnCustomNumber.lengthBeforeComma + 1;
                    carry = 0;
                }
            }
        } else {
            if (Summand2.isZero() && !Summand1.isZero()) {
                returnCustomNumber.lengthBeforeComma = Summand1.lengthBeforeComma;
                returnCustomNumber.lengthAfterComma = Summand1.lengthAfterComma;
                returnCustomNumber.digitArray = Summand1.digitArray;
                for (int i = 0; i < Summand1.getDataLengthTotal(); i++) {
                    returnCustomNumber.digitArray.add(Summand1.getDigitArray().get(i));
                }
            }
            if (Summand1.isZero() && !Summand2.isZero()) {
                returnCustomNumber.lengthBeforeComma = Summand2.lengthBeforeComma;
                returnCustomNumber.lengthAfterComma = Summand2.lengthAfterComma;
                for (int i = 0; i < Summand2.getDataLengthTotal(); i++) {
                    returnCustomNumber.digitArray.add(Summand2.getDigitArray().get(i));
                }
            }
            if (Summand1.isZero() && Summand2.isZero()) {
                returnCustomNumber.setZero();
            }
        }
        Summand1.cleanCustomNumber();
        Summand2.cleanCustomNumber();
        return returnCustomNumber;
    }

    /* multiplies 2 Numbers; always returns a positive result */
    private static CustomNumber internalMultiplication(CustomNumber Faktor1, CustomNumber Faktor2) {
        CustomNumber returnCustomNumber = new CustomNumber();

        int digitsCountToShiftRight = 0;
        returnCustomNumber.setZero();

        if (Faktor1.getlengthAfterComman() > 0) {
            digitsCountToShiftRight = digitsCountToShiftRight + Faktor1.getlengthAfterComman();
        }

        if (Faktor2.getlengthAfterComman() > 0) {
            digitsCountToShiftRight = digitsCountToShiftRight + Faktor2.getlengthAfterComman();
        }

        for (int i = 0; i < Faktor2.getDataLengthTotal(); i++) {
            CustomNumber multiplicationLineResult = new CustomNumber();
            CustomNumber singleDigitCustomNumber = new CustomNumber();
            if (Faktor2.getDigitArray().get(i) == 0) {
                multiplicationLineResult.setZero();
                //System.out.println("@@@@@@@");
            } else if (Faktor2.getDigitArray().get(i) == 1) {
                multiplicationLineResult.is(Faktor1);
                //System.out.println("WWWWWWW");
            } else {
                singleDigitCustomNumber.setValue(String.valueOf(Faktor2.getDigitArray().get(i)) + ".");
                multiplicationLineResult = oneDiggitCustomNumberMultiplication(Faktor1, singleDigitCustomNumber);
            }
            multiplicationLineResult.shiftLeft(Faktor2.getDataLengthTotal() - i - 1);
            returnCustomNumber = internalAddition(returnCustomNumber, multiplicationLineResult);
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
    */

    private static CustomNumber oneDiggitCustomNumberMultiplication(CustomNumber Faktor1, CustomNumber Faktor2) {
        CustomNumber returnCustomNumber = new CustomNumber();
        if (Faktor1.isZero() || Faktor2.isZero()) {
            returnCustomNumber.setZero();
        } else {
            while (Faktor1.getDigitArray().size() < Faktor1.getlengthBeforeComman()) {
                Faktor1.getDigitArray().add(Faktor1.getDigitArray().size(), (byte) 0);
                Faktor1.lengthAfterComma += 1;
            }

            byte carry = 0;
            for (int i = Faktor1.getDataLengthTotal() - 1; i >= 0 || carry != 0; i--) {
                byte tempDigitResult = 0;
                byte digitResult = 0;
                tempDigitResult = (byte) (Faktor1.getDigitArray().get(i) * Faktor2.getDigitArray().get(0) + carry);
                digitResult = (byte) ((tempDigitResult) % 10);
                carry = (byte) (tempDigitResult / 10);

                returnCustomNumber.appendDataDigit(digitResult, "L");
                if (i == 0 && carry != 0) {
                    returnCustomNumber.digitArray.add(0, carry);
                    returnCustomNumber.lengthBeforeComma = returnCustomNumber.lengthBeforeComma + 1;
                    carry = 0;
                }
            }
        }
        return returnCustomNumber;
    }
}