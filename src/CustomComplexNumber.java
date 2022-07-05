public class CustomComplexNumber {
    private CustomNumber real;
    private CustomNumber imag;

    public CustomComplexNumber(CustomNumber real, CustomNumber imag)
    {
        this.real = new CustomNumber(real);
        this.imag = new CustomNumber(imag);
    }

    public CustomComplexNumber()
    {
        this.real = new CustomNumber();
        this.imag = new CustomNumber();
    }

    public CustomNumber getImag() {
        return imag;
    }

    public CustomNumber getReal() {
        return real;
    }

    public void setImag(CustomNumber imag) {
        this.imag.set(imag);
    }

    public void setReal(CustomNumber real) {
        this.real.set(real);
    }

    public void set(CustomComplexNumber number)
    {
        this.setReal(number.real);
        this.setImag(number.imag);
    }

    @Override
    public String toString() {
        return  "R: " + real +
                " I: " + imag;
    }
}
