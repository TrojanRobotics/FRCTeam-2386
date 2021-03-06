package com.BCHS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Relay;

public class Launcher 
{
	Encoder encoder;
	PIDController launcherPID;
	double kp, ki, kd;
	Bundle motorBundle;
	LimitSwitch limit;
	Relay relay;
	
	/**
	 * Creates a Launcher object.
	 * @param aChannel The "a" channel for the encoder.
	 * @param bChannel The "b" channel for the encoder.
	 * @param channelOne The 1st channel for the PID bundle.
	 * @param channelTwo The 2nd channel for the PID bundle.
	 */
	public Launcher(int aChannel, int bChannel, int channelOne, int channelTwo)
	{
		relay = new Relay(Config.LIGHTS);
		relay.setDirection(Relay.Direction.kReverse);
		kp = 0.15;
		ki = 0.0;
		kd = 0.0;
		
		encoder = new Encoder(aChannel, bChannel);
		encoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
		encoder.setDistancePerPulse(Config.SE_DPP);
		encoder.start();
		
		motorBundle = new Bundle(channelOne, channelTwo);
		launcherPID = new PIDController(kp, ki, kd, encoder, motorBundle);		
		limit = new LimitSwitch(Config.RLIMIT_SWITCH, true);
	}
	/**
	 * A method to set RPM
	 * @param RPM Setpoint for PID.
	 */
	public void setRPM(double RPM)
	{
		launcherPID.enable();
		launcherPID.setSetpoint(RPM);
	}
	/**
	 * Set method for BCHSBundle
	 * @param speed Sets speed for PID bundle.
	 */
	public void set(double speed)
	{
		motorBundle.set(-speed);
	}
	/**
	 * Stop method for BCHSBundle.
	 */
	public void stop()
	{
		motorBundle.stop();
		launcherPID.disable();
	}
	
	public boolean isReady()
	{
		if (limit.get())
			return true;
		else
			return false;
	}
		
	public void lightsOn()
        {
		if (isReady())
			relay.set(Relay.Value.kOn);
		else
			relay.set(Relay.Value.kOff);
	}
	
}
