package org.usfirst.frc.team4550.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;

//	//PRACTICE ROBOT CONSTANTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//	
//	public static final int LEFT_FORWARD_PORT = 3;
//	public static final int RIGHT_FORWARD_PORT = 2;
//	public static final int LEFT_REAR_PORT = 5;
//	public static final int RIGHT_REAR_PORT = 1;
//	public static final int CLIMBER_PORT  = 6;
//	public static final int ARM_PORT = 4;

//	COMPETITION ROBOT CONSTANTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	// Wheel Talons
	public static final int LEFT_FORWARD_PORT = 5;
	public static final int RIGHT_FORWARD_PORT = 4;
	public static final int LEFT_REAR_PORT = 2;
	public static final int RIGHT_REAR_PORT = 3;
	
	// Mechanism Talons
	public static final int CLIMBER_PORT  = 1;
	public static final int ARM_PORT = 6;
	
	// Wheel Talon Polarity
	public static final boolean LEFT_FORWARD_REVERSE = false;
	public static final boolean RIGHT_FORWARD_REVERSE = true;
	public static final boolean LEFT_REAR_REVERSE = false;
	public static final boolean RIGHT_REAR_REVERSE = true;		
	
	// Joystick Axises
	public static final int L_JOYSTICK_HORIZONTAL = 0;
	public static final int L_JOYSTICK_VERTICAL = 1;
	public static final int L2 = 2;
	public static final int R2 = 3;
	public static final int R_JOYSTICK_HORIZONTAL = 4;
	public static final int R_JOYSTICK_VERTICAL = 5;

	// Controller Buttons
	public static final int X_BUTTON = 1;
	public static final int O_BUTTON = 2;
	public static final int SQUARE_BUTTON = 3;
	public static final int TRIANGLE_BUTTON = 4;
	public static final int L1_BUTTON = 5;
	public static final int R1_BUTTON = 6;
	public static final int SELECT_BUTTON = 7;
	public static final int START_BUTTON = 8;
	//These buttons are when you push down the left and right circle pad
	public static final int L_JOYSTICK_BUTTON = 9;
	public static final int R_JOYSTICK_BUTTON = 10;

	// Controller Zeroes
	public static final double LEFT_Y_ZERO = -0.0078125;
	public static final double RIGHT_Y_ZERO = -0.0078125;



	// Wheel Encoder Ports
	public static final int ENCODER_A_LEFT = 0;
	public static final int ENCODER_B_LEFT = 1;
	public static final int ENCODER_A_RIGHT = 4;
	public static final int ENCODER_B_RIGHT = 5;
	
	
	// Arm Encoder Ports
	public static final int ENCODER_ARM_A = 2;
	public static final int ENCODER_ARM_B = 3;
	
	// Servo Ports
	public static final int SERVO_PORT = 0;
	public static final int SERVO_TOO_PORT = 1;
	

}