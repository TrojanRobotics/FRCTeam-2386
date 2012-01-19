package com.nickmcguire;

import com.sun.cldc.jna.*;
import edu.wpi.first.wpilibj.image.NIVisionException;

/**
 * This file includes some hidden functionality of the NIVision library
 *
 * @author Nick McGuire
 * @version 1.0
 */
public class NMVision
{
	/**
	 * Enumerations representing the possible methods to invoke
	 */
	public static class MorphologyOptions
	{
		public final int value;
		
		/**
		 * For full definition of what these methods mean look at, "NI Vision for LabWindows/CVI Function Reference"
		 */
		
		public static final MorphologyOptions IMAQ_AUTOM = new MorphologyOptions(0);
		
		public static final MorphologyOptions IMAQ_CLOSE = new MorphologyOptions(1);
		
		public static final MorphologyOptions IMAQ_DILATE = new MorphologyOptions(2);
		
		public static final MorphologyOptions IMAQ_ERODE = new MorphologyOptions(3);
		
		public static final MorphologyOptions IMAQ_GRADIENT = new MorphologyOptions(4);
		
		public static final MorphologyOptions IMAQ_GRADIENTOUT = new MorphologyOptions(5);
		
		public static final MorphologyOptions IMAQ_GRADIENTIN = new MorphologyOptions(6);
		
		public static final MorphologyOptions IMAQ_HITMISS = new MorphologyOptions(7);
		
		public static final MorphologyOptions IMAQ_OPEN = new MorphologyOptions(8);
		
		public static final MorphologyOptions IMAQ_PCLOSE = new MorphologyOptions(9);
		
		public static final MorphologyOptions IMAQ_POPEN = new MorphologyOptions(10);
		
		public static final MorphologyOptions IMAQ_THICK = new MorphologyOptions(11);
		
		public static final MorphologyOptions IMAQ_THIN = new MorphologyOptions(12);
		
		public MorphologyOptions(int value)
		{
			this.value = value;
		}
	}
	
	private static final TaskExecutor taskExecutor = new TaskExecutor("nivision task");
	
	//============================================================================
    //  Error Management functions
    //============================================================================
    private static final BlockingFunction imaqGetLastErrorFn = NativeLibrary.getDefaultInstance().getBlockingFunction("imaqGetLastError");
    static { imaqGetLastErrorFn.setTaskExecutor(taskExecutor);  }

    public static int getLastError()
	{
        return imaqGetLastErrorFn.call0();
    }
	
    protected static void assertCleanStatus (int code) throws NIVisionException
	{
        if (code == 0) 
            throw new NIVisionException(imaqGetLastErrorFn.call0());
    }
	
	//ConvexHull setup
	private static final BlockingFunction imaqConvexHullFn = NativeLibrary.getDefaultInstance().getBlockingFunction("imaqConvexHull");
    static { imaqConvexHullFn.setTaskExecutor(taskExecutor); }
    
	/**
	 * A wrapper method to interface with NIVision's convexHull function
	 * 
	 * @param source A pointer to the source image
	 * @param dest A pointer to the destination image
	 * @param connectivity8 This changes how the function considers pixels, refer to Chapter 9 of "NI Vision Concepts Manual" for details
	 * @throws NIVisionException 
	 */
    public static void convexHull(Pointer source, Pointer dest, int connectivity8)  throws NIVisionException
	{
        assertCleanStatus(imaqConvexHullFn.call3(source.address().toUWord().toPrimitive(), dest.address().toUWord().toPrimitive(), connectivity8));
    }
	
	
	
	//Morphology setup
	private static final BlockingFunction imaqMorphologyFn = NativeLibrary.getDefaultInstance().getBlockingFunction("imaqMorphology");
    static { imaqMorphologyFn.setTaskExecutor(taskExecutor); }
    
	/**
	 * A wrapper method to interface with NIVision's morphology function
	 * 
	 * TODO:
	 * I am currently looking into how to make a Structuring element. But as of writing you cannot make your own Structuring element.
	 * 
	 * @param source A pointer to the source image
	 * @param dest A pointer to the destination image
	 * @param method The method you want to actually use on the image
	 * @throws NIVisionException 
	 */
    public static void imaqMorpholgy(Pointer source, Pointer dest, MorphologyOptions method)  throws NIVisionException
	{
        assertCleanStatus(imaqMorphologyFn.call4(source.address().toUWord().toPrimitive(), dest.address().toUWord().toPrimitive(), method.value, 0));
    }
}
