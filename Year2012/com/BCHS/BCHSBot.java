package Year2012.com.BCHS;

import com.sun.squawk.util.MathUtils;
//import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationLCD;
//import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Kinect;
import edu.wpi.first.wpilibj.Relay.Value;
//import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.camera.AxisCameraException;
//import edu.wpi.first.wpilibj.image.NIVisionException;
//import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboardData;

public class BCHSBot extends IterativeRobot
{
	double DELAY = 10.0;
	
	//AnalogChannel ultraSonic;
	
	//BCHSBundle leftSide, rightSide;
	BCHSCamera cam;
	BCHSHockey hockeySticks;
	BCHSKinect xKinect;
	BCHSLauncher launcher;
	BCHSRetrieval retrieval;
	BCHSChasis chasis;
	
	DriverStation ds = DriverStation.getInstance();
	DriverStationLCD dsLCD = DriverStationLCD.getInstance();
	
	//Encoder leftEncoder, rightEncoder;
	
	Joystick driveJoystick, secondaryJoystick;
	
	Kinect kinect;
	
	//PIDController leftPID, rightPID;
	
	RobotDrive drive;
	
	boolean test = true;
	
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

		//Sensors
		//leftEncoder = new Encoder(Config.LENCODER[0], Config.LENCODER[1]);
		//rightEncoder = new Encoder(Config.RENCODER[0], Config.RENCODER[1]);
		//ultraSonic = new AnalogChannel(Config.ULTRASONIC);
		
		//leftEncoder.setDistancePerPulse(Config.LE_DPP);
		//rightEncoder.setDistancePerPulse(Config.RE_DPP);
		
		//leftEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
		//rightEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
		
		//Driving Systems
		//leftSide = new BCHSBundle(Config.LDRIVE[0], Config.LDRIVE[1]);
		//rightSide = new BCHSBundle(Config.RDRIVE[0], Config.RDRIVE[1]);
		
		chasis = new BCHSChasis(Config.LENCODER[0], Config.LENCODER[1], Config.RENCODER[0], Config.RENCODER[1], Config.ULTRASONIC, Config.LDRIVE, Config.RDRIVE);
		drive = new RobotDrive(chasis.leftSide, chasis.rightSide);
		xKinect = new BCHSKinect(chasis.leftSide, chasis.rightSide, launcher, retrieval, hockeySticks);
		//leftPID = new PIDController(Config.PID[0], Config.PID[1], Config.PID[2], leftEncoder, leftSide);
		//rightPID = new PIDController(Config.PID[0], Config.PID[1], Config.PID[2], rightEncoder, rightSide);
		
		//Starting
		//rightEncoder.start();
		//leftEncoder.start();
		
		SmartDashboard.putDouble("PD", Config.PID[0]);
		SmartDashboard.putDouble("ID", Config.PID[1]);
		SmartDashboard.putDouble("DD", Config.PID[2]);
		
		SmartDashboard.putDouble("CheckPL", chasis.leftSidePID.getP());
		SmartDashboard.putDouble("CheckIL", chasis.leftSidePID.getI());
		SmartDashboard.putDouble("CheckDL", chasis.leftSidePID.getD());
		
		SmartDashboard.putDouble("CheckPR", chasis.rightSidePID.getP());
		SmartDashboard.putDouble("CheckIR", chasis.rightSidePID.getI());
		SmartDashboard.putDouble("CheckDR", chasis.rightSidePID.getD());
		
		SmartDashboard.putDouble("TimerDelay", DELAY);
	}
	
	public void disabledPeriodic()
	{
		test = true;
		
		try {
			chasis.leftSidePID.setPID(SmartDashboard.getDouble("PD"), SmartDashboard.getDouble("ID"), SmartDashboard.getDouble("DD"));
			chasis.rightSidePID.setPID(SmartDashboard.getDouble("PD"), SmartDashboard.getDouble("ID"), SmartDashboard.getDouble("DD"));
			DELAY = SmartDashboard.getDouble("TimerDelay");
		} catch (NetworkTableKeyNotDefined ex) {
			ex.printStackTrace();
		}
		
		SmartDashboard.putDouble("CheckPL", chasis.leftSidePID.getP());
		SmartDashboard.putDouble("CheckIL", chasis.leftSidePID.getI());
		SmartDashboard.putDouble("CheckDL", chasis.leftSidePID.getD());
		
		SmartDashboard.putDouble("CheckPR", chasis.rightSidePID.getP());
		SmartDashboard.putDouble("CheckIR", chasis.rightSidePID.getI());
		SmartDashboard.putDouble("CheckDR", chasis.rightSidePID.getD());
		
		SmartDashboard.putString("GameMode", "Disabled");
		
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
			//leftPID.setSetpoint(-120);
			//rightPID.setSetpoint(120);
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
		else if (ds.getDigitalIn(4))
		{
			drive.drive(-0.75, 0.0);
			//launcher.set(0.7);
			//retrieval.set(0.8);
			//hockeySticks.set(1.0);
			Timer.delay(5000.0);
			drive.drive(0.0, 0.0);
			
		}
	}
	
	public void teleopInit()
	{
		chasis.stop();
		
		SmartDashboard.putString("GameMode", "Init");
	}

	public void teleopPeriodic()
	{
		SmartDashboard.putString("GameMode", "Periodic");
		
		SmartDashboard.putInt("Right Encoder", chasis.rightEncoder.get());
		SmartDashboard.putInt("Left Encoder", chasis.leftEncoder.get());
			
		SmartDashboard.putDouble("RightPID", chasis.rightEncoder.pidGet());
		SmartDashboard.putDouble("LeftPID", chasis.leftEncoder.pidGet());
		
		SmartDashboard.putDouble("Distance", BCHSLib.voltageToDistance(chasis.ultrasonic));
		
		SmartDashboard.putDouble("Shooter Encoder", MathUtils.round(launcher.encoder.pidGet()) * -1 );
		
		
		if (Config.TESTING && test)
		{
			//rightPID.enable();
			//leftPID.enable();
			//launcher.setRPM(-100.0);
			//rightPID.setSetpoint(48);
			//leftPID.setSetpoint(-48);
			
			//test = false;
			
			/*
			dsLCD.println(DriverStationLCD.Line.kMain6, 1, "                     ");
			dsLCD.println(DriverStationLCD.Line.kUser2, 1, "                     ");
			dsLCD.println(DriverStationLCD.Line.kUser3, 1, "                     ");
			dsLCD.updateLCD();
			
			dsLCD.println(DriverStationLCD.Line.kMain6, 1, "SEG: " + launcher.encoder.get());
			dsLCD.println(DriverStationLCD.Line.kUser2, 1, "SEG: " + rightEncoder.get());
			dsLCD.println(DriverStationLCD.Line.kUser3, 1, "SEG: " + leftEncoder.get());
			
			 *
			 */
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

			if (secondaryJoystick.getRawButton(3))
				retrieval.set(0.75);
			else if (secondaryJoystick.getRawButton(2))
				retrieval.set(-0.75);
			else
				retrieval.set(0.0);
			
			if (secondaryJoystick.getRawButton(9))
				cam.relay.set(Value.kOn);
			else
				cam.relay.set(Value.kOff);
			
			/*
			if (driveJoystick.getRawButton(8))
				cam.takePicture(Config.CAM_RGB);*/
		}
	}
}