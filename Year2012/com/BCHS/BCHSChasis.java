/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Year2012.com.BCHS;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Ultrasonic;

/**
 *
 * @author nickmcguire
 */
public class BCHSChasis
{
	BCHSBundle leftSide, rightSide;
	Encoder leftEncoder, rightEncoder;
	AnalogChannel ultrasonic;
	PIDController leftSidePID, rightSidePID;
	
	public BCHSChasis(int leftAChannel, int leftBChannel, int rightAChannel, int rightBChannel, int ultraSonic, int[] leftSide, int[] rightSide)
	{
		leftEncoder = new Encoder(leftAChannel, leftBChannel);
		rightEncoder = new Encoder(rightAChannel, rightBChannel);
		ultrasonic = new AnalogChannel(ultraSonic);
		this.leftSide = new BCHSBundle(leftSide[0], leftSide[1]);
		this.rightSide = new BCHSBundle(rightSide[0], rightSide[1]);
		
		leftEncoder.setDistancePerPulse(Config.LE_DPP);
		rightEncoder.setDistancePerPulse(Config.RE_DPP);
		
		leftEncoder.start();
		rightEncoder.start();
	}
	
	public void set(double speed)
	{
		leftSide.set(speed);
		rightSide.set(speed);
	}
	
	public void enable()
	{
		leftSidePID.enable();
		rightSidePID.enable();
	}
	
	public void stop()
	{
		leftSide.stop();
		rightSide.stop();
		leftSidePID.disable();
		rightSidePID.disable();
	}
	
	public void reset()
	{
		leftEncoder.reset();
		rightEncoder.reset();
	}
}
