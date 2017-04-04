package CCcanTalon;

import com.ctre.CANTalon;

public class CCcanTalon extends CANTalon {

	private boolean _reverse;
	
	public CCcanTalon(int deviceNumber, boolean reverse) {
		super(deviceNumber);
		// TODO Auto-generated constructor stub
		_reverse = reverse;
	}
	
	public void set(double speed){
		if(_reverse){
			speed *= -1;
		}
		super.set(speed);
	}

}
