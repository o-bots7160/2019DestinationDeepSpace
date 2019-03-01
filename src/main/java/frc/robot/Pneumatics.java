package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;

public class Pneumatics{

    Joystick joy;

    DoubleSolenoid grabberLift = new DoubleSolenoid(0, 1);
    DoubleSolenoid hatchGrabber = new DoubleSolenoid(2, 3);
    DoubleSolenoid frntEnd = new DoubleSolenoid(4, 5);
    DoubleSolenoid backEnd = new DoubleSolenoid(6, 7);

    public Pneumatics(Joystick joy){
        this.joy = joy;
    }

    public void run(){
        
    }

}