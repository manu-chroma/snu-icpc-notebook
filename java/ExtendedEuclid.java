class ExtendedEuclid {
    // For comments please refer to uva 10090
    /*
     *  If the Linear Diophantine Equation is 
     *  ax + by = c --- (1)
     *  then we denote the canonical equation of 1 as 
     *  ax + by = d --- (2) , where d = gcd(a , b)
     *  using extended Euclid algorithm we get the solution for 2 as (x0 , y0)
     *  
     *  Now the general solution for 1 is given by
     *  x" = (x0 * c + t * b) / d
     *  y" = (y0 * c - t * a) / d
     *  
     */
    static long gcd(long a , long b) { return (b == 0) ? a : gcd(b, a % b); }
    static long[] extendedEuclid(long a , long b) {
        long rnm2[] = new long[]{0 , 1};    // r_n-2 , x , y
        long rnm1[] = new long[]{1 , 0};    // r_n-1 , u , v
        while(a != 0) {
            long r = ((b % a) + a) % a;
            long q = Long.signum(a) * Long.signum(b) < 0 && b % a != 0 ? b / a - 1 : b / a;
            long coeffA = rnm2[0] - q * rnm1[0];
            long coeffB = rnm2[1] - q * rnm1[1];
            b = a;
            a = r;
            rnm2[0] = rnm1[0];
            rnm2[1] = rnm1[1];
            rnm1[0] = coeffA;
            rnm1[1] = coeffB;
        }
        return new long[]{rnm2[0], rnm2[1] , b};
    }
    // greater than a / b
    static long grt(long a , long b) {
        return  (a / b) + (Long.signum(a) * Long.signum(b) < 0 && a % b != 0 ? 0 : 1);
    }
    // less than a / b
    static long less(long a , long b) {
        return (a / b) + (Long.signum(a) * Long.signum(b) > 0 && a % b != 0 ? 0 : -1);
    }
    // greater than equal to a / b
    static long grtEq(long a , long b) {
        return (a / b) + (Long.signum(a) * Long.signum(b) > 0 && a % b != 0 ? 1 : 0);
    }
    // less than or equal to a / b
    static long lessEq(long a , long b) {
        return (a / b) + (Long.signum(a) * Long.signum(b) < 0 && a % b != 0 ? -1 : 0);
    }
}
