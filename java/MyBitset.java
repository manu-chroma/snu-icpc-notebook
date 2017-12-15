class MyBitSet {
    long bits[];
    int cardinality;
    int size;
    MyBitSet(int MAX) {
        size = MAX;
        bits = new long[((MAX - 1) / 64) + 1];
        cardinality = 0;
    }
    void set(int n, boolean f) {
        int index = n / 64;
        if (f) {
            if((bits[index] & (1L << (n % 64))) == 0)
                cardinality++;
            bits[index] |= (1L << (n % 64));
        }
        else
            bits[index] ^= (bits[index] & (1L << (n % 64))) != 0 ? (1L << (n % 64)) : 0;
    }
    boolean get(int n) {
        return ((bits[n / 64]) & (1L << (n % 64))) != 0;
    }    
}

