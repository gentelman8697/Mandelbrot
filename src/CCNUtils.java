public class CCNUtils {
    public static CustomComplexNumber addUp(CustomComplexNumber n1, CustomComplexNumber n2) {
        return new CustomComplexNumber(CNUtils.addUp(n1.getReal(), n2.getReal()), CNUtils.addUp(n1.getImag(), n2.getImag()));
    }

    public static CustomComplexNumber subTr(CustomComplexNumber n1, CustomComplexNumber n2) {
        return new CustomComplexNumber(CNUtils.subTr(n1.getReal(), n2.getReal()), CNUtils.subTr(n1.getImag(), n2.getImag()));
    }

    public static CustomComplexNumber multiply(CustomComplexNumber n1, CustomComplexNumber n2) {
        return new CustomComplexNumber(CNUtils.subTr(CNUtils.multiply(n1.getReal(), n2.getReal()), CNUtils.multiply(n1.getImag(), n2.getImag())), CNUtils.addUp(CNUtils.multiply(n1.getReal(), n2.getImag()), CNUtils.multiply(n1.getImag(), n2.getReal())));
    }

    public static CustomComplexNumber divide(CustomComplexNumber n1, CustomComplexNumber n2) {
        CustomNumber c2d2 = CNUtils.addUp(CNUtils.exp(n2.getReal(), 2), CNUtils.exp(n2.getImag(), 2));
        return new CustomComplexNumber(new CustomNumber(CNUtils.divide(CNUtils.addUp(CNUtils.multiply(n1.getReal(), n2.getReal()), CNUtils.multiply(n1.getImag(), n2.getImag())), c2d2)), new CustomNumber(CNUtils.divide(CNUtils.subTr(CNUtils.multiply(n1.getImag(), n2.getReal()), CNUtils.multiply(n1.getReal(), n2.getImag())), c2d2)));
    }

    public static CustomNumber absolute(CustomComplexNumber number) {
        return new CustomNumber(CNUtils.squareRoot(CNUtils.addUp(CNUtils.exp(number.getReal(), 2), CNUtils.exp(number.getImag(), 2)), 25));
    }
}
