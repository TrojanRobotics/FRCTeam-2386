package Year2012.com.BCHS;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

public class BCHSBot extends IterativeRobot {

	AxisCamera camera;
	ParticleAnalysisReport[] particles;
	ParticleAnalysisReport first;
	int firstsWidth, pixelCentre, close;
	AnalogChannel ultraSonic;
	RobotDrive drive;
	Joystick driveJoystick;
	double distance;
	double speed;
	double RPM;
	BCHSLauncher launcher; 
	BCHSRetrieval retrieval;
	BCHSBundle motorBundle;

	public void robotInit() 
	{
		camera = AxisCamera.getInstance();
		drive = new RobotDrive(1, 3, 2, 4);
		driveJoystick = new Joystick(1);
		ultraSonic = new AnalogChannel(1);
		launcher = new BCHSLauncher(0, 0, 1, 2);
		retrieval = new BCHSRetrieval(3);
		motorBundle = new BCHSBundle(1,2);
		launcher.encoder.setDistancePerPulse(0.00237);
	}

	public void autonomousPeriodic() 
	{
		particles = BCHSCamera.getParticles(camera, new int[]{0, 0, 0, 0, 0, 0});
		for (int i = 0; i < particles.length; i++) {
			System.out.println("Particle number " + i + " is " + particles[i]);
		}
	}

	public void teleopPeriodic() 
	{
		drive.arcadeDrive(driveJoystick);
		retrieval.liftJag.set(speed);
		launcher.launcherPID.setSetpoint(RPM);
		launcher.motorBundle.set(speed);
	}
}