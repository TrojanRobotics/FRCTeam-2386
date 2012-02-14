package Year2012.com.BCHS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;


public class BCHSLauncher 
{
	public Encoder encoder;
	PIDController launcherPID;
	double kp, ki, kd;
	BCHSBundle motorBundle;
	boolean runOnce = false;
	
	public BCHSLauncher(int aChannel, int bChannel, int channelOne, int channelTwo)
	{
		encoder = new Encoder(aChannel, bChannel);
		launcherPID = new PIDController(kp, ki, kd, encoder, motorBundle);
		encoder.start();
		encoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
		motorBundle = new BCHSBundle(channelOne, channelTwo);
				
	}
	
	public void setRPM(double RPM)  //Set RPM of Launcher
	{
		if (!runOnce)
		{	
			launcherPID.setSetpoint(RPM);
			launcherPID.enable();
			runOnce = true;
		}
	}
	
	public void set(double speed)
	{
		motorBundle.set(speed);
	}
	public void stop()
	{
		motorBundle.stop();
		launcherPID.disable();
	}
}
