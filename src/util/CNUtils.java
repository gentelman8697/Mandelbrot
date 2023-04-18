package util;

import datatypes.*;

public class CNUtils {
    public static CustomNumber addUp(CustomNumber summand1, CustomNumber summand2, int relevant) {
        CustomNumber returnCustomNumber = new CustomNumber();
        returnCustomNumber = addUpSubtractionLogic(summand1, summand2, true);
        returnCustomNumber.cropRelevant(relevant);
        return returnCustomNumber;
    }

    public static CustomNumber addUp(CustomNumber summand1, CustomNumber summand2) {
        return new CustomNumber(addUp(summand1, summand2, -1));
    }

    public static boolean areEqual(CustomNumber number1, CustomNumber number2) {
        if (!((number1.isPos() == number2.isPos()) && (number1.getLBC() == number2.getLBC()) && (number1.getLAC() == number2.getLAC()) && (number1.getDataLength() == number2.getDataLength()))) {
            return false;
        }
        return number1.getDigitArray().equals(number2.getDigitArray());
    }

    /**
     * Divides the given dividend by the given divisor and returns the result as a CustomNumber.
     *
     * Preconditions:
     * - dividend and divisor must be non-null CustomNumber instances.
     * - divisor must not be zero.
     *
     * Postconditions:
     * - The returned CustomNumber represents the result of the division operation.
     * - The dividend and divisor CustomNumbers are not changed during the operation.
     *
     * @param dividend The dividend CustomNumber.
     * @param divisor The divisor CustomNumber.
     * @param relevant The number of decimal places to keep after division.
     * @return The result of the division as a CustomNumber.
     * @throws RuntimeException If divisor is zero.
     */
    public static CustomNumber divide(CustomNumber dividend, CustomNumber divisor, int relevant) {
        CustomNumber retVal = new CustomNumber();
        boolean dividendSign = dividend.isPos();
        boolean divisorSign = divisor.isPos();

        // Set both dividend and divisor as positive to handle division of signed numbers.
        dividend.setPos();
        divisor.setPos();

        // Check if divisor is zero and throw an exception if it is.
        if (divisor.isZero()) {
            dividend.setSign(dividendSign);
            divisor.setSign(divisorSign);
            retVal.setSign(divisorSign == dividendSign);
            dividend.print();
            divisor.print();
            throw new RuntimeException("STOP YOU VIOLATED THE LAW ! (div by zero)");
        } else if (dividend.isZero()) {
            // If dividend is zero, set the retVal to zero and set its sign accordingly.
            dividend.setSign(dividendSign);
            divisor.setSign(divisorSign);
            retVal.setZero();
        } else if (areEqual(dividend, divisor)) {
            // If dividend and divisor are equal, set retVal to 1 and set its sign accordingly.
            dividend.setSign(dividendSign);
            divisor.setSign(divisorSign);
            retVal.set("+1.");
            retVal.setSign(divisorSign == dividendSign);
        } else if (isSmaller(dividend, divisor)) {
            // If dividend is smaller than divisor, call internalDivisionDividendSmallThanDivisor.
            internalDivisionDividendSmallThanDivisor(dividend, divisor, retVal);
            retVal.setSign(divisorSign == dividendSign);
            dividend.setSign(dividendSign);
            divisor.setSign(divisorSign);
        } else {
            // If divisor is smaller than dividend, call internalDivisionDivisorSmallThanDividend.
            internalDivisionDivisorSmallThanDividend(dividend, divisor, retVal);
            retVal.setSign(divisorSign == dividendSign);
            dividend.setSign(dividendSign);
            divisor.setSign(divisorSign);
        }

        // Crop the result to the specified number of relevant decimal places.
        retVal.cropRelevant(relevant);
        return retVal;
    }

