package Year2012.com.BCHS;
import edu.wpi.first.wpilibj.Jaguar;

public class BCHSRetrieval 
{
	Jaguar liftJag;
	
	public BCHSRetrieval(int liftJagChannel)
	{
		liftJag = new Jaguar(liftJagChannel);
	}
	public void set(double speed)
	{
		liftJag.set(speed);
	}
	public void stop()
	{
		liftJag.stopMotor();
	}
}
