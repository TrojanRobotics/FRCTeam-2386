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
	static final int RLIMIT_SWITCH = 10;
	
	/**
	 * Relay Constants
	 */
	static final int LIGHTS = 1;
	
	/**
	 * Value Constants
	 */
	static final double[] PID = { 1.0, 0.4, 0.235 };
	static final int[] CAM_HSL = { 0, 23, 31, 142, 73, 255 };
	static final int[] CAM_RGB = { 191, 255, 129, 229, 117, 190 };
	
	static final double LE_DPP = 0.0190;
	static final double RE_DPP = 0.0240;
	static final double SE_DPP = 0.18;
}
