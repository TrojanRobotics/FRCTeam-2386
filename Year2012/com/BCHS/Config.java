package Year2012.com.BCHS;

public interface Config
{
	/**
	 * Testing Constants
	 */
	static final boolean TESTING = false;
	
	/**
	 * GenericHID Constants
	 */
	static final int MAIN_JOYSTICK = 1;
	static final int SECONDARY_JOYSTICK = 2;
	
	/**
	 * PWM Constants
	 */
	static final int[] LDRIVE = { 1, 2 };
	static final int[] RDRIVE = { 3, 4 };
	static final int[] SHOOTER = { 7, 8 };
	
	static final int RETRIEVE = 5;
	static final int HOCKEY = 6;
	
	/**
	 * Analog Constants
	 */
	static final int ULTRASONIC = 1;
	
	/**
	 * Digital Constants
	 */
	static final int[] LENCODER = { 3, 4 };
	static final int[] RENCODER = { 1, 2 };
	static final int[] SENCODER = { 5, 6 };
	
	static final int TLIMIT_SWITCH = 8;
	static final int BLIMIT_SWITCH = 9;
	
	/**
	 * Value Constants
	 */
	static final double[] PID = { 0.0, 0.0, 0.0 };
	static final double[] CAM_HSL = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
	
	static final double LE_DPP = 0.0;
	static final double RE_DPP = 0.0;
	static final double SE_DPP = 0.0;
}
