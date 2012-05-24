package Year2012.com.BCHS;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class BCHSBot extends IterativeRobot
{
	BCHSCamera cam;
	BCHSHockey hockeySticks;
	BCHSKinect xKinect;
	BCHSLauncher launcher;
	BCHSRetrieval retrieval;
	BCHSChasis chasis;
    //SafetyHelper safety;
	
	DriverStation ds = DriverStation.getInstance();
	DriverStationLCD dsLCD = DriverStationLCD.getInstance();
	
	Joystick driveJoystick, secondaryJoystick;
	
	Kinect kinect;
	
	boolean test = true;
	boolean autoOnce = true;
	
	public void robotInit()
	{
		//Control Inputs
		driveJoystick = new Joystick(Config.MAIN_JOYSTICK);
		secondaryJoystick = new Joystick(Config.SECONDARY_JOYSTICK);
		kinect = Kinect.getInstance();
		
		//Functional Mechanisms
		launcher = new BCHSLauncher(Config.SENCODER[0], Config.SENCODER[1], Config.SHOOTER[0], Config.SHOOTER[1]);
		retrieval = new BCHSRetrieval(Config.RETRIEVE);
		hockeySticks = new BCHSHockey(Config.HOCKEY, Config.TLIMIT_SWITCH, Config.BLIMIT_SWITCH);
		cam = new BCHSCamera();
		
		//Drive Systems
		chasis = new BCHSChasis(Config.LENCODER[0], Config.LENCODER[1], Config.RENCODER[0], Config.RENCODER[1], Config.ULTRASONIC, Config.LDRIVE, Config.RDRIVE);
		xKinect = new BCHSKinect(chasis.leftSide, chasis.rightSide, launcher, retrieval, hockeySticks);
		
                //Other
        //safety = SafetyHelper.getInstance();
		dsLCD.println(DriverStationLCD.Line.kUser6, 1, "V1.2");
		dsLCD.updateLCD();
	}
	
	public void disabledPeriodic()
	{
		test = true;
		autoOnce = true;
	}
	
	public void autonomousInit()
	{
		
	}

	public void autonomousPeriodic()
	{
		if (ds.getDigitalIn(1))
		{
			
		}
		else if (ds.getDigitalIn(2))
		{
			hockeySticks.set(0.75);
			launcher.set(0.50);
			Timer.delay(3.0);
			retrieval.set(0.5);
			Timer.delay(5.0);
			retrieval.stop();
		}
		else if (ds.getDigitalIn(3))
		{
			xKinect.kinectDrive(kinect);
		}
		else if (ds.getDigitalIn(4) && autoOnce)
		{
			chasis.set(0.75);
			hockeySticks.set(1.0);
			Timer.delay(1.0);
			chasis.stop();
			Timer.delay(1.0);
			launcher.set(0.45);
			Timer.delay(1.5);
			retrieval.set(0.8);
			Timer.delay(1.25);
			hockeySticks.stop();
			Timer.delay(10.0);
			launcher.stop();
			retrieval.stop();
			autoOnce = false;
		}
		else if (ds.getDigitalIn(5) && autoOnce)
		{
			launcher.set(0.75);
			Timer.delay(1.0);
			retrieval.set(0.8);
			Timer.delay(5.0);
			launcher.stop();
			retrieval.stop();
			autoOnce = false;
		}
	}
	
	public void teleopInit()
	{
		chasis.stop();
	}

	public void teleopPeriodic()
	{	
		if (Config.TESTING && test)
		{
			
		} 
		else 
		{
			dsLCD.println(DriverStationLCD.Line.kMain6, 1, ""+MathUtils.round(-launcher.encoder.getRate()));
			
			launcher.lightsOn();
			
			double x = driveJoystick.getX();
			double y = driveJoystick.getY();
			
			x = BCHSLib.signSquare(x);
			y = BCHSLib.signSquare(y);
			
			chasis.leftSide.set(BCHSLib.limitOutput(y - x));
			chasis.rightSide.set(-BCHSLib.limitOutput(y + x));
			
			if (secondaryJoystick.getRawButton(11))
				hockeySticks.set(-1.0);
			else if (secondaryJoystick.getRawButton(10))
				hockeySticks.set(1.0);
			else
				hockeySticks.set(0);

			if (secondaryJoystick.getTrigger())
				launcher.set(1.0);
			else if (secondaryJoystick.getRawButton(2))
				launcher.set(0.75);
			else if (secondaryJoystick.getRawButton(3))
				launcher.set(0.5);
			else if (secondaryJoystick.getRawButton(4))
				launcher.set(0.25);
			else
				launcher.set(0.0);

			if (secondaryJoystick.getRawButton(6))
				retrieval.set(0.75);
			else if (secondaryJoystick.getRawButton(7))
				retrieval.set(-0.75);
			else
				retrieval.set(0.0);
			
			dsLCD.updateLCD();
			SmartDashboard.putDouble("leftSide", chasis.leftEncoder.getRate());
			SmartDashboard.putDouble("RightSide", chasis.rightEncoder.getRate());
		}
	}
}