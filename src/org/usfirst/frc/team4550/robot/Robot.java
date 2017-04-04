package org.usfirst.frc.team4550.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import Chassis.Chassis;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	// Mechanisms
	public static OI _oi;
	private static Chassis _chassis;
	private Arm _arm;
	private Climber _climber;

	private Camera _camera;
	
	// Autonomous Variables
	Command autonomousCommand;
	private SendableChooser<Integer> _chooser;
	private int _autoSelected;
	private boolean _autonomousOver;
	
	//When mode is false, you can move the arm
	//When mode is true, you can move the climber - switch with square
	private boolean _mode;
	private boolean _unlocked;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit()
	{	
		//Autonomous Chooser Init
		_chooser = new SendableChooser<Integer>();

		_chooser.addObject("Base Line", 0);
		_chooser.addObject("Mid Gear", 1);
		_chooser.addObject("Side Gear Left", 2);
		_chooser.addObject("Side Gear Right", 3);
		_chooser.addObject("New Test Auto - aka Sahil's Doom", 4);
		
		SmartDashboard.putData("Auto Mode", _chooser);
		
		// Initiate Mechanisms
		_chassis = Chassis.getInstance();
		_arm = Arm.getArm();
		_climber = Climber.getClimber();
		_oi = new OI();
		
		// Reset Robot
		_chassis.reset();
		_arm.reset();
		
		// Start-up Booleans
		_autonomousOver = false;
		_unlocked = false;
		
		// Camera try-catch
		try{
			_camera = Camera.getInstance();
			_camera.startFeed();
		}catch(Exception e){
			System.out.println("Error creating camera");
		}
		
		System.out.println("Robot initialized");
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit()
	{

	}

	@Override
	public void disabledPeriodic()
	{
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit()
	{
		_autoSelected = (int) _chooser.getSelected();
		_autonomousOver = false;
		_chassis.reset();
		_arm.reset();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic()
	{
		double carpetModifier = 1.16;
		if(!_autonomousOver)
		{
			// This switch controls what each autonomous command does
			// Each case corresponds to a different autonomous choice
			System.out.println("Auto Start");
			
			switch(_autoSelected)
			{
			// "Base Line"
			case 0:
				_chassis.tankDrive(0, .5);
				Timer.delay(2.25);
				_chassis.stop();
				break;
		
			// "Mid Gear"
			case 1:
				//Test end slow
				_chassis.mushToDistance(675*carpetModifier, .35);
				Timer.delay(0.2);
				_arm.moveDistance(-0.2, 40);

				_arm.setPosition(1.6);
				Timer.delay(2);
				
				_chassis.mushToDistance(100, -0.3);
				_arm.moveDistance(0.2, 40);
				break;
				
			// "Side Gear Left"
			case 2:
				//test Mush
				_chassis.mushToDistance(830*carpetModifier, .45);
				Timer.delay(.2);
				_chassis.turnToAngle(61.5, .75);
				_chassis.mushToDistance(250, .45);
				Timer.delay(0.2);
				_arm.moveDistance(-0.2, 40);
				_arm.setPosition(1.6);
				Timer.delay(2);
				_chassis.mushToDistance(100, -0.3);
				_arm.moveDistance(0.2, 40);
				break;
				
			// "Side Gear Right"
			case 3:
				//test Mush
				_chassis.mushToDistance(830*carpetModifier, .45);
				Timer.delay(.2);
				_chassis.turnToAngle(-61.5, .75);
				_chassis.mushToDistance(250, .45);
				Timer.delay(0.2);
				_arm.moveDistance(-0.2, 40);
				_arm.setPosition(1.6);
				Timer.delay(2);
				_chassis.mushToDistance(100, -.3);	
				_arm.moveDistance(0.2, 40);
				break;
			
			//Sahil's Doom
			case 4:
//				_chassis.testEndSlowMushToDistance(650, .35);
				break;
			default:
				break;
			}
			_autonomousOver = true;
			System.out.println("Autonomous Ended");	
		}
	}
	

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		// It is dead now because we control it with an integer
		 if (autonomousCommand != null){
			 System.out.println("deadzo pt. 2");
			 autonomousCommand.cancel();
		 }
		 
		_chassis.reset();
		_arm.reset();
		_autonomousOver = true;
		_mode = false;
		_unlocked = false;
	}
	
	

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		//Debugging Prints
		System.out.println("Gyro: " + _chassis.getAngle());
		System.out.println();
		System.out.println("Arm Encoder:  " + _arm.getEncoder());
		System.out.println("Encoder-Left: " + _chassis.getLeftEncoder());
		System.out.println("Encoder-Right: " + _chassis.getRightEncoder());
		
		//Open arm - R1
		if(_oi.getR1())
		{
			_arm.setPosition(1.6);
		}
		//Close arm - L1
		if(_oi.getL1())
		{
			_arm.setPosition(.2);
		}
		//Climber - Triangle
		if(_oi.getTriangleButton())
		{
			_mode = true;
		}
		//Claw - X
		if(_oi.getXButton())
		{
			_mode = false;
		}

		_chassis.holoDrive( _oi.exp(_oi.normalize( _oi.getRJoystickYAxis( ), -1, RobotMap.RIGHT_Y_ZERO, 1 ), 3) , _oi.exp( _oi.normalize( _oi.getRJoystickXAxis( ), -1, 0, 1 ), 3 ), _oi.exp( _oi.normalize( _oi.getLJoystickXAxis( ), -1, 0, 1 ), 3 ));		
		
		//If we are operating the claw
		if( !_mode ){
			// If the arm is locked
			if( !_unlocked ){
				//Right trigger to move up
				if( _arm.getEncoder() < 0 && _oi.getAxis(RobotMap.R2) > .03  )
				{
					System.out.println("Getting axis value");
					_arm.setSpeed( _oi.getAxis(RobotMap.R2));
				}
				//Left trigger to move down
				else if( _arm.getEncoder() > -308.5 && _oi.getAxis(RobotMap.L2) > 0.03 )
				{
					//System.out.println(Utilities.exp(_driver.getAxis(RobotMap.LEFT_2),5) );
					_arm.setSpeed( -1.0 * _oi.getAxis(RobotMap.L2));
				}
				//Otherwise don't move
				else
				{
					_arm.setSpeed(0);
				}
			//If the arm is unlocked
			}else{
				//Right trigger to move up
				if( _oi.getAxis(RobotMap.R2) > .03  )
				{
					_arm.setSpeed( _oi.getAxis(RobotMap.R2));
				}
				//Left trigger to move down
				else if( _oi.getAxis(RobotMap.L2) > 0.03 )
				{
					_arm.setSpeed( -1.0 * _oi.getAxis(RobotMap.L2));
				}
				//Otherwise keep still
				else
				{
					_arm.setSpeed(0);
				}
			}

			// Pressing Start unlocks the arm
			if( _oi.getStart() ){
				_unlocked = true;
			}
			// Pressing Select locks the arm
			if( _oi.getSelect()){
				_arm.reset();
				_unlocked = false;
			}

		// If operating the climber
		}else{
			// Right to climb up
			if( _oi.getAxis(RobotMap.R2) > .03 )
			{
				//System.out.println( Utilities.exp(-1.0 * _driver.getAxis(RobotMap.RIGHT_2), 5) );
				_climber.setSpeed( -1.0 * _oi.getAxis(RobotMap.R2) * 0.9 );
			}

/* Not in use 			
			//Left trigger to move down
			else if( _oi.getAxis(RobotMap.L2) > 0.03 )
			{
				//System.out.println(Utilities.exp(_driver.getAxis(RobotMap.LEFT_2),5) );
				_climber.setSpeed(_oi.getAxis(RobotMap.L2) * 0.9 );
			}
*/
			
			// Otherwise keep still
			else
			{
				_climber.setSpeed(0);
			}
		}
	}


	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}


}
