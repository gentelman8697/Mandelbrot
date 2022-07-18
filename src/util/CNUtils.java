package util;
import datatypes.*;

public class CNUtils {
    public static CustomNumber addUp(CustomNumber summand1, CustomNumber summand2, int relevant) {
        CustomNumber returnCustomNumber = new CustomNumber();
        returnCustomNumber = addUpSubstractionLogic(summand1, summand2, true);
        returnCustomNumber.cropRelevant(relevant);
        return returnCustomNumber;
    }

    public static CustomNumber addUp(CustomNumber summand1, CustomNumber summand2) {
        return new CustomNumber(addUp(summand1, summand2, -1));
    }

    public static CustomNumber subTr(CustomNumber minuend, CustomNumber subtrahend, int relevant) {
        CustomNumber returnCustomNumber = new CustomNumber();
        returnCustomNumber = addUpSubstractionLogic(minuend, subtrahend, false);
        returnCustomNumber.cropRelevant(relevant);
        return returnCustomNumber;
    }

    public static CustomNumber subTr(CustomNumber minuend, CustomNumber subtrahend) {
        return new CustomNumber(subTr(minuend, subtrahend, -1));
    }

    public static CustomNumber multiply(CustomNumber factor1, CustomNumber factor2, int relevant) {
        CustomNumber returnCustomNumber = new CustomNumber();

        if (factor1.isZero() || factor2.isZero()) {
            returnCustomNumber.setZero();
        } else if (factor1.getDataLength() == 1) {
            switch (factor1.getDigitArray().get(0)) {
                case (byte) 1 -> {
                    returnCustomNumber.set(factor2);
                    if (factor1.getLBC() > 1) {
                        returnCustomNumber.shiftLeft(factor1.getLBC() - 1);
                    } else if (factor1.getLBC() < 1) {
                        returnCustomNumber.shiftRight((factor1.getLBC() * -1) + 1);
                    }
                }
                case (byte) 2 -> {
                    returnCustomNumber = addUp(factor2, factor2, relevant);
                    if (factor1.getLBC() > 1) {
                        returnCustomNumber.shiftLeft(factor1.getLBC() - 1);
                    } else if (factor1.getLBC() < 1) {
                        returnCustomNumber.shiftRight((factor1.getLBC() * -1) + 1);
                    }
                }
                default -> returnCustomNumber = internalMultiplication(factor2, factor1);
            }
        } else if (factor2.getDataLength() == 1) {
            switch (factor2.getDigitArray().get(0)) {
                case (byte) 1 -> {
                    returnCustomNumber.set(factor1);
                    if (factor2.getLBC() > 1) {
                        returnCustomNumber.shiftLeft(factor2.getLBC() - 1);
                    } else if (factor2.getLBC() < 1) {
                        returnCustomNumber.shiftRight((factor2.getLBC() * -1) + 1);
                    }
                }
                case (byte) 2 -> {
                    returnCustomNumber = addUp(factor1, factor1, relevant);
                    if (factor2.getLBC() > 1) {
                        returnCustomNumber.shiftLeft(factor2.getLBC() - 1);
                    } else if (factor2.getLBC() < 1) {
                        returnCustomNumber.shiftRight((factor2.getLBC() * -1) + 1);
                    }
                }
                default -> returnCustomNumber = internalMultiplication(factor1, factor2);
            }
        } else {
            if (factor2.getDataLength() > factor1.getDataLength()) {
                returnCustomNumber = internalMultiplication(factor2, factor1);
            } else {
                returnCustomNumber = internalMultiplication(factor1, factor2);
            }
        }

        if (factor1.isPos() != factor2.isPos()) {
            returnCustomNumber.setNeg();
        } else {
            returnCustomNumber.setPos();
        }
        returnCustomNumber.cropRelevant(relevant);
        return returnCustomNumber;
    }

    public static CustomNumber multiply(CustomNumber factor1, CustomNumber factor2) {
        return new CustomNumber(multiply(factor1, factor2, -1));
    }