    /**
     * Helper function for the division operation when the dividend is smaller than the divisor.
     *
     * Preconditions:
     * - dividend, divisor, and retVal must be non-null CustomNumber instances.
     *
     * Postconditions:
     * - retVal will contain the result of the division operation with the given dividend and divisor.
     *
     * @param dividend The dividend CustomNumber.
     * @param divisor The divisor CustomNumber.
     * @param retVal The CustomNumber that will store the result of the division operation.
     */
    private static void internalDivisionDividendSmallThanDivisor(CustomNumber dividend, CustomNumber divisor,
                                                                 CustomNumber retVal) {
        CustomNumber workDividend = new CustomNumber();
        CustomNumber workDivisor = new CustomNumber();
        workDividend.set(dividend);
        workDivisor.set(divisor);

        // Shift left both dividend and divisor to align their most significant digits.
        int maxAfter = Math.max(dividend.getLAC(), divisor.getLAC());
        workDividend.shiftLeft(maxAfter);
        workDivisor.shiftLeft(maxAfter);

        // Set phantom zeros for both workDividend and workDivisor.
        workDividend.setPhantomZeros();
        workDivisor.setPhantomZeros();

        // Ensure workDividend is greater or equal to workDivisor.
        while (isGreater(workDividend, workDivisor)) {
            workDividend.appendDigitRight((byte) 0);
        }

        byte addDigit = 0;
        int precisionEscapeCounter = 0;
        // Loop until the desired precision is reached.
        while (precisionEscapeCounter < workDividend.getDataLength() + workDivisor.getDataLength() + 5) {
            // Subtract workDivisor from workDividend as long as it is greater or equal.
            while (isGreaterOrEqual(workDividend, workDivisor)) {
                workDividend = subTr(workDividend, workDivisor, -1);
                addDigit++;
            }
            // Append the digit obtained from the subtraction to retVal.
            retVal.appendDigitRight(addDigit);

            // Break the loop if workDividend becomes zero.
            if (workDividend.isZero()) {
                break;
            }

            // Shift workDividend left and reset addDigit and precisionEscapeCounter.
            workDividend.appendDigitRight((byte) 0);
            workDividend.shiftLeft();
            addDigit = 0;
            precisionEscapeCounter++;
        }

        // Shift retVal left and clean trailing zeros.
        retVal.shiftLeft();
        retVal.clean();
    }

