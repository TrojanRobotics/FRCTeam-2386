package Year2012.com.BCHS;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Kinect;
import edu.wpi.first.wpilibj.Timer;

public class BCHSBot extends IterativeRobot
{
	AnalogChannel ultraSonic;
	
	//BCHSBundle leftSide, rightSide;
	BCHSCamera cam;
	BCHSHockey hockeySticks;
	BCHSKinect xKinect;
	BCHSLauncher launcher;
	BCHSRetrieval retrieval;
	BCHSChasis chasis;
	
	DriverStation ds = DriverStation.getInstance();
	DriverStationLCD dsLCD = DriverStationLCD.getInstance();
	
	Encoder leftEncoder, rightEncoder;
	
	Joystick driveJoystick, secondaryJoystick;
	
	Kinect kinect;
	
	PIDController leftPID, rightPID;
	
	RobotDrive drive;
	
	
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

		//Chasis
		chasis = new BCHSChasis(Config.LENCODER[0], Config.LENCODER[1], Config.RENCODER[0], Config.RENCODER[1], Config.ULTRASONIC, Config.LDRIVE, Config.RDRIVE);
		chasis.setDistance(Config.LE_DPP);
		
		
		//Launcher
		launcher.encoder.setDistancePerPulse(Config.SE_DPP);
		launcher.encoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
		
		//Driving Systems
		//leftSide = new BCHSBundle(Config.LDRIVE[0], Config.LDRIVE[1]);
		//rightSide = new BCHSBundle(Config.RDRIVE[0], Config.RDRIVE[1]);
		drive = new RobotDrive(chasis.leftSide, chasis.rightSide);
		xKinect = new BCHSKinect(chasis.leftSide, chasis.rightSide, launcher, retrieval, hockeySticks);
		
		
		//Starting
		//sensor.start();
	}

	public void autonomousPeriodic()
	{
		if (ds.getDigitalIn(1))
		{
			String side = cam.leftOrRight();
			cam.getLargestParticle(new int[]{1, 2, 3, 4, 5, 6});
			
			if (side.equalsIgnoreCase("left"))
				drive.drive(1.0, -1.0);
			else if (side.equalsIgnoreCase("right"))
				drive.drive(1.0, 1.0);
			else if (side.equalsIgnoreCase("center"))
				drive.drive(0.0, 0.0);
		}
		else if (ds.getDigitalIn(2))
		{
			leftPID.setSetpoint(-120);
			rightPID.setSetpoint(120);
			hockeySticks.set(0.75);
			launcher.set(0.7);
			Timer.delay(3.0);
			retrieval.set(0.5);
			Timer.delay(5.0);
			retrieval.stop();
		}
		else if (ds.getDigitalIn(3))
		{
			xKinect.kinectDrive(kinect);
		}
	}
	
	public void teleopInit()
	{
		leftPID.disable();
		rightPID.disable();
	}

	public void teleopPeriodic()
	{
		if (Config.TESTING)
		{
			leftPID.setSetpoint(12);
			rightPID.setSetpoint(12);
		} 
		else 
		{
			drive.arcadeDrive(driveJoystick);
			
			if (driveJoystick.getRawButton(11))
				hockeySticks.set(0.75);
			else if (driveJoystick.getRawButton(10))
				hockeySticks.set(-0.75);
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
			
			if (secondaryJoystick.getRawButton(9))
				cam.lightsOn(true);
			else
				cam.lightsOn(false);
			
			if (secondaryJoystick.getRawButton(3))
				retrieval.set(0.75);
			else if (secondaryJoystick.getRawButton(2))
				retrieval.set(-0.75);
			else
				retrieval.set(0.0);
		}
	}
}