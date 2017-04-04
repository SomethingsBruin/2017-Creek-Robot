package Chassis;



import org.usfirst.frc.team4550.robot.Arm;
import org.usfirst.frc.team4550.robot.OI;
import org.usfirst.frc.team4550.robot.Robot;
import org.usfirst.frc.team4550.robot.RobotMap;






import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import CCcanTalon.CCcanTalon;

/**
 * Chassis Subsystem
 * @author Robotics
 *
 */
public class Chassis 
{

	private CCcanTalon _leftForward;
	private CCcanTalon _rightForward;
	private CCcanTalon _leftRear;
	private CCcanTalon _rightRear;

	public static OI _oi;

	private Arm _arm = Arm.getArm();

	private AHRS _ahrs; 
	private Encoder _leftEncoder;
	//	private Encoder _rightEncoder;

	private static Chassis _instance;

	private Chassis()
	{
		_leftForward = new CCcanTalon(RobotMap.LEFT_FORWARD_PORT, false);
		_rightForward = new CCcanTalon(RobotMap.RIGHT_FORWARD_PORT, true);
		_leftRear = new CCcanTalon(RobotMap.LEFT_REAR_PORT, false);
		_rightRear = new CCcanTalon(RobotMap.RIGHT_REAR_PORT, true);
		try
		{
			_ahrs = new AHRS(SPI.Port.kMXP);
		}catch( RuntimeException e)
		{
			DriverStation.reportError("Error instantiating navX-MXP:  " + e.getMessage(), true);
		}

		_oi = new OI();
		_leftEncoder = new Encoder(RobotMap.ENCODER_A_LEFT, RobotMap.ENCODER_B_LEFT);
		//		_rightEncoder = new Encoder(RobotMap.ENCODER_A_RIGHT, RobotMap.ENCODER_B_RIGHT);
	}

	public void holoDrive( double fwd, double stf, double rot )
	{
		double LF = fwd + stf + rot;
		double LR = fwd - stf + rot;
		double RF = fwd - stf - rot;
		double RR = fwd + stf - rot;

		double max = Math.abs(LF);

		if( Math.abs(LR) > max ) 
		{
			max = Math.abs( LR );
		}
		if( Math.abs(RF) > max ) 
		{
			max = Math.abs( RF );
		}
		if( Math.abs(RR) > max ) 
		{
			max = Math.abs( RR );
		}

		if( max > 1 )
		{
			LF /= max;
			LR /= max;
			RR /= max;
			RF /= max;
		}
		_leftForward.set(LF);
		_leftRear.set(LR);
		_rightForward.set(RF);
		_rightRear.set(RR );

	}

	public double getAngle()
	{
		return _ahrs.getAngle();
	}

	public double getLeftEncoder()
	{
		return -1*_leftEncoder.getDistance();
	}

	public double getRightEncoder(){
		//		return _rightEncoder.getDistance();
		return -1;
	}

	public double getDisplacementX()
	{
		return -1*_ahrs.getDisplacementX();
	}

	public double getDisplacementY()
	{
		return _ahrs.getDisplacementY();
	}

	public double getDisplacementZ()
	{
		return _ahrs.getDisplacementZ();
	}

	public void reset(){
		_ahrs.reset();
		_ahrs.resetDisplacement();
		_leftEncoder.reset();
//		_rightEncoder.reset();
		System.out.println("Reset Gyro/Encoders");
	}

	public static Chassis getInstance()
	{
		if(_instance == null)
		{
			_instance = new Chassis();
		}
		return _instance;
	}

	public void turn(double speed)
	{
		_leftForward.set(-speed);
		_rightForward.set(speed);
		_leftRear.set(-speed);
		_rightRear.set(speed);
	}

	//Tank drive without side-to-side strafing
	public void tankDrive(double xSpeed, double ySpeed){
		_leftForward.set(ySpeed - xSpeed);
		_rightForward.set(ySpeed + xSpeed);
		_leftRear.set(ySpeed - xSpeed);
		_rightRear.set(ySpeed + xSpeed);
	} 

	//Sets chassis speed to zero
	public void stop()
	{
		_leftForward.set(0);
		_rightForward.set(0);
		_leftRear.set(0);
		_rightRear.set(0);
	}

	//PID Loop turn
	public void turnToAngle( double angle, double speed )
	{
		boolean done = false;
		reset();   

		//Default speed is at 0.7
		long maxTime = 1200;//4 seconds
		double time = 0.0;

		//PID constants
		double Kp = 3.80;
		double Ki = 0.0;
		double Kd = 0.52;

		//PID variables
		double moveSpeed = speed/2;
		double error = 0.0;
		double prevError = 0.0;
		double errorSum = 0.0;

		angle += this.getAngle();


		time = System.currentTimeMillis();

		//PID loop
		while ( !done )
		{                    
			prevError = error;
			System.out.println( " GYRO: " + this.getAngle() );
			error = ( angle - this.getAngle() ) / 100.0;
			errorSum += error;
			errorSum = _oi.normalize( errorSum, -5, 0, 5 );

			//System.out.println( "error: " + error + " errorSum: " + errorSum );

			double p = error * Kp;
			double i = errorSum * Ki;
			double d = (error - prevError) * Kd;       

			this.turn( _oi.normalize(-1*(p + i + d), -moveSpeed, 0, moveSpeed ) ); 

			if ( (Math.abs( errorSum ) < 0.01) || (System.currentTimeMillis() > time+maxTime) )
			{
				System.out.println("Time" + System.currentTimeMillis() );
				System.out.println("Angle " + this.getAngle() );
				done = true;
				break;
			}
		}
		System.out.println("Angle turnt to: " + this.getAngle());
		this.stop(); 
		done = false;
	}
	
	//Sets speed of motors
	public void move(double speed)
	{
		_leftForward.set(speed);
		_rightForward.set(speed);
		_leftRear.set(speed);
		_rightRear.set(speed);
	}
	
	//Moves to a distance
	public void mushToDistance( double dist, double speed )
	{	
		reset();
		double distMeters = dist;
		double initAngle = this.getAngle();


		while( Math.abs(_leftEncoder.getDistance()) < distMeters /*&& Math.abs(_rightEncoder.getDistance()) < distMeters*/)
		{
			System.out.println( "Left Encoder" + _leftEncoder.getDistance());
			System.out.println(" Gyro:  " + this.getAngle());
			tankDrive((this.getAngle() - initAngle)/100, _oi.normalize(speed, -.9, 0, .9));
		}
		this.stop();
		System.out.println("Distance-Left (Auto):  " + _leftEncoder.getDistance());
		//		System.out.println("Distance-Right (Auto): " + _rightEncoder.getDistance());
	}

}	

