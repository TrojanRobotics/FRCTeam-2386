package com.nickmcguire;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.parsing.IInputOutput;

/**
 * This class is designed to work with a Onza TE controller and most likely an Xbox controller
 * 
 * @author Nick McGuire
 * @version 1.0
 */
public class OnzaTE extends GenericHID implements IInputOutput
{
	final int kDefaultLYAxis = 2;
	final int kDefaultLXAxis = 1;
	final int kDefaultRYAxis = 5;
	final int kDefaultRXAxis = 4;
	final int kDefaultTriggerAxis = 3;
	
	/**
	 * Enumerations representing the possible axis
	 */
	public static class AxisType
	{
		public final int value;
		
		static final int kNumAxis = 4;
		
		public static final AxisType kRX = new AxisType(0);
		public static final AxisType kRY = new AxisType(1);
		public static final AxisType kLX = new AxisType(2);
		public static final AxisType kLY = new AxisType(3);
		
		public AxisType(int value)
		{
			this.value = value;
		}
	}
	
	/**
	 * Enumerations representing the possible buttons to select
	 */
	public static class XboxButtons
	{
		public final int value;
		
		public static final XboxButtons kAButton = new XboxButtons(1);
		public static final XboxButtons kBButton = new XboxButtons(2);
		public static final XboxButtons kXButton = new XboxButtons(3);
		public static final XboxButtons kYButton = new XboxButtons(4);
		public static final XboxButtons kLBButton = new XboxButtons(5);
		public static final XboxButtons kRBButton = new XboxButtons(6);
		public static final XboxButtons kBackButton = new XboxButtons(7);
		public static final XboxButtons kStartButton = new XboxButtons(8);
		public static final XboxButtons kLeftButton = new XboxButtons(9);
		public static final XboxButtons kRightButton = new XboxButtons(10);
		
		public XboxButtons(int value)
		{
			this.value = value;
		}
	}
	
	private DriverStation m_ds;
    private final int m_port;
    private final byte[] m_axes;
	
	/**
	 * This creates an OnzaTE object with the specified port
	 * 
	 * @param port Which port the OnzaTE is plugged into
	 */
	public OnzaTE(final int port)
	{
		m_ds = DriverStation.getInstance();
        m_axes = new byte[AxisType.kNumAxis];
        m_port = port;
		
		m_axes[AxisType.kRX.value] = kDefaultRXAxis;
        m_axes[AxisType.kRY.value] = kDefaultRYAxis;
        m_axes[AxisType.kLX.value] = kDefaultLXAxis;
        m_axes[AxisType.kLY.value] = kDefaultLYAxis;
	}
	
	/**
	 * This method returns the value of the button
	 * 
	 * @param button The button to check
	 * @return The value of the button
	 */
	public boolean getButton(XboxButtons button)
	{
		return getRawButton(button.value);
	}
	
	/**
	 * This method returns the value of the button
	 * 
	 * @param button The button to check
	 * @return The value of the button
	 */
	public boolean getRawButton(final int button)
	{
		return ((0x1 << (button - 1)) & m_ds.getStickButtons(m_port)) != 0;
	}
	
	/**
	 * This method returns the value of the axis
	 * 
	 * @param axis The axis to check
	 * @return The value of the axis
	 */
	public double getAxis(AxisType axis)
	{
		return getRawAxis(axis.value);
	}
	
	/**
	 * This method returns the value of the axis
	 * 
	 * @param axis The axis to check
	 * @return The value of the axis
	 */
	public double getRawAxis(final int axis)
	{
		return m_ds.getStickAxis(m_port, axis);
	}
	
	/**
	 * This method returns the value of the Bumper
	 * 
	 * @param hand The Bumper to check
	 * @return The value of the Bumper
	 */
	public boolean getBumper(Hand hand)
	{
		if (hand.value == Hand.kLeft.value)
			return getButton(XboxButtons.kRBButton);
		else
			return getButton(XboxButtons.kLBButton);
	}
	
	/**
	 * NOT SUPPORTED
	 * 
	 * @param hand Unused
	 * @return false
	 */
	public boolean getTop(Hand hand)
	{
		return false;
	}

	/**
	 * NOT SUPPORTED (Being worked on)
	 * 
	 * @param hand
	 * @return false
	 */
	public boolean getTrigger(Hand hand)
	{
		return false;
	}
	
	/**
	 * NOT SUPPORTED
	 * 
	 * @return 0.0
	 */
	public double getThrottle()
	{
		return 0.0;
	}
	
	/**
	 * NOT SUPPORTED
	 * 
	 * @return 0.0
	 */
	public double getTwist()
	{
		return 0.0;
	}
	
	/**
	 * This method returns the X value of the Joystick
	 * 
	 * @param hand The Joystick to check
	 * @return The value of the Joystick
	 */
	public double getX(Hand hand)
	{
		if (hand.value == Hand.kLeft.value)
			return m_ds.getStickAxis(m_port, m_axes[AxisType.kLX.value]);
		else
			return m_ds.getStickAxis(m_port, m_axes[AxisType.kLX.value]);
	}
	
	/**
	 * This method returns the Y value of the Joystick
	 * 
	 * @param hand The Joystick to check
	 * @return The value of the Joystick
	 */
	public double getY(Hand hand)
	{
		if (hand.value == Hand.kLeft.value)
			return m_ds.getStickAxis(m_port, m_axes[AxisType.kLY.value]);
		else
			return m_ds.getStickAxis(m_port, m_axes[AxisType.kRY.value]);
	}
	
	/**
	 * NOT SUPPORTED
	 * 
	 * @param Unused
	 * @return 0.0
	 */
	public double getZ(Hand hand)
	{
		return 0.0;
	}
}