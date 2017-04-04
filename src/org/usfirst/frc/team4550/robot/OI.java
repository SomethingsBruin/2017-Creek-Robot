package org.usfirst.frc.team4550.robot;

import edu.wpi.first.wpilibj.Joystick;
import static org.usfirst.frc.team4550.robot.RobotMap.*;
import edu.wpi.first.wpilibj.buttons.Button;

import org.usfirst.frc.team4550.robot.commands.ExampleCommand;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
	// The Joystick that the driver is using
	private Joystick _controller;
	
	public OI()
	{
		//Sets _controller to to the correct input
		int input = 1 ; // input 1
		_controller = new Joystick ( input );
		
	}
	
	/**
	 * Prints the axises in the console
	 */
	public void printAxis()
	{
		//Prints all of the axises on the controller
		for( int i = 0; i < _controller.getAxisCount(); i++ )
		{
			System.out.printf( "%10s", i + " " + _controller.getRawAxis( i ) + "\t" );
	
		}
		System.out.println();
	}
	
	/**
	 * Prints the value assigned to the pressed button
	 */
	public void printButtons()
	{
		// Prints all of the buttons on the controller
		for( int i = 1; i <= _controller.getButtonCount(); i++ )
		{
			System.out.printf( "%10s", i + "   " + _controller.getRawButton( i ) + "\t" );
		}
		System.out.println();
	}
	
	/**
	 * Get the value of the selected axis
	 * @param axis
	 * @return
	 */
	public double getAxis( int axis )
	{
		// Returns the value of the specified axis.
		return _controller.getRawAxis( axis );
	}

	public double getLJoystickXAxis()
	{
		// Returns the left _controller's horizontal value.
		return _controller.getRawAxis( L_JOYSTICK_HORIZONTAL );
	}

	public double getLJoystickYAxis()
	{
		// Returns the left _controller's vertical value, which is inverted.
		return _controller.getRawAxis( L_JOYSTICK_VERTICAL ) * -1;
	}

	public double getRJoystickXAxis()
	{
		// Returns the right _controller's horizontal value.
		return _controller.getRawAxis( R_JOYSTICK_HORIZONTAL );
	}

	public double getRJoystickYAxis()
	{
		// Returns the right _controller's vertical value, which is inverted.
		return _controller.getRawAxis( R_JOYSTICK_VERTICAL ) * -1;
	}
	
	public double getL2( )
	{
		return _controller.getRawAxis(  L2 );
	}
	
	public double getR2( )
	{
		return _controller.getRawAxis( R2 );
	}
	
	public boolean getXButton( )
	{
		//Returns whether or not the x button is being pressed
		return _controller.getRawButton( RobotMap.X_BUTTON );
	}
	
	public boolean getOButton( )
	{
		return _controller.getRawButton( RobotMap.O_BUTTON );
	}
	
	public boolean getTriangleButton( )
	{
		return _controller.getRawButton( RobotMap.TRIANGLE_BUTTON );
	}
	
	public boolean getSquareButton( )
	{
		return _controller.getRawButton( RobotMap.SQUARE_BUTTON );
	}
	
	public boolean getL1( ){
		return _controller.getRawButton( RobotMap.L1_BUTTON );
	}
	
	public boolean getR1( ){
		return _controller.getRawButton( RobotMap.R1_BUTTON);
	}
	
	public boolean getStart( ){
		return _controller.getRawButton( RobotMap.START_BUTTON );
	}
	
	public boolean getSelect( ){
		return _controller.getRawButton( RobotMap.SELECT_BUTTON );
	}
	
	/**
	 * Normalizes the a given value
	 * @param max
	 * @param min
	 * @param value
	 * @return
	 */
	public static double normalize( double value, double min, double zero, double max )
	{
		//If the value exceeds the maximum, set it back to the max
		if( value > max )
		{
			return max;
		}
		//If the value is lower than the minimum, set it back to the min
		else if( value < min )
		{
			return min;
		}
		//If the controller is at neutral position, set the speed to zero
		else if (value == zero){
			return 0;
		}
		//Otherwise the value is fine
		return value;
	}
	
	public double exp(double value, double exponent){
		return Math.pow(value, exponent);
	}
}
