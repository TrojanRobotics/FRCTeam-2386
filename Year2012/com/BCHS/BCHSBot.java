package Year2012.com.BCHS;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;
import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.Kinect;
import edu.wpi.first.wpilibj.Skeleton;
import edu.wpi.first.wpilibj.Timer;

public class BCHSBot extends IterativeRobot {
	//camera declrations
    
	AxisCamera camera;
	ParticleAnalysisReport[] particles;
	ParticleAnalysisReport first;
	int firstsWidth, pixelCentre, close;
	AnalogChannel ultraSonic;
	// driving decleration 
	RobotDrive drive;
	BCHSBundle leftSide, rightSide;
	Joystick driveJoystick, secondaryJoystick;
	// functional mechanisms
	BCHSLauncher launcher;
	BCHSRetrieval retrieval;
	BCHSHockey hockeySticks;
	//kinect
	Kinect kinect;
	double leftAngle, rightAngle, headAngle, rightLegAngle, leftLegAngle, rightLegYZ, leftLegYZ, EtoWLeft, WtoHLeft, leftAngleYZ, OriginGetY, OriginGetX, TheArrow, ForOrBackRight, ForOrBackLeft, HR, WR, HL, WL, rar, ror, MaxForRight, MaxForLeft, MaxRevRight, MaxRevLeft, ZeroRight, ZeroLeft, inR, inL, Nothing, Happened, Fail, Ure, RKneeAnkleYZ, LKneeAnkleYZ;
        BCHSKinect xKinect;
        
        DriverStation ds = DriverStation.getInstance();
	Encoder leftEncoder, rightEncoder;
	PIDController leftPID, rightPID;
	double kp, ki, kd;
	boolean run = true;

	public void robotInit() {
		if (ds.getDigitalIn(1)) {
			camera = AxisCamera.getInstance();
		}

		leftSide = new BCHSBundle(1, 2);
		rightSide = new BCHSBundle(3, 4);
                
                
               
               
                xKinect = new BCHSKinect(leftSide, rightSide, launcher, retrieval, hockeySticks);
                
		drive = new RobotDrive(leftSide, rightSide);
		driveJoystick = new Joystick(1);
		secondaryJoystick = new Joystick(2);
		ultraSonic = new AnalogChannel(1);

		launcher = new BCHSLauncher(5, 6, 7, 8);
		retrieval = new BCHSRetrieval(5);
		launcher.encoder.setDistancePerPulse(0.0237);

		hockeySticks = new BCHSHockey(6, 8, 9);

		kinect = Kinect.getInstance();

		kp = 0.3;
		ki = 0.0;
		kd = 0.0;

		leftEncoder = new Encoder(3, 4);
		rightEncoder = new Encoder(1, 2);
		leftEncoder.setDistancePerPulse(0.0237);
		rightEncoder.setDistancePerPulse(0.0237);
		leftEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
		rightEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kDistance);
		rightEncoder.start();
		leftEncoder.start();

		leftPID = new PIDController(kp, ki, kd, leftEncoder, leftSide);
		rightPID = new PIDController(kp, ki, kd, rightEncoder, rightSide);
		leftPID.enable();
		rightPID.enable();
	}

	public void autonomousPeriodic() {
		

		if (ds.getDigitalIn(1))
		{
			particles = BCHSCamera.getParticles(camera, new int[]{0, 0, 0, 0, 0, 0});
			for (int i = 0; i < particles.length; i++) {
				System.out.println("Particle number " + i + " is " + particles[i]);
			}
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
			leftPID.disable();
			rightPID.disable();
			retrieval.stop();
		}
		else if (ds.getDigitalIn(3))
		{
			//kinect Code
			xKinect.kinectDrive(kinect);
		}
	}

	public void teleopPeriodic()
	{
		//drive.arcadeDrive(driveJoystick);

		if (run)
		{
			leftPID.setSetpoint(12);
			rightPID.setSetpoint(12);
			run = false;
		}
		else
		{
			if (driveJoystick.getRawButton(11)) 
				hockeySticks.set(0.5); 
			else if (driveJoystick.getRawButton(10)) 
				hockeySticks.set(-0.5); 
			else
				hockeySticks.set(0);
			
			//bchsLauncher 
			if (driveJoystick.getTrigger()) 
				launcher.set(-0.7);
			else
				launcher.set(0.0);
			//bchsRetrieval 
			
			if (driveJoystick.getRawButton(3))
				retrieval.set(-0.5); 
			else if (driveJoystick.getRawButton(2))
				retrieval.set(0.5); 
			else 
				retrieval.set(0.0);
		}
	}
}