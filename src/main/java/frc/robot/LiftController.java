package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class LiftController {

	WPI_TalonSRX _lift = new WPI_TalonSRX(30);
	Joystick _joy1;
	int step = 0;
	// PID values
	double P = 0.0004, I = 0, D = 0;
	// CTREEnocder weird stuff
	CTREEncoder enc = new CTREEncoder(_lift);
	// PID controller
	PIDController liftPID = new PIDController(P, I, D, enc, _lift);
	
	// Height for placing the hatch as well as modes to reach those points
    double[] hatchHeight = new double[]{4096, 8192, 9000};

	// Height for placing the ball as well as modes to reach those points
    double[] ballHeight = new double[]{5000, 7000, 8000, 9000};
	
	public LiftController(boolean setInverted, Joystick joy){
		invert(setInverted);
		_joy1 = joy;
		liftPID.reset();
		liftPID.setOutputRange(-0.5, 0.5);
	}

	public void run(){

		if(_joy1.getRawButton(5))
			step = 0;
		else if(_joy1.getRawButton(6)){
			liftPID.disable();
			step = 0;
		}
		
		switch(step){
			case 0:
				if(_joy1.getRawButton(3)){
					_lift.set(.5);
					liftPID.setSetpoint(_lift.getSelectedSensorPosition(0));
				}else if(_joy1.getRawButton(4)){
					_lift.set(-.3);
					liftPID.setSetpoint(_lift.getSelectedSensorPosition(0));
				}else if(_joy1.getRawButton(7)){
					liftPID.setSetpoint(hatchHeight[0]);
				}else if(_joy1.getRawButton(8)){
					liftPID.setSetpoint(hatchHeight[1]);
				}else if(_joy1.getRawButton(9)){
					liftPID.setSetpoint(hatchHeight[2]);
				}else if(_joy1.getRawButton(5)){
					liftPID.setSetpoint(ballHeight[0]);
				}else if(_joy1.getRawButton(10)){
					liftPID.setSetpoint(ballHeight[1]);
				}else if(_joy1.getRawButton(11)){
					liftPID.setSetpoint(ballHeight[2]);
				}else if(_joy1.getRawButton(12)){
					liftPID.setSetpoint(ballHeight[3]);
				}else
					liftPID.enable();
				break;
			case 1:
				if(_joy1.getRawButton(3)){
					_lift.set(.5);
				}else if(_joy1.getRawButton(4))
					_lift.set(-.3);
				else 
					_lift.set(0);
				break;	
		}
	}

	// Used to invert the talon direction
	public void invert(boolean inv) {
		_lift.setInverted(inv);
	}
	
	
	
	public void reset() {
		_lift.getSensorCollection().setPulseWidthPosition(0, 100);
		_lift.getSensorCollection().setQuadraturePosition(0, 100);
	}
	
}

class CTREEncoder implements PIDSource {

    WPI_TalonSRX talon;

    public CTREEncoder(WPI_TalonSRX talon){

        this.talon = talon;
    }
       
    public int get() {
        return talon.getSelectedSensorPosition();
    }
    @Override

    public double pidGet() {
        return get();
    }

    @Override

    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;

    }

    @Override

    public void setPIDSourceType (PIDSourceType pidSource){
        
    }
}