package org.usfirst.frc.team4550.robot;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;

public class Camera {

	public static Camera _instance;

	private UsbCamera _cam;
	private UsbCamera _cam2;

	private Camera(){
		_cam = CameraServer.getInstance().startAutomaticCapture(0);
		_cam2 = CameraServer.getInstance().startAutomaticCapture(1);
		_cam.setFPS(20);
		_cam.setFPS(20);
	}

	public static Camera getInstance(){
		if(_instance == null){
			_instance = new Camera();
		}
		return _instance;
	}

	public void startFeed(){

	}

}
