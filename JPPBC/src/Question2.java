/**
 * Our 4th Philly Classic Question. Philly Classic Question 2.
 * @author Jay
 * @version 9/16/13
 */
public class Question2 {
	/**
	 * Finds the prime less than N and the prime greater than T which whose sum of the distances between them and their respective numbers is minimized.
	 * @param N - the first number
	 * @param T - the second number
	 * @return - the fuzzed numbers
	 */
	public static int[] fuzzNumbers(int N, int T){
		for(int i=2;i<N;i++)
			for(int j=1;j<i-1;j++){
				int Nprime=N-j,Tprime=T+i-j-1;
				if(isPrime(Nprime)&&isPrime(Tprime)&&isPrime((Nprime+Tprime)/2))
					return new int[]{Nprime,Tprime};
			}
		return null;
	}
	public static boolean isPrime(int number){
		int sqrt=(int)Math.sqrt(number);
		for(int i=2;i<=sqrt;i++)
			if(number%i==0)
				return false;
		return true;
	}
}
