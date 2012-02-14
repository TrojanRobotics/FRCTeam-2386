package Year2012.com.BCHS;
import edu.wpi.first.wpilibj.Jaguar;

public class BCHSRetrieval 
{
	Jaguar retrievaljag;
	
	public BCHSRetrieval(int retrievalJagChannel)
	{
		retrievaljag = new Jaguar(retrievalJagChannel);
	}
	public void set(double speed)
	{
		retrievaljag.set(speed);
	}
}
