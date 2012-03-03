package Year2012.com.BCHS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;


public class BCHSLauncher 
{
	Encoder encoder;
	PIDController launcherPID;
	double kp, ki, kd;
	BCHSBundle motorBundle;
	DigitalInput conveyorLimit;
	
	/**
	 * Creates a Launcher object.
	 * @param aChannel The "a" channel for the encoder.
	 * @param bChannel The "b" channel for the encoder.
	 * @param channelOne The 1st channel for the PID bundle.
	 * @param channelTwo The 2nd channel for the PID bundle.
	 */
	public BCHSLauncher(int aChannel, int bChannel, int channelOne, int channelTwo)
	{
		encoder = new Encoder(aChannel, bChannel);
		motorBundle = new BCHSBundle(channelOne, channelTwo);
		launcherPID = new PIDController(kp, ki, kd, encoder, motorBundle);
		encoder.start();
		encoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
	}
	/**
	 * A method to set RPM
	 * @param RPM Setpoint for PID.
	 */
	public void setRPM(double RPM)  //Set RPM of Launcher
	{
		launcherPID.setSetpoint(RPM);
		launcherPID.enable();
	}
	/**
	 * Set method for BCHSBundle
	 * @param speed Sets speed for PID bundle.
	 */
	public void set(double speed)
	{	
		motorBundle.set(-speed);	
	}
	public boolean isReady()
	{
		if (conveyorLimit.get() == true)
			return false;
		else
			return true;
	}	
	/**
	 * Stop method for BCHSBundle.
	 */
	public void stop()
	{
		motorBundle.stop();
		launcherPID.disable();
	}
}