    public static CustomNumber divide(CustomNumber dividend, CustomNumber divisor, int relevant) {
        CustomNumber retCustomNumber = new CustomNumber();
        boolean dividendPos = dividend.isPos();
        boolean divisorPos = divisor.isPos();

        dividend.setPos();
        divisor.setPos();
        if (divisor.isZero()) {
            dividend.setSign(dividendPos);
            divisor.setSign(divisorPos);
            retCustomNumber.setSign(divisorPos == dividendPos);
            dividend.print();
            divisor.print();
            throw new RuntimeException("STOP YOU VIOLATED THE LAW ! (div by zero)");
        } else if (dividend.isZero()) {
            dividend.setSign(dividendPos);
            divisor.setSign(divisorPos);
            retCustomNumber.setZero();
        } else if (areEqual(dividend, divisor)) {
            dividend.setSign(dividendPos);
            divisor.setSign(divisorPos);
            retCustomNumber.set("+1.");
            retCustomNumber.setSign(divisorPos == dividendPos);
        } else if (isSmaller(dividend, divisor)) {
            CustomNumber workDividend = new CustomNumber();
            CustomNumber workDivisor = new CustomNumber();
            workDividend.set(dividend);
            workDivisor.set(divisor);
            int maxAfter = Math.max(dividend.getLAC(), divisor.getLAC());
            workDividend.shiftLeft(maxAfter);
            workDivisor.shiftLeft(maxAfter);
            workDividend.setPhantomZeros();
            workDivisor.setPhantomZeros();

            while (isGreater(workDividend, workDivisor)) {
                workDividend.appendDigitRight((byte) 0);
            }

            internalDivisionAfterComma(workDividend, workDivisor, retCustomNumber);
            retCustomNumber.shiftLeft();
            retCustomNumber.clean();
            retCustomNumber.setSign(divisorPos == dividendPos);
            dividend.setSign(dividendPos);
            divisor.setSign(divisorPos);
        } else {
            CustomNumber workDividend = new CustomNumber();
            CustomNumber workDivisor = new CustomNumber();
            workDividend.set(dividend);
            workDivisor.set(divisor);
            int maxAfter = Math.max(dividend.getLAC(), divisor.getLAC());
            workDividend.shiftLeft(maxAfter);
            workDivisor.shiftLeft(maxAfter);
            workDividend.setPhantomZeros();
            workDivisor.setPhantomZeros();
            CustomNumber tempDividend = new CustomNumber();
            tempDividend.set(workDividend);

            int newDiff = dividend.getLBC() - divisor.getLBC();
            for (int i = 0; i < newDiff; i++) {
                tempDividend.removeDigitRight();
            }
            tempDividend.shiftRight(newDiff);

            byte addVal = 0;
            for (int i = 0; i < newDiff; i++) {
                while (isGreaterOrEqual(tempDividend, workDivisor)) {
                    tempDividend = subTr(tempDividend, workDivisor, -1);
                    addVal++;
                }
                retCustomNumber.appendDigitRight(addVal);
                addVal = 0;
                tempDividend.appendDigitRight(workDividend.getDigitArray().get(workDividend.getDataLength() - (newDiff - i)));
                tempDividend.shiftLeft();
            }
            internalDivisionAfterComma(tempDividend, workDivisor, retCustomNumber);
            retCustomNumber.shiftLeft(newDiff + 1);
            retCustomNumber.clean();
            retCustomNumber.setSign(divisorPos == dividendPos);
            dividend.setSign(dividendPos);
            divisor.setSign(divisorPos);
        }
        retCustomNumber.cropRelevant(relevant);
        return retCustomNumber;
    }

    public static CustomNumber divide(CustomNumber dividend, CustomNumber divisor) {
        return new CustomNumber(divide(dividend, divisor, -1));
    }

