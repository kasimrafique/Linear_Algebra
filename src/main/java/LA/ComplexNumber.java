package LA;

import java.util.Arrays;
import java.util.Optional;

public class ComplexNumber {
    private final double a;
    private final double bi;

    public static final ComplexNumber MULTIPLICATIVE_IDENTITY = new ComplexNumber(1, 0);
    public static final ComplexNumber ZERO = new ComplexNumber(0, 0);

    public static final ComplexNumber ADDITIVE_IDENTITY = ZERO;

    private ComplexNumber(double a, double bi) {
        this.a = a;
        this.bi = bi;
    }

    public static ComplexNumber real(double x) {
        return new ComplexNumber(x, 0);
    }

    public static ComplexNumber rectangular(double a, double bi) {
        return new ComplexNumber(a, bi);
    }

    public static ComplexNumber polar(double modulus, double argument) {
       return new ComplexNumber(modulus * Math.cos(argument), modulus * Math.sin(argument));
    }

    public static ComplexNumber sum(ComplexNumber... zs) {
        return Arrays.stream(zs).reduce(
                ComplexNumber.ADDITIVE_IDENTITY,
                (sum, z) -> ComplexNumber.rectangular(sum.a + z.a, sum.bi + z.bi)
        );
    }

    public static ComplexNumber product(ComplexNumber... zs) {
        return Arrays.stream(zs).reduce(
                ComplexNumber.MULTIPLICATIVE_IDENTITY,
                (prod, z) -> ComplexNumber.rectangular(prod.a * z.a - prod.bi * z.bi, prod.a * z.bi + prod.bi * z.a)
        );
    }

    public double realPart() {
        return this.a;
    }

    public double imaginaryPart() {
        return this.bi;
    }

    public ComplexNumber conjugate() {
        return new ComplexNumber(a, -bi);
    }

    public double modulus() {
        return Math.sqrt(a * a + bi * bi);
    }


    // Returns the positive root of the complex number
    //        sqrt of imaginary number is imaginary
    //        https://www.cuemath.com/algebra/square-root-of-complex-number/
    public ComplexNumber sqrt(){
        // Handle all cases where b is 0
        if ((bi == 0) && (a>=0)) return ComplexNumber.rectangular(Math.sqrt(a),0);
        if ((bi == 0) && (a<0)) return ComplexNumber.rectangular(0, Math.sqrt(-a));

        return ComplexNumber.rectangular(Math.sqrt(((this.modulus() + a)/2.0)),
                (bi/Math.abs(bi))*Math.sqrt((this.modulus() - a)/2.0));
    }

    // Argument of complex number between 0 - 2Pi
    // i.e. not negative
    public Double argument() {
        double r = 0.0;
        if (a == 0 && bi == 0) r = 0.0;
        if (Math.round(a) == 0 && bi > 0) r = Math.PI / 2;
        if (Math.round(a) == 0 && bi < 0) r = 3 * Math.PI / 2;
        if (Math.round(a) > 0 && bi == 0) r = 0.0;
        if (Math.round(a) < 0 && bi == 0) r = Math.PI;

        if (a > 0 && bi > 0) r = Math.atan(bi / a);
        if (a < 0 && bi > 0) r = Math.PI - Math.atan(bi / -a);
        if (a > 0 && bi < 0) r = 2*Math.PI - Math.atan(-bi / a);
        if (a < 0 && bi < 0) r = Math.PI + Math.atan(bi/a);

        return r;
    }

    public ComplexNumber additiveInverse(){
        return ComplexNumber.rectangular(this.a*-1, this.bi*-1);
    }

    public Optional<ComplexNumber> multiplicativeInverse(){
        if (a == 0 && bi == 0) return Optional.empty();

        return Optional.of(ComplexNumber.rectangular(a/(a*a + bi*bi), -1*bi/(a*a + bi*bi)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComplexNumber that = (ComplexNumber) o;
        return Double.compare(a, that.a) == 0 && Double.compare(bi, that.bi) == 0;
    }

    @Override
    public String toString() {
        return "ComplexNumber{" + a + " + " + bi + "i}";
    }
}
