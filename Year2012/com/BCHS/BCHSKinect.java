package com.BCHS;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.Kinect;
import edu.wpi.first.wpilibj.Skeleton;

public class BCHSKinect 
{

    Bundle leftSide, rightSide;
    Launcher launcher;
    Retrieval retrieval;
    Hockey hockeySticks;
    double leftAxis, rightAxis, leftAngle, rightAngle, headAngle, rightLegAngle, leftLegAngle, rightLegYZ, leftLegYZ, EtoWLeft, WtoHLeft, leftAngleYZ, OriginGetY, OriginGetX, TheArrow, ForOrBackRight, ForOrBackLeft, HR, WR, HL, WL, rar, ror, MaxForRight, MaxForLeft, MaxRevRight, MaxRevLeft, ZeroRight, ZeroLeft, inR, inL, Nothing, Happened, Fail, Ure, RKneeAnkleYZ, LKneeAnkleYZ;
	Kinect kinect;

    BCHSKinect(Bundle leftSide, Bundle rightSide, Launcher launcher, Retrieval retrieval, Hockey hockeySticks) 
	{
        leftAxis = 0.0;
		rightAxis = 0.0;
		this.leftSide = leftSide;
        this.rightSide = rightSide;
        this.launcher = launcher;
        this.retrieval = retrieval;
        this.hockeySticks = hockeySticks;
		kinect = Kinect.getInstance();
    }

    public void kinectDrive() 
	{


        if (kinect.getSkeleton().GetTrackState() == Skeleton.tTrackState.kTracked) 
		{
            Ure = kinect.getSkeleton().GetSpine().getY();
            Fail = kinect.getSkeleton().GetShoulderCenter().getY();
            OriginGetY = ((Fail + Ure) / 2);
            OriginGetX = kinect.getSkeleton().GetShoulderCenter().getX();

            /*
             * Determine angle of each arm and map to range -1,1
             */
            leftAngle = AngleXY(kinect.getSkeleton().GetElbowLeft(), kinect.getSkeleton().GetWristLeft(), true);
            rightAngle = AngleXY(kinect.getSkeleton().GetShoulderRight(), kinect.getSkeleton().GetWristRight(), false);
            EtoWLeft = AngleXY(kinect.getSkeleton().GetElbowLeft(), kinect.getSkeleton().GetWristLeft(), true);
            WtoHLeft = AngleXY(kinect.getSkeleton().GetWristLeft(), kinect.getSkeleton().GetHandLeft(), true);
            leftAngleYZ = AngleYZ(kinect.getSkeleton().GetShoulderLeft(), kinect.getSkeleton().GetWristLeft(), true);
            leftAxis = CoerceToRange(leftAngle, -70, 70, -1, 1);
            rightAxis = CoerceToRange(rightAngle, -70, 70, -1, 1);

            /*
             * Determine the head angle and use it to set the Head buttons
             */
            headAngle = AngleXY(kinect.getSkeleton().GetShoulderCenter(), kinect.getSkeleton().GetHead(), false);

            /*
             * Calculate the leg angles in the XY plane and use them to set the
             * Leg Out buttons
             */
            leftLegAngle = AngleXY(kinect.getSkeleton().GetHipLeft(), kinect.getSkeleton().GetAnkleLeft(), true);
            rightLegAngle = AngleXY(kinect.getSkeleton().GetHipRight(), kinect.getSkeleton().GetAnkleRight(), false);

            /*
             * Calculate the leg angle in the YZ plane and use them to set the
             * Leg Forward and Leg Back buttons
             */
            rightLegYZ = AngleYZ(kinect.getSkeleton().GetHipRight(), kinect.getSkeleton().GetKneeRight(), false);
            RKneeAnkleYZ = AngleYZ(kinect.getSkeleton().GetKneeRight(), kinect.getSkeleton().GetAnkleRight(), false);
            leftLegYZ = AngleYZ(kinect.getSkeleton().GetHipLeft(), kinect.getSkeleton().GetKneeLeft(), false);
            LKneeAnkleYZ = AngleYZ(kinect.getSkeleton().GetKneeLeft(), kinect.getSkeleton().GetAnkleLeft(), false);

            //throttle
            Nothing = (kinect.getSkeleton().GetHipRight().getZ());
            Happened = (kinect.getSkeleton().GetWristRight().getZ());
            ForOrBackRight = Nothing - Happened;


            if (rightLegYZ < -110.0 || leftLegYZ < -110.0) //This is an emergancy brake
            {
                setSpeed(0.0, 0.0);

                if (rightLegYZ < -110.0) {
                    launcher.set(0.7);
                } else if (leftLegYZ < -110.0) {
                    hockeySticks.set(0.5);
                }
                if (RKneeAnkleYZ < -130.0) {
                    retrieval.set(0.75);
                } else {
                    retrieval.set(0.0);
                }
                if (LKneeAnkleYZ < -130.0) {
                    hockeySticks.set(-0.5);
                }

            } 
			else 
			{
                if (leftAngle < 180.0 && leftAngle > 0.0) {
                    if (ForOrBackRight > 0.30) {
                        ForOrBackRight = 0.30;
                    } else if (ForOrBackRight < -0.30) {
                        ForOrBackRight = -0.30;
                    }

                    ForOrBackRight = ForOrBackRight / 0.3;

                    if (leftAngle == 90.0) {
                        setSpeed(ForOrBackRight, ForOrBackRight);
                    } else if (leftAngle > 90.0) {
                        if (leftAngle > 170.0) {
                            leftAngle = 170.0;
                        }

                        rar = ForOrBackRight * ((170.0 - leftAngle) / 80.0);
                        setSpeed(rar, ForOrBackRight);
                    } else {
                        if (leftAngle < 10.0) {
                            leftAngle = 10.0;
                        }

                        ror = ForOrBackRight * ((leftAngle - 10.0) / 80.0);
                        setSpeed(ForOrBackRight, ror);
                    }
                }

                launcher.set(0.0);
                retrieval.set(0.0);
            }
        }
    }