    public static CustomNumber squareRoot(CustomNumber number, int relevant) {
        CustomNumber workNumber1 = new CustomNumber();
        CustomNumber workNumber2 = new CustomNumber(number);
        CustomNumber temp = new CustomNumber();

        for (int i = 0; i < 10; i++) {
            workNumber1 = CNUtils.divide(CNUtils.addUp(workNumber1, workNumber2, -1), new CustomNumber("+2."), relevant);
            workNumber2 = CNUtils.divide(number, workNumber1, -1);

            if (workNumber1.getDataLength() > relevant) {
                workNumber1.removeNDigitsRight(workNumber1.getDataLength()-relevant);
            }
            if (workNumber2.getDataLength() > relevant) {
                workNumber2.removeNDigitsRight(workNumber2.getDataLength()-relevant);
            }

            if (areEqual(temp, workNumber2)) {
                break;
            }

            temp.set(workNumber2);
        }

        workNumber2.cropRelevant(relevant);
        return workNumber2;
    }

    public static CustomNumber squareRoot(CustomNumber number) {
        return new CustomNumber(squareRoot(number, -1));
    }

    public static CustomNumber exp(CustomNumber number, int exp, int relevant) {
        CustomNumber retNumber = new CustomNumber();

        if (exp == 0) {
            retNumber.set(new CustomNumber("+1."));
        } else if (exp > 0) {
            if (exp == 1) {
                retNumber.set(number);
            } else {
                retNumber.set(number);
                for (int i = 0; i < exp - 1; i++) {
                    retNumber = CNUtils.multiply(number, retNumber, relevant);
                }
            }
        } else {
            retNumber.set(number);
            if (exp != -1) {
                exp *= -1;
                for (int i = 0; i < exp - 1; i++) {
                    retNumber = CNUtils.multiply(number, retNumber, relevant);
                }
            }
            retNumber = CNUtils.divide(new CustomNumber("+1."), retNumber, relevant);
        }

        if (number.isNeg() && exp % 2 != 0) {
            retNumber.setNeg();
        }

        retNumber.cropRelevant(relevant);

        return retNumber;
    }

    public static CustomNumber exp(CustomNumber number, int exp) {
        return new CustomNumber(exp(number, exp, -1));
    }

    public static boolean areEqual(CustomNumber number1, CustomNumber number2) {
        if (!((number1.isPos() == number2.isPos()) && (number1.getLBC() == number2.getLBC()) && (number1.getLAC() == number2.getLAC()) && (number1.getDataLength() == number2.getDataLength()))) {
            return false;
        }
        return number1.getDigitArray().equals(number2.getDigitArray());
    }

    public static boolean isGreater(CustomNumber number1, CustomNumber number2) {
        boolean retVal = !number1.isZero() || !number2.isZero();
        if (number1.isZero() && !number2.isZero()) {
            retVal = !number2.isPos();
        }
        if (!number1.isZero() && number2.isZero()) {
            retVal = number1.isPos();
        }
        if (!number1.isZero() && !number2.isZero()) {
            if (number1.isPos() && number2.isPos()) {
                if (number1.getLBC() < number2.getLBC()) {
                    retVal = false;
                }
                if (number1.getLBC() == number2.getLBC()) {
                    if (areEqual(number1, number2)) {
                        retVal = false;
                    }

                    number1.evenOut(number2);
                    number2.evenOut(number1);

                    for (int i = 0; i < number1.getDigitArray().size(); i++) {
                        if (number1.getDigitArray().get(i) > number2.getDigitArray().get(i)) {
                            retVal = true;
                            break;
                        }
                        if (number1.getDigitArray().get(i) < number2.getDigitArray().get(i)) {
                            retVal = false;
                            break;
                        }
                    }
                }
            }
            if (number1.isPos() && number2.isNeg()) {
                retVal = true;
            }
            if (number1.isNeg() && number2.isPos()) {
                retVal = false;
            }
            if (number1.isNeg() && number2.isNeg()) {
                if (number1.getLBC() > number2.getLBC()) {
                    retVal = false;
                }

                if (number1.getLBC() == number2.getLBC()) {
                    if (areEqual(number1, number2)) {
                        retVal = false;
                    }

                    number1.evenOut(number2);
                    number2.evenOut(number1);

                    for (int i = 0; i < number1.getDigitArray().size(); i++) {
                        if (number1.getDigitArray().get(i) > number2.getDigitArray().get(i)) {
                            retVal = false;
                            break;
                        }
                        if (number1.getDigitArray().get(i) < number2.getDigitArray().get(i)) {
                            retVal = true;
                            break;
                        }
                    }
                }
            }
        }
        number1.clean();
        number2.clean();
        return retVal;
    }

