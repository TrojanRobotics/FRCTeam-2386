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

public class BCHSBot extends IterativeRobot
{	
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
        double leftAngle, rightAngle, headAngle, rightLegAngle, leftLegAngle, rightLegYZ, leftLegYZ, EtoWLeft, WtoHLeft, leftAngleYZ, OriginGetY, OriginGetX, TheArrow, ForOrBackRight, ForOrBackLeft, HR, WR, HL, WL, rar, ror,MaxForRight, MaxForLeft, MaxRevRight, MaxRevLeft, ZeroRight, ZeroLeft,inR, inL, Nothing, Happened,Fail, Ure, RKneeAnkleYZ,LKneeAnkleYZ;
	
	DriverStation ds = DriverStation.getInstance();
	
	Encoder leftEncoder, rightEncoder;
	PIDController leftPID, rightPID;
	
	double kp, ki, kd;
	
	boolean run = true;
	
	
	public void robotInit() 
	{
		if (ds.getDigitalIn(1))
			camera = AxisCamera.getInstance();

		leftSide = new BCHSBundle(1, 2);
		rightSide = new BCHSBundle(3,4);
		drive = new RobotDrive(leftSide, rightSide);
		driveJoystick = new Joystick(1);
		secondaryJoystick = new Joystick(2);
		ultraSonic = new AnalogChannel(1);

		launcher = new BCHSLauncher(5,6,7,8);
		retrieval = new BCHSRetrieval(5);
		launcher.encoder.setDistancePerPulse(0.0237);
		
		hockeySticks = new BCHSHockey(6,8,9);
                
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

	public void autonomousPeriodic() 
	{
		particles = BCHSCamera.getParticles(camera, new int[]{0, 0, 0, 0, 0, 0});
		for (int i = 0; i < particles.length; i++) {
			System.out.println("Particle number " + i + " is " + particles[i]);
		}
                
                //kinect Code
                double leftAxis = 0.0;
                double rightAxis = 0.0;
                
                if (kinect.getSkeleton().GetTrackState() == Skeleton.tTrackState.kTracked) {
                        Ure = kinect.getSkeleton().GetSpine().getY();
                        Fail = kinect.getSkeleton().GetShoulderCenter().getY();
                        OriginGetY = ((Fail+Ure)/2);
                        OriginGetX = kinect.getSkeleton().GetShoulderCenter().getX();

                        /* Determine angle of each arm and map to range -1,1 */
                        leftAngle = AngleXY(kinect.getSkeleton().GetElbowLeft(), kinect.getSkeleton().GetWristLeft(), true);
                        rightAngle = AngleXY(kinect.getSkeleton().GetShoulderRight(), kinect.getSkeleton().GetWristRight(), false);
                        EtoWLeft = AngleXY(kinect.getSkeleton().GetElbowLeft(), kinect.getSkeleton().GetWristLeft(), true);
                        WtoHLeft = AngleXY(kinect.getSkeleton().GetWristLeft(), kinect.getSkeleton().GetHandLeft(), true);
                        leftAngleYZ = AngleYZ(kinect.getSkeleton().GetShoulderLeft(),kinect.getSkeleton().GetWristLeft(),true);
                        leftAxis = CoerceToRange(leftAngle, -70, 70, -1, 1);
                        rightAxis = CoerceToRange(rightAngle, -70, 70, -1, 1);

                        /* Determine the head angle and use it to set the Head buttons */
                        headAngle = AngleXY(kinect.getSkeleton().GetShoulderCenter(), kinect.getSkeleton().GetHead(), false);

                        /* Calculate the leg angles in the XY plane and use them to set the Leg Out buttons */
                        leftLegAngle = AngleXY(kinect.getSkeleton().GetHipLeft(), kinect.getSkeleton().GetAnkleLeft(), true);
                        rightLegAngle = AngleXY(kinect.getSkeleton().GetHipRight(), kinect.getSkeleton().GetAnkleRight(), false);

                        /* Calculate the leg angle in the YZ plane and use them to set the Leg Forward and Leg Back buttons */
                        rightLegYZ = AngleYZ(kinect.getSkeleton().GetHipRight(), kinect.getSkeleton().GetKneeRight(), false);
                        RKneeAnkleYZ = AngleYZ(kinect.getSkeleton().GetKneeRight(), kinect.getSkeleton().GetAnkleRight(), false);
                        leftLegYZ = AngleYZ(kinect.getSkeleton().GetHipLeft(), kinect.getSkeleton().GetKneeLeft(), false);
                        LKneeAnkleYZ = AngleYZ(kinect.getSkeleton().GetKneeLeft(), kinect.getSkeleton().GetAnkleLeft(), false);
                        
                        //throttle
                        Nothing = (kinect.getSkeleton().GetHipRight().getZ());
                        Happened =(kinect.getSkeleton().GetWristRight().getZ());
                        ForOrBackRight = Nothing-Happened;


                        if (rightLegYZ < -110.0 || leftLegYZ < -110.0){  //This is an emergancy brake
                            setSpeed(0.0,0.0);
                            if(rightLegYZ < -110.0){
                                launcher.set(0.7);
                            }
                            /*else if (leftLegYZ < -110.0){
                                hockey.set();
                            }*/
                            if (RKneeAnkleYZ < -130.0){
                                retrieval.set(0.75);
                                //lcd.println(DriverStationLCD.Line.kMain6, 1, "Falcon Kick ");  
                                //lcd.updateLCD();                        
                            }
                            else{
                                retrieval.set(0.0);
                            }                  
                        }
                        else{
                            if (leftAngle < 180.0 && leftAngle > 0.0){
                                if(ForOrBackRight > 0.30){
                                    ForOrBackRight = 0.30;
                                }
                                else if (ForOrBackRight < -0.30){
                                    ForOrBackRight = -0.30;
                                }
                                ForOrBackRight = ForOrBackRight/0.3;
                                if(leftAngle == 90.0){
                                    setSpeed(ForOrBackRight, ForOrBackRight);
                                }                   
                                else if(leftAngle > 90.0){
                                    if (leftAngle > 170.0){
                                        leftAngle = 170.0;
                                    }
                                    //Gleft =(100/(91-leftAngleYZ)*(1/90)) ;
                                    rar = ForOrBackRight*((170.0-leftAngle)/80.0);
                                    setSpeed(rar ,ForOrBackRight);
                                }
                                else{
                                    if(leftAngle < 10.0){
                                        leftAngle = 10.0;
                                    }
                                    ror = ForOrBackRight*((leftAngle-10.0)/80.0);
                                    setSpeed(ForOrBackRight, ror);
                                }
                            }
                            launcher.set(0.0);
                            retrieval.set(0.0);
                    }
                }
               // Timer.delay(.01);   /* Delay 10ms to reduce proceessing load*/
                
                
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
		
		/*
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
		 * */
	}
        
    /**
     * This method returns the angle (in degrees) of the vector pointing from Origin to Measured
     * projected to the XY plane. If the mirrored parameter is true the vector is flipped about the Y-axis.
     * Mirroring is used to avoid the region where the atan2 function is discontinuous
     * @param origin The Skeleton Joint to use as the origin point
     * @param measured The Skeleton Joint to use as the endpoint of the vector
     * @param mirrored Whether to mirror the X coordinate of the joint about the Y-axis
     * @return The angle in degrees
     */ 
    public double AngleXY(Skeleton.Joint origin, Skeleton.Joint measured, boolean mirrored){
        return Math.toDegrees(MathUtils.atan2(measured.getY()- origin.getY(),
                (mirrored) ? (origin.getX() - measured.getX()) : (measured.getX() - origin.getX())));
    }
    

     /**
     * This method returns the angle (in degrees) of the vector pointing from Origin to Measured
     * projected to the YZ plane. If the mirrored parameter is true the vector is flipped about the Y-axis.
     * Mirroring is used to avoid the region where the atan2 function is discontinuous
     * @param origin The Skeleton Joint to use as the origin point
     * @param measured The Skeleton Joint to use as the endpoint of the vector
     * @param mirrored Whether to mirror the Z coordinate of the joint about the Y-axis
     * @return The angle in degrees
     */
    
    public double AngleXY2(double OriginX, double OriginY, Skeleton.Joint measured, boolean mirrored){
        return Math.toDegrees(MathUtils.atan2(measured.getY()- OriginY,
                (mirrored) ? (OriginX - measured.getX()) : (measured.getX() - OriginX)));
    }
    
    /*Was used to measure an angle(in degrees) thats not necessarily a joint in kinect*/
      
    public double AngleYZ(Skeleton.Joint origin, Skeleton.Joint measured, boolean mirrored){
        return Math.toDegrees(MathUtils.atan2(measured.getY()- origin.getY(),
                (mirrored) ? (origin.getZ() - measured.getZ()) : (measured.getZ() - origin.getZ())));
    }

    /**
     * This method checks if two Joints have z-coordinates within a given tolerance
     * @param origin
     * @param measured
     * @param tolerance
     * @return True if the z-coordinates are within tolerance
     */
    public boolean InSameZPlane(Skeleton.Joint origin, Skeleton.Joint measured, double tolerance)
        {
            return Math.abs(measured.getZ() - origin.getZ()) < tolerance;
        }

    /**
     * This method takes an input, an input range, and an output range,
     * and uses them to scale and constrain the input to the output range
     * @param input The input value to be manipulated
     * @param inputMin The minimum value of the input range
     * @param inputMax The maximum value of the input range
     * @param outputMin The minimum value of the output range
     * @param outputMax The maximum value of the output range
     * @return The output value scaled and constrained to the output range
     */   
    public double CoerceToRange(double input, double inputMin, double inputMax, double outputMin, double outputMax)
        {
            /* Determine the center of the input range and output range */
            double inputCenter = Math.abs(inputMax - inputMin) / 2 + inputMin;
            double outputCenter = Math.abs(outputMax - outputMin) / 2 + outputMin;

            /* Scale the input range to the output range */
            double scale = (outputMax - outputMin) / (inputMax - inputMin);

            /* Apply the transformation */
            double result = (input + -inputCenter) * scale + outputCenter;

            /* Constrain to the output range */
            return Math.max(Math.min(result, outputMax), outputMin);
        }

    /*
     * Sets speed to Jaguars 1 through -1 only
     */
    public void setSpeed(double speedleft, double speedright){
        leftSide.set(speedleft*0.75);
        rightSide.set(-speedright*0.75);
       
    }
}