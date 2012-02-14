package Year2012.com.BCHS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;


public class BCHSLauncher 
{
	Encoder encoder;
	PIDController launcherPID;
	double kp, ki, kd;
	BCHSBundle motorBundle;
	
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
		launcherPID.setSetpoint(RPM);
		launcherPID.enable();
	}
	
	public void set(double speed)
	{
		motorBundle.set(speed);
	}		
}
