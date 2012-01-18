/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nickmcguire;

import com.sun.cldc.jna.*;
import com.sun.squawk.Address;
import edu.wpi.first.wpilibj.image.NIVisionException;

/**
 *
 * @author Nick
 */
public class NMVision
{
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
	
	//MOTHER OF GOD
	private static final BlockingFunction imaqConvexHullFn = NativeLibrary.getDefaultInstance().getBlockingFunction("imaqConvexHull");
    static { imaqConvexHullFn.setTaskExecutor(taskExecutor); }
    
    public static void convexHull(Pointer source, Pointer dest, int connectivity8)  throws NIVisionException
	{
        assertCleanStatus(imaqConvexHullFn.call3(source.address().toUWord().toPrimitive(), dest.address().toUWord().toPrimitive(), connectivity8));
    }
	
	//***** LOOK INTO imaqMorphology(Image* dest, Image* source, MorphologyMethod method, const StructuringElement* structuringElement); ******//
	
	private static final BlockingFunction imaqMorphologyFn = NativeLibrary.getDefaultInstance().getBlockingFunction("imaqMorphology");
    static { imaqMorphologyFn.setTaskExecutor(taskExecutor); }
    
    public static void imaqMorpholgy(Pointer source, Pointer dest, int method)  throws NIVisionException
	{
        assertCleanStatus(imaqMorphologyFn.call4(source.address().toUWord().toPrimitive(), dest.address().toUWord().toPrimitive(), method, 0));
    }
}
