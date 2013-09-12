
public class Question4 {
	public static double maxSpending(double[] amounts){
		double max=0;
		for(int i=0;i<amounts.length;i++){
			double value=best(amounts,0,0);
			if(value>max)
				max=value;
		}
		return max;
	}
	private static double best(double[] amounts,double currentTotal,int previousIndex){
		if(currentTotal>100.0)
			return 0;
		double best=currentTotal;
		for(int i=previousIndex;i<amounts.length;i++){
			double value=best(amounts,currentTotal+amounts[i],i);
			if(value>best)
				best=value;
		}
		return best;
	}
}
