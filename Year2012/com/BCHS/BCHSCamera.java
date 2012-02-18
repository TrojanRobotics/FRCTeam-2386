ackage Year2012.com.BCHS;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

public class BCHSCamera
{
	static AxisCamera camera;
	ParticleAnalysisReport[] orderedParticles;
	ParticleAnalysisReport first;
	int firstsWidth, pixelCentre, close;
	AnalogChannel ultraSonic;
	ParticleAnalysisReport largestParticle;
	Relay relay;
	
	public BCHSCamera ()
	{
		camera = AxisCamera.getInstance();
		camera.writeBrightness(100);
		relay.setDirection(Relay.Direction.kReverse);
	}
	
	
	
	public void getLargestParticle(int[] imageValues)
	{
		try
		{
			relay.set(Relay.Value.kOn);
			ColorImage colorImage = camera.getImage();
			relay.set(Relay.Value.kOff);
			BinaryImage binaryImage = colorImage.thresholdHSI(imageValues[0],imageValues[1],imageValues[2],imageValues[3],imageValues[4],imageValues[5]);
			colorImage.free();
			
			orderedParticles = binaryImage.getOrderedParticleAnalysisReports();
			binaryImage.free();
			largestParticle = orderedParticles[0];		
		}
		
		catch (AxisCameraException ex)
		{
			ex.printStackTrace();
		}
		catch (NIVisionException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public String leftOrRight(){
		if (largestParticle.center_mass_x < camera.getResolution().width/2 + 10) 
		{
			return "right";
		}
			
		else if (largestParticle.center_mass_x > camera.getResolution().width/2 - 10) 
		{
			return "left";
		} 
		
		else if (largestParticle.center_mass_x >= camera.getResolution().width/2 + 10 || largestParticle.center_mass_x <= camera.getResolution().width/2 - 10) 
		{
			return "centre";
		}
		return "nil, yo.";
	}
}