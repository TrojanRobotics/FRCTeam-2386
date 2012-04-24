package Year2012.com.BCHS;

import edu.wpi.first.wpilibj.Jaguar;

public class BCHSRetrieval 
{
	Jaguar liftJag;
	/**
	 * Creates object for retrieval.
	 * @param liftJagChannel Channel for jaguar.
	 */
	public BCHSRetrieval(int liftJagChannel)
	{
		liftJag = new Jaguar(liftJagChannel);
	}
	/**
	 * Set method for retrieval.
	 * @param speed Sets speed of jaguar.
	 */
	public void set(double speed)
	{
		liftJag.set(-speed);
	}
	/**
	 * Stop method for retrieval.
	 */
	public void stop()
	{
		liftJag.stopMotor();
	}
}
