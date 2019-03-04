package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;

public class Pneumatics{

    Joystick joy;

    DoubleSolenoid grabberLift = new DoubleSolenoid(0, 1);
    DoubleSolenoid hatchGrabber = new DoubleSolenoid(2, 3);
    //DoubleSolenoid frntEnd = new DoubleSolenoid(4, 5);
    //DoubleSolenoid backEnd = new DoubleSolenoid(6, 7);

    public Pneumatics(Joystick joy){
        this.joy = joy;
        
    }

    public void run(){
        if(joy.getRawButton(1)){
            grabberLift.set(DoubleSolenoid.Value.kForward);
        }else if(joy.getRawButton(2)){
            grabberLift.set(DoubleSolenoid.Value.kReverse);
        }else{
            grabberLift.set(DoubleSolenoid.Value.kOff);
        }
        if(joy.getRawButton(3)){
            hatchGrabber.set(DoubleSolenoid.Value.kForward);
        } else if(joy.getRawButton(4)){
            hatchGrabber.set(DoubleSolenoid.Value.kReverse);
        } else{
            hatchGrabber.set(DoubleSolenoid.Value.kOff);
        }
        
    }

}