    public static boolean isGreaterOrEqual(CustomNumber number1, CustomNumber number2) {
        if (areEqual(number1, number2)) {
            return true;
        }
        return isGreater(number1, number2);
    }

    public static boolean isSmaller(CustomNumber number1, CustomNumber number2) {
        return !isGreaterOrEqual(number1, number2);
    }

    public static boolean isSmallerOrEqual(CustomNumber number1, CustomNumber number2) {
        return (areEqual(number1, number2) || isGreater(number2, number1));
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

    private static CustomNumber addUpSubstractionLogic(CustomNumber number1, CustomNumber number2, boolean mode) {
        CustomNumber returnCustomNumber = new CustomNumber();
        boolean number1Positive = number1.isPos();
        boolean number1Negative = number1.isNeg();
        boolean number2Positive = number2.isPos();
        boolean number2Negative = number2.isNeg();
        number1.setPos();
        number2.setPos();

        if (areEqual(number1, number2)) {
            if (number1Positive && mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase1(number1, number2);
            } else if (number1Positive && mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase0();
            } else if (number1Positive && !mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase0();
            } else if (number1Positive && !mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase1(number1, number2);
            } else if (number1Negative && mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase0();
            } else if (number1Negative && mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase2(number1, number2);
            } else if (number1Negative && !mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase2(number1, number2);
            } else if (number1Negative && !mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase0();
            }
        } else if (isGreater(number1, number2)) {
            if (number1Positive && mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase1(number1, number2);
            } else if (number1Positive && mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase3(number1, number2);
            } else if (number1Positive && !mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase3(number1, number2);
            } else if (number1Positive && !mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase1(number1, number2);
            } else if (number1Negative && mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase4(number1, number2);
            } else if (number1Negative && mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase2(number1, number2);
            } else if (number1Negative && !mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase2(number1, number2);
            } else if (number1Negative && !mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase4(number1, number2);
            }
        } else if (isSmaller(number1, number2)) {
            if (number1Positive && mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase1(number1, number2);
            } else if (number1Positive && mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase6(number1, number2);
            } else if (number1Positive && !mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase6(number1, number2);
            } else if (number1Positive && !mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase1(number1, number2);
            } else if (number1Negative && mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase5(number1, number2);
            } else if (number1Negative && mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase2(number1, number2);
            } else if (number1Negative && !mode && number2Positive) {
                returnCustomNumber = addUpSubtractionLogicCase2(number1, number2);
            } else if (number1Negative && !mode && number2Negative) {
                returnCustomNumber = addUpSubtractionLogicCase5(number1, number2);
            }
        }
        number1.setSign(number1Positive);
        number2.setSign(number2Positive);

        return returnCustomNumber;
    }

    private static CustomNumber addUpSubtractionLogicCase0() {
        CustomNumber returnCustomNumber = new CustomNumber();
        returnCustomNumber.setZero();
        return returnCustomNumber;
    }

    private static CustomNumber addUpSubtractionLogicCase1(CustomNumber number1, CustomNumber number2) {
        return internalAddition(number1, number2);
    }

    private static CustomNumber addUpSubtractionLogicCase2(CustomNumber number1, CustomNumber number2) {
        CustomNumber returnCustomNumber = internalAddition(number1, number2);
        returnCustomNumber.setNeg();
        return returnCustomNumber;
    }

    private static CustomNumber addUpSubtractionLogicCase3(CustomNumber number1, CustomNumber number2) {
        return internalSubtraction(number1, number2);
    }

    private static CustomNumber addUpSubtractionLogicCase4(CustomNumber number1, CustomNumber number2) {
        CustomNumber returnCustomNumber = internalSubtraction(number1, number2);
        returnCustomNumber.setNeg();
        return returnCustomNumber;
    }

    private static CustomNumber addUpSubtractionLogicCase5(CustomNumber number1, CustomNumber number2) {
        return internalSubtraction(number2, number1);
    }

    private static CustomNumber addUpSubtractionLogicCase6(CustomNumber number1, CustomNumber number2) {
        CustomNumber returnCustomNumber = internalSubtraction(number2, number1);
        returnCustomNumber.setNeg();
        return returnCustomNumber;
    }

    /* Minuend and Subtrahend must be positive
       Minuend must be bigger than the Subtrahend
     */
    private static CustomNumber internalSubtraction(CustomNumber minuend, CustomNumber subtrahend) {
        CustomNumber returnCustomNumber = new CustomNumber();
        byte carry = 0;

        if (!subtrahend.isZero()) {
            minuend.evenOut(subtrahend);
            subtrahend.evenOut(minuend);
            byte tempDigitResult = 0;
            for (int i = minuend.getDigitArray().size() - 1; i >= 0; i--) {
                if ((subtrahend.getDigitArray().get(i) + carry) <= (minuend.getDigitArray().get(i))) {
                    tempDigitResult = (byte) (minuend.getDigitArray().get(i) - (subtrahend.getDigitArray().get(i) + carry));
                    carry = 0;
                }
                if ((subtrahend.getDigitArray().get(i) + carry) > (minuend.getDigitArray().get(i))) {
                    tempDigitResult = (byte) ((minuend.getDigitArray().get(i) + (byte) 10) - (subtrahend.getDigitArray().get(i) + carry));
                    carry = 1;
                }

                returnCustomNumber.appendDigitLeft(tempDigitResult);
            }

            if (minuend.getLAC() > 0) {
                returnCustomNumber.shiftRight(minuend.getLAC());
            }
            if (minuend.getLAC() < 0) {
                returnCustomNumber.shiftLeft(minuend.getLAC() * -1);
            }

            minuend.clean();
            subtrahend.clean();
            returnCustomNumber.clean();
        } else {
            returnCustomNumber.set(minuend);
        }

        return returnCustomNumber;
    }

    /* Adds up the absolute values of two Summa-nds
        Sign of the returnNumber always positive
     */
    private static CustomNumber internalAddition(CustomNumber summand1, CustomNumber summand2) {
        CustomNumber returnCustomNumber = new CustomNumber();

        if (!summand1.isZero() && !summand2.isZero()) {
            byte carry = 0;
            if (!(summand1.getDataLength() == summand2.getDataLength() && summand1.getLBC() == summand2.getLBC() && summand1.getLAC() == summand2.getLAC())) {
                summand1.evenOut(summand2);
                summand2.evenOut(summand1);
            }

            returnCustomNumber.setLBC(summand1.getLBC());
            returnCustomNumber.setLAC(summand1.getLAC());

            for (int i = summand1.getDataLength() - 1; i >= 0 || carry != 0; i--) {
                byte tempDigitResult = 0;
                tempDigitResult = (byte) (summand1.getDigitArray().get(i) + summand2.getDigitArray().get(i) + carry);
                if (tempDigitResult >= 10) {
                    tempDigitResult = (byte) (tempDigitResult - 10);
                    carry = 1;
                } else {
                    carry = 0;
                }

                returnCustomNumber.getDigitArray().add(0, tempDigitResult);
                if (i == 0 && carry != 0) {
                    returnCustomNumber.getDigitArray().add(0, carry);
                    returnCustomNumber.incLBC();
                    carry = 0;
                }
            }
        } else {
            if (summand2.isZero() && !summand1.isZero()) {
                returnCustomNumber.set(summand1);
            }
            if (summand1.isZero() && !summand2.isZero()) {
                returnCustomNumber.set(summand2);
            }
            if (summand1.isZero() && summand2.isZero()) {
                returnCustomNumber.setZero();
            }
        }
        summand1.clean();
        summand2.clean();
        returnCustomNumber.clean();
        return returnCustomNumber;
    }

    /* multiplies 2 Numbers; always returns a positive result */
    private static CustomNumber internalMultiplication(CustomNumber factor1, CustomNumber factor2) {
        CustomNumber returnCustomNumber = new CustomNumber();
        CustomNumber tempFactor1 = new CustomNumber();
        CustomNumber tempFactor2 = new CustomNumber();
        CustomNumber lineResult = new CustomNumber();

        int digitsCountToShiftRight = 0;
        returnCustomNumber.setZero();

        if (factor1.getLAC() > 0) {
            digitsCountToShiftRight = digitsCountToShiftRight + factor1.getLAC();
        }

        if (factor2.getLAC() > 0) {
            digitsCountToShiftRight = digitsCountToShiftRight + factor2.getLAC();
        }

        tempFactor1.set(factor1);
        tempFactor2.set(factor2);



        if (tempFactor1.getLAC() > 0) {
            tempFactor1.setLBC(tempFactor1.getLBC() + tempFactor1.getLAC());
            tempFactor1.setLAC(0);
        }
        if (tempFactor2.getLAC() > 0) {
            tempFactor2.setLBC(tempFactor2.getLBC() + tempFactor2.getLAC());
            tempFactor2.setLAC(0);
        }

        for (int i = 0; i < tempFactor2.getDataLength(); i++) {
            switch (tempFactor2.getDigitArray().get(i)) {
                case 0 -> lineResult.setZero();
                case 1 -> lineResult.set(tempFactor1);
                case 2 -> lineResult = internalAddition(tempFactor1, tempFactor1);
                default -> {
                    lineResult = oneDigitCustomNumberMultiplication(tempFactor1, tempFactor2.getDigitArray().get(i));
                    lineResult.clean();
                }
            }

            if(!lineResult.isZero()) //FIXME: this if should not be needed, rework multiplication
            {
                lineResult.shiftLeft(tempFactor2.getLBC() - i - 1);
            }
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

    private static CustomNumber oneDigitCustomNumberMultiplication(CustomNumber factor, byte digitFactor) {
        CustomNumber retCustomNumber = new CustomNumber();
        if (factor.isZero() || digitFactor == 0) {
            retCustomNumber.setZero();
        } else {

            while (factor.getDigitArray().size() < factor.getLBC()) {
                factor.getDigitArray().add(factor.getDigitArray().size(), (byte) 0);
                factor.setLAC(factor.getLAC() + 1);
            }

            byte carry = 0;
            for (int i = factor.getDataLength() - 1; i >= 0 || carry != 0; i--) {
                byte tempDigitResult;
                byte digitResult;
                tempDigitResult = (byte) (factor.getDigitArray().get(i) * digitFactor + carry);
                digitResult = (byte) ((tempDigitResult) % 10);
                carry = (byte) (tempDigitResult / 10);

                retCustomNumber.appendDigitLeft(digitResult);
                if (i == 0 && carry != 0) {
                    retCustomNumber.getDigitArray().add(0, carry);
                    retCustomNumber.incLBC();
                    carry = 0;
                }
            }
        }
        return retCustomNumber;
    }

    private static void internalDivisionAfterComma(CustomNumber dividend, CustomNumber divisor, CustomNumber retCustomNumber) {
        byte addVal = 0;
        int count = 0;
        while (count < dividend.getDataLength() + divisor.getDataLength() + 5) {
            while (isGreaterOrEqual(dividend, divisor)) {
                dividend = subTr(dividend, divisor, -1);
                addVal++;
            }
            retCustomNumber.appendDigitRight(addVal);
            if (dividend.isZero()) {
                break;
            }
            dividend.appendDigitRight((byte) 0);
            dividend.shiftLeft();
            addVal = 0;
            count++;
        }
    }

    public static void test(int iterations) {
        CustomNumber ctValA = new CustomNumber();
        CustomNumber ctValB = new CustomNumber();
        double dbValA;
        double dbValB;

        for (double i = iterations * -1; i <= iterations; i += 1) {
            for (double k = iterations * -1; k <= iterations; k += 1) {
                for (double j = iterations * -1; j <= iterations; j += 1) {
                    dbValA = j + i;
                    dbValB = k - i;
                    ctValA.set(String.valueOf(dbValA));
                    ctValB.set(String.valueOf(dbValB));
                    if (!testCase(ctValA, ctValB, dbValA, dbValB)) {
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

    private static boolean testCase(CustomNumber ctValA, CustomNumber ctValB, double dbValA, double dbValB) {
        CustomNumber ctValRes = new CustomNumber();
        CustomNumber ctDbTempRes = new CustomNumber();
        double dbValRes;

        ctValRes = addUp(ctValA, ctValB, -1);
        dbValRes = dbValA + dbValB;

        ctDbTempRes.set(String.valueOf(dbValRes));
        if (!ctValRes.toString().equals(ctDbTempRes.toString())) {
            testCaseErrorMessage(ctValRes, ctDbTempRes, dbValRes, ctValA, ctValB, dbValA, dbValB);
            return false;
        }

        ctValRes = subTr(ctValA, ctValB, -1);
        dbValRes = dbValA - dbValB;

        ctDbTempRes.set(String.valueOf(dbValRes));
        if (!ctValRes.toString().equals(ctDbTempRes.toString())) {
            testCaseErrorMessage(ctValRes, ctDbTempRes, dbValRes, ctValA, ctValB, dbValA, dbValB);
            return false;
        }

        ctValRes = multiply(ctValA, ctValB, -1);
        dbValRes = dbValA * dbValB;

        ctDbTempRes.set(String.valueOf(dbValRes));
        if (!ctValRes.toString().equals(ctDbTempRes.toString())) {
            testCaseErrorMessage(ctValRes, ctDbTempRes, dbValRes, ctValA, ctValB, dbValA, dbValB);
            return false;
        }

        CustomNumber tmpCtValA = new CustomNumber();
        CustomNumber tmpCtValB = new CustomNumber();

        double tmpDbValA = dbValA * 10;
        double tmpDbValB = dbValB * 10;

        tmpCtValA.set(ctValA);
        tmpCtValB.set(ctValB);

        tmpCtValA.shiftLeft();
        tmpCtValB.shiftLeft();

        ctValRes = multiply(tmpCtValA, tmpCtValB, -1);
        dbValRes = tmpDbValA * tmpDbValB;

        ctDbTempRes.set(String.valueOf(dbValRes));
        if (!ctValRes.toString().equals(ctDbTempRes.toString())) {
            testCaseErrorMessage(ctValRes, ctDbTempRes, dbValRes, tmpCtValA, tmpCtValB, tmpDbValA, tmpDbValB);
            return false;
        }
        return true;
    }


    private static void testCaseErrorMessage(CustomNumber ctValRes, CustomNumber ctDbTempRes, double dbValRes, CustomNumber ctValA, CustomNumber ctValB, double dbValA, double dbValB) {
        System.out.println("========================= ERROR =========================");
        System.out.println("Compared Strings:");
        System.out.println("ct:    " + ctValRes.toString());
        System.out.println("ctTmp: " + ctDbTempRes.toString());
        System.out.println("db:    " + String.valueOf(dbValRes));
        System.out.println("Custom Number Calculated Value: ");
        ctValRes.print();
        System.out.println("Double Calculated Value: ");
        ctDbTempRes.print();
        System.out.println("Custom Number Input: ");
        ctValA.print();
        ctValB.print();
        System.out.println("Double Input:");
        System.out.println(dbValA);
        System.out.println(dbValB);
        System.out.println("======================= ERROR END =======================");
    }
}