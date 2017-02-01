// Sieve of Eratosthenes in O(N*loglogN)
#include <vector>
#include <iostream>
using namespace std;

vector<int> getPrimes(int n) {
  vector<bool> prime(n + 1, true);
  prime[0] = prime[1] = false;
  vector<int> primes;
  for (int i = 2; i <= n; i++)
	if (prime[i]) {
	  for (int j = i * i; j <= n; j += i) {
		  prime[j] = false;
		}

	  primes.push_back(i);
	}
  return primes;
}

// brute force to root[n]
bool isPrime(long long n) {
  if (n <= 1)
	  return false;

  for (long long i = 2; i * i <= n; i++)
	if (n % i == 0)
	  return false;

  return true;
}

int main() {
  // bound on the largest prime generated
  int n = (int)1e5; 
  // array containing all the primes 
  vector<int> primes = getPrimes(n);

  cout << "prime array size: " << primes.size() << "\n";
  for (int i = 0; i < primes.size(); i++)
	  cout << primes[i] << " ";

  cout << endl;

  // for (int i = 0; i <= n; i++)
  //   if (isPrime(i))
  //     cout << i << " ";
}