    /**
     * Helper function for the division operation when the divisor is smaller than the dividend.
     *
     * Preconditions:
     * - dividend, divisor, and retVal must be non-null CustomNumber instances.
     *
     * Postconditions:
     * - retVal will contain the result of the division operation with the given dividend and divisor.
     *
     * @param dividend The dividend CustomNumber.
     * @param divisor The divisor CustomNumber.
     * @param retVal The CustomNumber that will store the result of the division operation.
     */
    private static void internalDivisionDivisorSmallThanDividend(CustomNumber dividend, CustomNumber divisor,
                                                                 CustomNumber retVal) {
        CustomNumber shiftedDividend = new CustomNumber();
        CustomNumber workDivisor = new CustomNumber();
        shiftedDividend.set(dividend);
        workDivisor.set(divisor);

        // Shift left both dividend and divisor to align their most significant digits.
        int maxAfter = Math.max(dividend.getLAC(), divisor.getLAC());
        shiftedDividend.shiftLeft(maxAfter);
        workDivisor.shiftLeft(maxAfter);

        // Set phantom zeros for both shiftedDividend and workDivisor.
        shiftedDividend.setPhantomZeros();
        workDivisor.setPhantomZeros();

        CustomNumber workDividend = new CustomNumber();
        workDividend.set(shiftedDividend);

        // Calculate the difference between dividend's and divisor's least significant digits.
        int diffLBC = dividend.getLBC() - divisor.getLBC();

        // Remove the least significant digits from workDividend.
        for (int i = 0; i < diffLBC; i++) {
            workDividend.removeDigitRight();
        }
        workDividend.shiftRight(diffLBC);

        // Process the digits before the comma.
        byte addDigit = 0;
        for (int i = 0; i < diffLBC; i++) {
            // Subtract workDivisor from workDividend as long as it is greater or equal.
            while (isGreaterOrEqual(workDividend, workDivisor)) {
                workDividend = subTr(workDividend, workDivisor, -1);
                addDigit++;
            }
            // Append the digit obtained from the subtraction to retVal.
            retVal.appendDigitRight(addDigit);
            addDigit = 0;

            // Fill the gap with zeros and append the next digit from shiftedDividend.
            while (workDividend.getLAC() < 0) {
                workDividend.appendDigitRight((byte) 0);
            }
            workDividend.appendDigitRight(shiftedDividend.getDigitArray().get(shiftedDividend.getDataLength() - (diffLBC - i)));
            workDividend.shiftLeft();
        }

        // Process the digits after the comma.
        int precisionEscapeCounter = 0;
        while (precisionEscapeCounter < workDividend.getDataLength() + workDivisor.getDataLength() + 5) {
            // Subtract workDivisor from workDividend as long as it is greater or equal.
            while (isGreaterOrEqual(workDividend, workDivisor)) {
                workDividend = subTr(workDividend, workDivisor, -1);
                addDigit++;
            }
            // Append the digit obtained from the subtraction to retVal.
            retVal.appendDigitRight(addDigit);

            // Break the loop if workDividend becomes zero.
            if (workDividend.isZero()) {
                break;
            }

            // Shift workDividend left and reset addDigit and precisionEscapeCounter.
            workDividend.appendDigitRight((byte) 0);
            workDividend.shiftLeft();
            addDigit = 0;
            precisionEscapeCounter++;
        }

        // Shift retVal left by (diffLBC + 1) positions and remove any trailing zeros.
        retVal.shiftLeft(diffLBC + 1);
        retVal.clean();
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

    public static CustomNumber divide(CustomNumber dividend, CustomNumber divisor) {
        return new CustomNumber(divide(dividend, divisor, -1));
    }

    /**
     * Multiplies two CustomNumber objects and returns the product.
     *
     * Preconditions:
     * - factor1 and factor2 must be non-null CustomNumber objects.
     * - relevant must be a non-negative integer.
     *
     * Postconditions:
     * - The returned CustomNumber object will be the product of factor1 and factor2.
     * - The returned CustomNumber object will have the sign set correctly based on the signs of factor1 and factor2.
     * - The returned CustomNumber object will have the specified number of significant digits (relevant).
     * - Neither factor1 nor factor2 will be modified during the calculation.
     *
     * @param factor1 The first factor for the multiplication.
     * @param factor2 The second factor for the multiplication.
     * @param relevant The number of significant digits to be kept in the result.
     * @return The product of factor1 and factor2 as a CustomNumber object.
     */
    public static CustomNumber multiply(CustomNumber factor1, CustomNumber factor2, int relevant) {
        CustomNumber retVal = new CustomNumber();

        // Handle cases where either factor is zero.
        if (factor1.isZero() || factor2.isZero()) {
            retVal.setZero();
        } else if (factor1.getDataLength() == 1) {
            // Handle cases where the first factor has a single digit.
            switch (factor1.getDigitArray().get(0)) {
                case (byte) 1 -> {
                    retVal.set(factor2);
                    if (factor1.getLBC() > 1) {
                        retVal.shiftLeft(factor1.getLBC() - 1);
                    } else if (factor1.getLBC() < 1) {
                        retVal.shiftRight((factor1.getLBC() * -1) + 1);
                    }
                }
                case (byte) 2 -> {
                    retVal = addUp(factor2, factor2, relevant);
                    if (factor1.getLBC() > 1) {
                        retVal.shiftLeft(factor1.getLBC() - 1);
                    } else if (factor1.getLBC() < 1) {
                        retVal.shiftRight((factor1.getLBC() * -1) + 1);
                    }
                }
                default -> retVal = internalMultiplication(factor2, factor1);
            }
        } else if (factor2.getDataLength() == 1) {
            // Handle cases where the second factor has a single digit.
            switch (factor2.getDigitArray().get(0)) {
                case (byte) 1 -> {
                    retVal.set(factor1);
                    if (factor2.getLBC() > 1) {
                        retVal.shiftLeft(factor2.getLBC() - 1);
                    } else if (factor2.getLBC() < 1) {
                        retVal.shiftRight((factor2.getLBC() * -1) + 1);
                    }
                }
                case (byte) 2 -> {
                    retVal = addUp(factor1, factor1, relevant);
                    if (factor2.getLBC() > 1) {
                        retVal.shiftLeft(factor2.getLBC() - 1);
                    } else if (factor2.getLBC() < 1) {
                        retVal.shiftRight((factor2.getLBC() * -1) + 1);
                    }
                }
                default -> retVal = internalMultiplication(factor1, factor2);
            }
        } else {
            // Handle cases where both factors have multiple digits.
            if (factor2.getDataLength() > factor1.getDataLength()) {
                retVal = internalMultiplication(factor2, factor1);
            } else {
                retVal = internalMultiplication(factor1, factor2);
            }
        }

        // Set the sign of the result.
        if (factor1.isPos() != factor2.isPos()) {
            retVal.setNeg();
        } else {
            retVal.setPos();
        }
        retVal.cropRelevant(relevant);
        return retVal;
    }

    public static CustomNumber multiply(CustomNumber factor1, CustomNumber factor2) {
        return new CustomNumber(multiply(factor1, factor2, -1));
    }

    public static CustomNumber squareRoot(CustomNumber number) {
        return new CustomNumber(squareRoot(number, -1));
    }

    public static boolean isGreater(CustomNumber number1, CustomNumber number2) {
        boolean retVal = !number1.isZero() || !number2.isZero();
        if (number1.isZero() && !number2.isZero()) {
            retVal = number2.isNeg();
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

    public static CustomNumber squareRoot(CustomNumber number, int relevant) {
        CustomNumber workNumber1 = new CustomNumber();
        CustomNumber workNumber2 = new CustomNumber(number);
        CustomNumber temp = new CustomNumber();

        for (int i = 0; i < 10; i++) {
            workNumber1 = CNUtils.divide(CNUtils.addUp(workNumber1, workNumber2, -1), new CustomNumber("+2."), relevant);
            workNumber2 = CNUtils.divide(number, workNumber1, -1);

            if (workNumber1.getDataLength() > relevant) {
                workNumber1.removeNDigitsRight(workNumber1.getDataLength() - relevant);
            }
            if (workNumber2.getDataLength() > relevant) {
                workNumber2.removeNDigitsRight(workNumber2.getDataLength() - relevant);
            }

            if (areEqual(temp, workNumber2)) {
                break;
            }

            temp.set(workNumber2);
        }

        workNumber2.cropRelevant(relevant);
        return workNumber2;
    }

    public static CustomNumber subTr(CustomNumber minuend, CustomNumber subtrahend, int relevant) {
        CustomNumber retVal = new CustomNumber();
        retVal = addUpSubtractionLogic(minuend, subtrahend, false);
        retVal.cropRelevant(relevant);
        return retVal;
    }

    public static CustomNumber subTr(CustomNumber minuend, CustomNumber subtrahend) {
        return new CustomNumber(subTr(minuend, subtrahend, -1));
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

    private static CustomNumber addUpSubtractionLogic(CustomNumber number1, CustomNumber number2, boolean mode) {
        CustomNumber retVal = new CustomNumber();
        boolean number1Positive = number1.isPos();
        boolean number1Negative = number1.isNeg();
        boolean number2Positive = number2.isPos();
        boolean number2Negative = number2.isNeg();
        number1.setPos();
        number2.setPos();

        if (areEqual(number1, number2)) {
            if (number1Positive && mode && number2Positive) {
                retVal = addUpSubtractionLogicCase1(number1, number2);
            } else if (number1Positive && mode && number2Negative) {
                retVal = addUpSubtractionLogicCase0();
            } else if (number1Positive && !mode && number2Positive) {
                retVal = addUpSubtractionLogicCase0();
            } else if (number1Positive && !mode && number2Negative) {
                retVal = addUpSubtractionLogicCase1(number1, number2);
            } else if (number1Negative && mode && number2Positive) {
                retVal = addUpSubtractionLogicCase0();
            } else if (number1Negative && mode && number2Negative) {
                retVal = addUpSubtractionLogicCase2(number1, number2);
            } else if (number1Negative && !mode && number2Positive) {
                retVal = addUpSubtractionLogicCase2(number1, number2);
            } else if (number1Negative && !mode && number2Negative) {
                retVal = addUpSubtractionLogicCase0();
            }
        } else if (isGreater(number1, number2)) {
            if (number1Positive && mode && number2Positive) {
                retVal = addUpSubtractionLogicCase1(number1, number2);
            } else if (number1Positive && mode && number2Negative) {
                retVal = addUpSubtractionLogicCase3(number1, number2);
            } else if (number1Positive && !mode && number2Positive) {
                retVal = addUpSubtractionLogicCase3(number1, number2);
            } else if (number1Positive && !mode && number2Negative) {
                retVal = addUpSubtractionLogicCase1(number1, number2);
            } else if (number1Negative && mode && number2Positive) {
                retVal = addUpSubtractionLogicCase4(number1, number2);
            } else if (number1Negative && mode && number2Negative) {
                retVal = addUpSubtractionLogicCase2(number1, number2);
            } else if (number1Negative && !mode && number2Positive) {
                retVal = addUpSubtractionLogicCase2(number1, number2);
            } else if (number1Negative && !mode && number2Negative) {
                retVal = addUpSubtractionLogicCase4(number1, number2);
            }
        } else if (isSmaller(number1, number2)) {
            if (number1Positive && mode && number2Positive) {
                retVal = addUpSubtractionLogicCase1(number1, number2);
            } else if (number1Positive && mode && number2Negative) {
                retVal = addUpSubtractionLogicCase6(number1, number2);
            } else if (number1Positive && !mode && number2Positive) {
                retVal = addUpSubtractionLogicCase6(number1, number2);
            } else if (number1Positive && !mode && number2Negative) {
                retVal = addUpSubtractionLogicCase1(number1, number2);
            } else if (number1Negative && mode && number2Positive) {
                retVal = addUpSubtractionLogicCase5(number1, number2);
            } else if (number1Negative && mode && number2Negative) {
                retVal = addUpSubtractionLogicCase2(number1, number2);
            } else if (number1Negative && !mode && number2Positive) {
                retVal = addUpSubtractionLogicCase2(number1, number2);
            } else if (number1Negative && !mode && number2Negative) {
                retVal = addUpSubtractionLogicCase5(number1, number2);
            }
        }
        number1.setSign(number1Positive);
        number2.setSign(number2Positive);

        return retVal;
    }

    private static CustomNumber addUpSubtractionLogicCase0() {
        CustomNumber retVal = new CustomNumber();
        retVal.setZero();
        return retVal;
    }

    private static CustomNumber addUpSubtractionLogicCase1(CustomNumber number1, CustomNumber number2) {
        return internalAddition(number1, number2);
    }

    private static CustomNumber addUpSubtractionLogicCase2(CustomNumber number1, CustomNumber number2) {
        CustomNumber retVal = internalAddition(number1, number2);
        retVal.setNeg();
        return retVal;
    }

    private static CustomNumber addUpSubtractionLogicCase3(CustomNumber number1, CustomNumber number2) {
        return internalSubtraction(number1, number2);
    }

    private static CustomNumber addUpSubtractionLogicCase4(CustomNumber number1, CustomNumber number2) {
        CustomNumber retVal = internalSubtraction(number1, number2);
        retVal.setNeg();
        return retVal;
    }

    private static CustomNumber addUpSubtractionLogicCase5(CustomNumber number1, CustomNumber number2) {
        return internalSubtraction(number2, number1);
    }

    private static CustomNumber addUpSubtractionLogicCase6(CustomNumber number1, CustomNumber number2) {
        CustomNumber retVal = internalSubtraction(number2, number1);
        retVal.setNeg();
        return retVal;
    }

    /* Adds up the absolute values of two Summa-nds
    Sign of the returnNumber always positive
 */
    private static CustomNumber internalAddition(CustomNumber summand1, CustomNumber summand2) {
        CustomNumber retVal = new CustomNumber();

        if (!summand1.isZero() && !summand2.isZero()) {
            byte carry = 0;
            if (!(summand1.getDataLength() == summand2.getDataLength() && summand1.getLBC() == summand2.getLBC() && summand1.getLAC() == summand2.getLAC())) {
                summand1.evenOut(summand2);
                summand2.evenOut(summand1);
            }

            retVal.setLBC(summand1.getLBC());
            retVal.setLAC(summand1.getLAC());

            for (int i = summand1.getDataLength() - 1; i >= 0 || carry != 0; i--) {
                byte tempDigitResult = 0;
                tempDigitResult = (byte) (summand1.getDigitArray().get(i) + summand2.getDigitArray().get(i) + carry);
                if (tempDigitResult >= 10) {
                    tempDigitResult = (byte) (tempDigitResult - 10);
                    carry = 1;
                } else {
                    carry = 0;
                }

                retVal.getDigitArray().add(0, tempDigitResult);
                if (i == 0 && carry != 0) {
                    retVal.getDigitArray().add(0, carry);
                    retVal.incLBC();
                    carry = 0;
                }
            }
        } else {
            if (summand2.isZero() && !summand1.isZero()) {
                retVal.set(summand1);
            }
            if (summand1.isZero() && !summand2.isZero()) {
                retVal.set(summand2);
            }
            if (summand1.isZero() && summand2.isZero()) {
                retVal.setZero();
            }
        }
        summand1.clean();
        summand2.clean();
        retVal.clean();
        return retVal;
    }

    /**
     * Multiplies two CustomNumber objects and returns the result as a new CustomNumber object.
     *
     * Preconditions:
     * - factor1 and factor2 are non-null CustomNumber objects.
     *
     * Postconditions:
     * - The returned CustomNumber object represents the product of factor1 and factor2 and is positive.
     * - The original CustomNumber objects (factor1 and factor2) are not modified.
     *
     * @param factor1 The first CustomNumber object to be multiplied.
     * @param factor2 The second CustomNumber object to be multiplied.
     * @return A new CustomNumber object representing the product of factor1 and factor2.
     */
    private static CustomNumber internalMultiplication(CustomNumber factor1, CustomNumber factor2) {
        // Initialize temporary CustomNumber objects for computation
        CustomNumber retVal = new CustomNumber();
        CustomNumber tempFactor1 = new CustomNumber();
        CustomNumber tempFactor2 = new CustomNumber();
        CustomNumber lineResult = new CustomNumber();

        int digitsCountToShiftRight = 0;
        retVal.setZero();

        // Calculate the number of digits to shift right after multiplication
        if (factor1.getLAC() > 0) {
            digitsCountToShiftRight = digitsCountToShiftRight + factor1.getLAC();
        }
        if (factor2.getLAC() > 0) {
            digitsCountToShiftRight = digitsCountToShiftRight + factor2.getLAC();
        }

        // Set the temporary factors to the input factors
        tempFactor1.set(factor1);
        tempFactor2.set(factor2);

        // Adjust the LBC and LAC of temporary factors
        if (tempFactor1.getLAC() > 0) {
            tempFactor1.setLBC(tempFactor1.getLBC() + tempFactor1.getLAC());
            tempFactor1.setLAC(0);
        }
        if (tempFactor2.getLAC() > 0) {
            tempFactor2.setLBC(tempFactor2.getLBC() + tempFactor2.getLAC());
            tempFactor2.setLAC(0);
        }

        // Perform the multiplication
        for (int i = 0; i < tempFactor2.getDataLength(); i++) {
            // Multiply each digit of tempFactor2 by tempFactor1
            switch (tempFactor2.getDigitArray().get(i)) {
                case 0 -> lineResult.setZero();
                case 1 -> lineResult.set(tempFactor1);
                case 2 -> lineResult = internalAddition(tempFactor1, tempFactor1);
                default -> {
                    lineResult = internalOneDigitMultiplication(tempFactor1, tempFactor2.getDigitArray().get(i));
                    lineResult.clean();
                }
            }

            // Shift the result based on the position of the digit
            if (!lineResult.isZero()) //FIXME: this if should not be needed, rework multiplication
            {
                lineResult.shiftLeft(tempFactor2.getLBC() - i - 1);
            }
            retVal = internalAddition(lineResult, retVal);
        }

        // Shift the result to the right by the calculated number of digits
        if (digitsCountToShiftRight > 0) {
            retVal.shiftRight(digitsCountToShiftRight);
        }
        return retVal;
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
    private static CustomNumber internalOneDigitMultiplication(CustomNumber factor, byte digitFactor) {
        CustomNumber retVal = new CustomNumber();
        if (factor.isZero() || digitFactor == 0) {
            retVal.setZero();
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

                retVal.appendDigitLeft(digitResult);
                if (i == 0 && carry != 0) {
                    retVal.getDigitArray().add(0, carry);
                    retVal.incLBC();
                    carry = 0;
                }
            }
        }
        return retVal;
    }

    /* Minuend and Subtrahend must be positive
       Minuend must be bigger than the Subtrahend
     */
    private static CustomNumber internalSubtraction(CustomNumber minuend, CustomNumber subtrahend) {
        CustomNumber retVal = new CustomNumber();
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

                retVal.appendDigitLeft(tempDigitResult);
            }

            if (minuend.getLAC() > 0) {
                retVal.shiftRight(minuend.getLAC());
            }
            if (minuend.getLAC() < 0) {
                retVal.shiftLeft(minuend.getLAC() * -1);
            }

            minuend.clean();
            subtrahend.clean();
            retVal.clean();
        } else {
            retVal.set(minuend);
        }

        return retVal;
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