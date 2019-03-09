package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ManipulaterController {


	DoubleSolenoid grabberLift = new DoubleSolenoid(0, 1);
	DoubleSolenoid hatchGrabber = new DoubleSolenoid(2, 3);

	WPI_TalonSRX _lift = new WPI_TalonSRX(30);
	WPI_VictorSPX _ballWheel = new WPI_VictorSPX(31);
	Joystick manipJoy = new Joystick(1);
	Joystick driveJoy;

	// PID values\[][\]

	double P = -0.0004, I = 0, D = 0;
	// CTREEnocder weird stuff
	CTREEncoder enc = new CTREEncoder(_lift);
	// PID controller
	PIDController liftPID = new PIDController(P, I, D, enc, _lift);
	// Height for placing the hatch as well as modes to reach those points
    double[] hatchHeight = new double[]{-500, -10200, -20117};

	// Height for placing the ball as well as modes to reach those points
    double[] ballHeight = new double[]{
		// rocket 1:
		-7082,
		// rocket 2:
		-17045,
		// rocket 3:
		-26330
		// cargo:
		//9000
	};

	enum getToBottomModes{
		SETBOTTOMPOINT, TURNLIFTOFF
	  }

	  getToBottomModes bottomModes;
	int step = 1;

		enum modes { PID, FULLMANUAL };

		modes mode;
	
	public ManipulaterController(boolean setInverted, Joystick joy){
		
		this.driveJoy = joy;
		liftPID.reset();
		liftPID.setOutputRange(-0.3, 0.5);
		mode = modes.PID;
		bottomModes = getToBottomModes.SETBOTTOMPOINT;
	}
	double getEncoder(){
		return _lift.getSelectedSensorPosition(0);
	}
	public void run(){
		cargoGrabberRun();
		SmartDashboard.putNumber("Encoder", _lift.getSelectedSensorPosition(0));
    	SmartDashboard.putNumber("Step", step);
    	SmartDashboard.putNumber("Voltage", _lift.getMotorOutputVoltage());
    	SmartDashboard.putNumber("Set point", liftPID.getSetpoint());

		if(driveJoy.getRawButton(9)){
            hatchGrabber.set(DoubleSolenoid.Value.kForward);
        } else if(driveJoy.getRawButton(10)){
            hatchGrabber.set(DoubleSolenoid.Value.kReverse);
        } else{
            hatchGrabber.set(DoubleSolenoid.Value.kOff);
		}
		if(driveJoy.getRawButton(11)){
            grabberLift.set(DoubleSolenoid.Value.kForward);
        } else if(driveJoy.getRawButton(12)){
            grabberLift.set(DoubleSolenoid.Value.kReverse);
        } else{
            grabberLift.set(DoubleSolenoid.Value.kOff);
		}
		

		switch(mode){
			case PID:
			if(manipJoy.getRawButton(8)){
				liftPID.disable();
				_lift.set(.5);
				liftPID.setSetpoint(_lift.getSelectedSensorPosition(0));
			  }else if(manipJoy.getRawButton(7)){
				liftPID.disable();
				_lift.set(-.3);
				liftPID.setSetpoint(_lift.getSelectedSensorPosition(0));
			  // Ball panel heights
			  }else if(manipJoy.getRawButton(4)){
				liftPID.setSetpoint(ballHeight[0]);
			  }else if(manipJoy.getRawButton(5)){
				liftPID.setSetpoint(ballHeight[1]);
			  }else if(manipJoy.getRawButton(6)){
				liftPID.setSetpoint(ballHeight[2]);
			  // Hatch height
			  }else if(manipJoy.getRawButton(1)){
				bottomModes = getToBottomModes.SETBOTTOMPOINT;
				goToBottom();
			  }else if(manipJoy.getRawButton(2)){
				liftPID.setSetpoint(hatchHeight[1]);
			  }else if(manipJoy.getRawButton(3)){
				liftPID.setSetpoint(hatchHeight[2]);
			  // Ball height for cargo ship
			  }else if(manipJoy.getRawButton(12)){
				// idk yet
			  }
			  else{
				  switch(step){
					case 1:
					liftPID.disable();
					  _lift.set(0);
					  if(driveJoy.getRawButtonPressed(5))
						step++;
					  break;
					case 2:
					  liftPID.enable();
					  if(driveJoy.getRawButtonPressed(5))
						step--;
					  break;
				  }
				}
				break;
			case FULLMANUAL:
				liftPID.disable();
				if(manipJoy.getY()>.5)
					_lift.set(.5);
				else if(manipJoy.getY()<-.5)
					_lift.set(-.3);
				else 
					_lift.set(0);
				break;	
		}
	}

	public void cargoGrabberRun(){
		if(manipJoy.getRawButton(11)){
			_ballWheel.set(1);
		}else if(manipJoy.getRawButton(12)){
			_ballWheel.set(-1);
		}else{
			_ballWheel.set(0);
		}
		//_ballWheel.set(.5);*/
	}

	void goToBottom(){
		switch(bottomModes){
		  case SETBOTTOMPOINT:
			liftPID.setSetpoint(hatchHeight[0]);
			bottomModes = getToBottomModes.TURNLIFTOFF;
			break;
		  case TURNLIFTOFF:
			if(_lift.getSelectedSensorPosition(0)<-1100)
			  step = 1;
			  liftPID.disable();
			break;
		}
	  }

	// Used to invert the talon direction
	public void invert(boolean inv) {
		_lift.setInverted(inv);
	}
	
	void reset() {
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