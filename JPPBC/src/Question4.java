
public class Question4 {
	public static double maxSpending(double[] amounts){
		double max=0;
		for(double amount:amounts){
			double value=((int)(100.0/amount))*amount;
			if(value>max)
				max=value;
		}
		if(max==100.0)
			return max;
		try{
			for(int i=0;i<amounts.length;i++){
				double value=best(amounts,0,0);
				if(value>max)
					max=value;
			}
		}
		catch(Exception e){
			return 100.0;
		}
		return max;
	}
	private static double best(double[] amounts,double currentTotal,int previousIndex) throws Exception{
		if(currentTotal>100.0)
			return 0;
		else if(currentTotal==100.0)
			throw new Exception("Finished!");
		double best=currentTotal;
		for(int i=previousIndex;i<amounts.length;i++){
			double value=best(amounts,currentTotal+amounts[i],i);
			if(value>best)
				best=value;
		}
		return best;
	}
}
