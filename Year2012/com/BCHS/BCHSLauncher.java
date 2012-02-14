package Year2012.com.BCHS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;

public class BCHSLauncher 
{
	public Encoder encoder;
	Jaguar frontjag, backjag;
	PIDController PID1, PID2;
	double kp, ki, kd;
	
	
	public BCHSLauncher (int aChannel, int bChannel, int frontjagChannel, int backjagChannel)
	{
		encoder = new Encoder(aChannel, bChannel);
		frontjag = new Jaguar(frontjagChannel);
		backjag = new Jaguar(backjagChannel);
		PID1 = new PIDController(kp, ki, kd, encoder, frontjag);
		PID2 = new PIDController(kp, ki, kd, encoder, backjag);
		encoder.start();
		encoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
				
	}
	public void setRPM(double RPM)  //Set RPM of Launcher
	{
		PID1.setSetpoint(RPM);
		PID2.setSetpoint(RPM);
		PID1.enable();
		PID2.enable();
			
			
	}
	
	public void set(double speed)
	{
		frontjag.set(speed);
		backjag.set(speed);
	}		
}
