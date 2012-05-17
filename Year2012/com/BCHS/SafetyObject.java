/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Year2012.com.BCHS;

import edu.wpi.first.wpilibj.SpeedController;

/**
 *
 * @author laxman
 */
abstract class SafetyObject 
{
	SpeedController controller;
	
    public abstract void stop();
	
	public abstract boolean isSafe();
	
    public abstract String getDescription();
}

