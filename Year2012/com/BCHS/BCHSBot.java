package Year2012.com.BCHS;

import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;


public class BCHSBot extends IterativeRobot {

	AxisCamera camera = AxisCamera.getInstance();
	ColorImage colorImage;
	BinaryImage binaryImage;
	ParticleAnalysisReport[] particles;
	ParticleAnalysisReport first;
	int firstsWidth;
	int pixelCentre;
	int close = 0;
	Jaguar left = new Jaguar(1);
	Jaguar right = new Jaguar(2);
	AnalogChannel batman = new AnalogChannel(1);

	public void robotInit() {
	}

	public void autonomousPeriodic() {
		try {
			double volts = (batman.getVoltage() * 1000) / 0.9766;

			colorImage = camera.getImage();
			binaryImage = colorImage.thresholdHSI(69, 159, 94, 255, 17, 207);

			colorImage.free();
			binaryImage.convexHull(true);
			binaryImage.removeSmallObjects(true, 0);
			particles = binaryImage.getOrderedParticleAnalysisReports();

			first = particles[0];
			firstsWidth = first.imageWidth;

			pixelCentre = binaryImage.getWidth() / 2;

			double d = 10; //TODO: Implement rangefinder

			double x = pixelCentre - first.center_mass_x;
			int tarPix = 2 * 320 / firstsWidth;

			double a = d / x;

			double theta = MathUtils.atan((tarPix * 0.5) / d);


			if (theta < 0) {
				theta = theta * -1;
			}

			for (int i = 0; i < particles.length; i++) {
				System.out.println("Particle number " + i + " is " + particles[i]);
			}

			System.out.println("\n\n\n");
			System.out.println("The tangent angle is " + theta);

		} catch (AxisCameraException ex) {
			ex.printStackTrace();
		} catch (NIVisionException ex) {
			ex.printStackTrace();
		}
	}

	public void teleopPeriodic() {
	}
}