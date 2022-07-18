package util;
import datatypes.*;

public class CCNUtils {
    public static CustomNumber absolute(CustomComplexNumber number, int relevant) {
        return new CustomNumber(CNUtils.squareRoot(CNUtils.addUp(CNUtils.exp(number.getReal(), 2, relevant), CNUtils.exp(number.getImag(), 2, relevant), relevant), relevant));
    }

    public static CustomNumber absolute(CustomComplexNumber number) {
        return new CustomNumber(absolute(number, -1));
    }

    public static CustomComplexNumber addUp(CustomComplexNumber n1, CustomComplexNumber n2, int relevant) {
        return new CustomComplexNumber(CNUtils.addUp(n1.getReal(), n2.getReal(), relevant), CNUtils.addUp(n1.getImag(), n2.getImag(), relevant));
    }

    public static CustomComplexNumber addUp(CustomComplexNumber n1, CustomComplexNumber n2) {
        return new CustomComplexNumber(addUp(n1, n2, -1));
    }

    public static CustomComplexNumber divide(CustomComplexNumber n1, CustomComplexNumber n2, int relevant) {
        CustomNumber c2d2 = CNUtils.addUp(CNUtils.exp(n2.getReal(), 2, relevant), CNUtils.exp(n2.getImag(), 2, relevant), relevant);
        return new CustomComplexNumber(new CustomNumber(CNUtils.divide(CNUtils.addUp(CNUtils.multiply(n1.getReal(), n2.getReal(), relevant), CNUtils.multiply(n1.getImag(), n2.getImag(), relevant), relevant), c2d2, relevant)), new CustomNumber(CNUtils.divide(CNUtils.subTr(CNUtils.multiply(n1.getImag(), n2.getReal(), relevant), CNUtils.multiply(n1.getReal(), n2.getImag(), relevant), relevant), c2d2, relevant)));
    }

    public static CustomComplexNumber divide(CustomComplexNumber n1, CustomComplexNumber n2) {
        return new CustomComplexNumber(divide(n1, n2, -1));
    }

    public static CustomComplexNumber multiply(CustomComplexNumber n1, CustomComplexNumber n2, int relevant) {
        return new CustomComplexNumber(CNUtils.subTr(CNUtils.multiply(n1.getReal(), n2.getReal(), relevant), CNUtils.multiply(n1.getImag(), n2.getImag(), relevant), relevant), CNUtils.addUp(CNUtils.multiply(n1.getReal(), n2.getImag(), relevant), CNUtils.multiply(n1.getImag(), n2.getReal(), relevant), relevant));
    }

    public static CustomComplexNumber multiply(CustomComplexNumber n1, CustomComplexNumber n2) {
        return new CustomComplexNumber(multiply(n1, n2, -1));
    }
    public static CustomComplexNumber subTr(CustomComplexNumber n1, CustomComplexNumber n2, int relevant) {
        return new CustomComplexNumber(CNUtils.subTr(n1.getReal(), n2.getReal(), relevant), CNUtils.subTr(n1.getImag(), n2.getImag(), relevant));
    }

    public static CustomComplexNumber subTr(CustomComplexNumber n1, CustomComplexNumber n2) {
        return new CustomComplexNumber(subTr(n1, n2, -1));
    }
}
