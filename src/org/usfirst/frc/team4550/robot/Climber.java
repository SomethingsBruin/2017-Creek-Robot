package org.usfirst.frc.team4550.robot;

import CCcanTalon.CCcanTalon;
/**
 * The Climber Subsystem
 * @author Robotics
 *
 */
public class Climber
{
	
	private static Climber _instance;
	private CCcanTalon _climber;
	
	private Climber()
	{
		_climber = new CCcanTalon( RobotMap.CLIMBER_PORT, false );
	}
	
	// Returns the current instance of the climber
	public static Climber getClimber()
	{
		if( _instance == null )
		{
			_instance = new Climber();
		}
		return _instance;
	}
	
	// Sets the climber talon speed
	public void setSpeed( double speed )
	{
		_climber.set( speed );
	}
	
}