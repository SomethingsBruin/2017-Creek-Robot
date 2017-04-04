package org.usfirst.frc.team4550.robot.commands;

import org.usfirst.frc.team4550.robot.OI;

import Chassis.Chassis;
import edu.wpi.first.wpilibj.command.Command;


public class DriveForward extends Command{

	Chassis _chassis;
	private double dist;
	private double initAngle;
	private double speed;
	
	public DriveForward(double dist, double speed){
		this.dist = dist;
		this.speed = speed;
	}
	
	protected void initialize(){
		_chassis = _chassis.getInstance();
		_chassis.reset();
		initAngle = _chassis.getAngle();
	}
	
	protected void execute(){
		System.out.println( "Encoder" + _chassis.getLeftEncoder());
		System.out.println(" Gyro:  " + _chassis.getAngle());
		_chassis.tankDrive((_chassis.getAngle() - initAngle)/100, OI.normalize(speed, -.9, 0, .9));
	}
	
	@Override
	protected boolean isFinished() {
		if( Math.abs( _chassis.getLeftEncoder() ) > dist ){
			return true;
		}
		return false;
	}
	
	protected void end(){
		_chassis.stop();
	}

	protected void interuppted(){
		_chassis.stop();
	}
	
}
