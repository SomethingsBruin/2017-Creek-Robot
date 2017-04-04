package org.usfirst.frc.team4550.robot;

import CCcanTalon.CCcanTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Servo;

/**
 * The Arm subsystem
 * @author Robotics
 *
 */
public class Arm
{
	private CCcanTalon _arm;
	private Encoder _encoder;
	private static Arm _instance;
	private Servo _servo;
	private Servo _servo2;
	
	private static double _position;

	private Arm()
	{
		_arm = new CCcanTalon ( RobotMap.ARM_PORT, false );
		_servo = new Servo( RobotMap.SERVO_PORT );
		_servo2 = new Servo( RobotMap.SERVO_TOO_PORT);
		_encoder = new Encoder( RobotMap.ENCODER_ARM_A, RobotMap.ENCODER_ARM_B);
	}
	
	//Checks to see if there an existing arm class, and if there is none it creates one
	public static Arm getArm()
	{
		if( _instance == null )
		{
			_instance = new Arm();
		}
		
		return _instance;
	}
	
	//Sets position of servos to p
	public void setPosition( double p )
	{
		_position = p;
		_servo.set( _position );
		_servo2.set( _position );
	}
	
	// "Sets the speed at which the motor turns up and down" - Adam
	public void setSpeed( double speed )
	{
		_arm.set( speed );
	}
	
	// Gets the position of the servos
	public double getPosition()
	{
		return _servo.get();
	}
	
	// Gets the distance that the arm has traveled
	public double getEncoder(){
		return _encoder.getDistance();
	}

	// Resets the encoder
	public void reset(){
		_encoder.reset();
	}
	
	// Uses an encoder to move a distance
	public void moveDistance(double speed, double distance){
		reset();
		while( Math.abs(_encoder.getDistance()) < distance)
		{
			this.setSpeed(speed);
		}
		this.setSpeed(0);
	}
	
}