    /**
     * This method returns the angle (in degrees) of the vector pointing from
     * Origin to Measured projected to the XY plane. If the mirrored parameter
     * is true the vector is flipped about the Y-axis. Mirroring is used to
     * avoid the region where the atan2 function is discontinuous
     *
     * @param origin The Skeleton Joint to use as the origin point
     * @param measured The Skeleton Joint to use as the endpoint of the vector
     * @param mirrored Whether to mirror the X coordinate of the joint about the
     * Y-axis
     * @return The angle in degrees
     */
    public double AngleXY(Skeleton.Joint origin, Skeleton.Joint measured, boolean mirrored) 
	{
        return Math.toDegrees(MathUtils.atan2(measured.getY() - origin.getY(),
                (mirrored) ? (origin.getX() - measured.getX()) : (measured.getX() - origin.getX())));
    }

    /**
     * This method returns the angle (in degrees) of the vector pointing from
     * Origin to Measured projected to the YZ plane. If the mirrored parameter
     * is true the vector is flipped about the Y-axis. Mirroring is used to
     * avoid the region where the atan2 function is discontinuous
     *
     * @param origin The Skeleton Joint to use as the origin point
     * @param measured The Skeleton Joint to use as the endpoint of the vector
     * @param mirrored Whether to mirror the Z coordinate of the joint about the
     * Y-axis
     * @return The angle in degrees
     */
    public double AngleXY2(double OriginX, double OriginY, Skeleton.Joint measured, boolean mirrored) 
	{
        return Math.toDegrees(MathUtils.atan2(measured.getY() - OriginY,
                (mirrored) ? (OriginX - measured.getX()) : (measured.getX() - OriginX)));
    }

    /*
     * Was used to measure an angle(in degrees) thats not necessarily a joint in
     * kinect
     */
    public double AngleYZ(Skeleton.Joint origin, Skeleton.Joint measured, boolean mirrored) 
	{
        return Math.toDegrees(MathUtils.atan2(measured.getY() - origin.getY(),
                (mirrored) ? (origin.getZ() - measured.getZ()) : (measured.getZ() - origin.getZ())));
    }

    /**
     * This method checks if two Joints have z-coordinates within a given
     * tolerance
     *
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
     * This method takes an input, an input range, and an output range, and uses
     * them to scale and constrain the input to the output range
     *
     * @param input The input value to be manipulated
     * @param inputMin The minimum value of the input range
     * @param inputMax The maximum value of the input range
     * @param outputMin The minimum value of the output range
     * @param outputMax The maximum value of the output range
     * @return The output value scaled and constrained to the output range
     */
    public double CoerceToRange(double input, double inputMin, double inputMax, double outputMin, double outputMax) 
	{
        /*
         * Determine the center of the input range and output range
         */
        double inputCenter = Math.abs(inputMax - inputMin) / 2 + inputMin;
        double outputCenter = Math.abs(outputMax - outputMin) / 2 + outputMin;

        /*
         * Scale the input range to the output range
         */
        double scale = (outputMax - outputMin) / (inputMax - inputMin);

        /*
         * Apply the transformation
         */
        double result = (input + -inputCenter) * scale + outputCenter;

        /*
         * Constrain to the output range
         */
        return Math.max(Math.min(result, outputMax), outputMin);
    }

    /*
     * Sets speed to Jaguars 1 through -1 only
     */
    public void setSpeed(double speedleft, double speedright) 
	{
        leftSide.set(speedleft * 0.75);
        rightSide.set(-speedright * 0.75);
    }
}
