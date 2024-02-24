public class ComplexNumber {
    private final double a;
    private final double bi;

    public static final ComplexNumber MULTIPLICATIVE_IDENTITY = new ComplexNumber(1, 0);

    private ComplexNumber(double a, double bi) {
        this.a = a;
        this.bi = bi;
    }

    public static ComplexNumber rectangular(double a, double bi) {
        return new ComplexNumber(a, bi);
    }

    public static ComplexNumber polar(double modulus, double argument) {
       return new ComplexNumber(modulus * Math.cos(argument), modulus * Math.sin(argument));
    }

    public static ComplexNumber sum(ComplexNumber... zs) {
        var res = ComplexNumber.rectangular(0,0);

        for (var z : zs){
            res = ComplexNumber.rectangular(res.a + z.a, res.bi + z.bi);
        }
        return res;
    }

    public static ComplexNumber product(ComplexNumber... zs) {
        var prod = ComplexNumber.MULTIPLICATIVE_IDENTITY;

        for (var z : zs) {
            prod = ComplexNumber.polar(prod.modulus() * z.modulus(), prod.argument() + z.argument());
        }

        return prod;
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


    //Returns the positive root of the complex number
    //        sqrt of imaginary number is imaginary
    //        https://www.cuemath.com/algebra/square-root-of-complex-number/
    public ComplexNumber sqrt(){
        //handle all cases where b is 0
        if ((bi == 0) && (a>=0)) return ComplexNumber.rectangular(Math.sqrt(a),0);
        if ((bi == 0) && (a<0)) return ComplexNumber.rectangular(0, Math.sqrt(-a));

        //then use formula
        return ComplexNumber.rectangular(Math.sqrt(((this.modulus() + a)/2.0)),
                (bi/Math.abs(bi))*Math.sqrt((this.modulus() - a)/2.0));
    }

    public double argument() {
        if (a == 0 && bi == 0) throw new RuntimeException("Argument of 0 is undefined");
        if (Math.round(a) == 0 && bi > 0) return Math.PI / 2;
        if (Math.round(a) == 0 && bi < 0) return 3 * Math.PI / 2;
        if (Math.round(a) > 0 && bi == 0) return 0;
        if (Math.round(a) < 0 && bi == 0) return Math.PI;

        if (a > 0 && bi > 0) return Math.atan(bi / a);
        if (a < 0 && bi > 0) return Math.PI - Math.atan(bi / -a);
        if (a > 0 && bi < 0) return 2*Math.PI - Math.atan(-bi / a);
        return Math.PI + Math.atan(bi/a);
    }

    public ComplexNumber additiveInverse(){
        return ComplexNumber.rectangular(this.a*-1, this.bi*-1);
    }

    public ComplexNumber multiplicativeInverse(){
        if (a == 0 && bi == 0) throw new RuntimeException("v = 0 has no inverse");

        return ComplexNumber.rectangular(a/(a*a + bi*bi), -1*bi/(a*a + bi*bi));
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
