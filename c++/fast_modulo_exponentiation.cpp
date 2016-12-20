#define mod 1000000007

// mod defined as macro
long long int fast_exp(long long int base, long long int exp) {
    long long int res=1;
    while(exp>0) {
       if(exp%2==1) res=(res*base)%mod;
       base=(base*base)%mod;
       exp/=2;
    }
    return res%mod;
}
