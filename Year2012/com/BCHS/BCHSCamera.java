package Year2012.com.BCHS;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

/**
 *
 * @author mattbettinson
 * mattbettinson@me.com
 */
public class BCHSCamera {
	public static ParticleAnalysisReport[] getParticles(AxisCamera camera, int[] imageValues)
	{
		try {
			ColorImage colorImage = camera.getImage();
			BinaryImage binaryImage = colorImage.thresholdHSI(imageValues[0],imageValues[1],imageValues[2],imageValues[3],imageValues[4],imageValues[5]);
			ParticleAnalysisReport[] orderedParticles = binaryImage.getOrderedParticleAnalysisReports();
			colorImage.free();
			binaryImage.free();
			
			return orderedParticles;

		} catch (AxisCameraException ex) {
			ex.printStackTrace();
			return null;
		} catch (NIVisionException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
