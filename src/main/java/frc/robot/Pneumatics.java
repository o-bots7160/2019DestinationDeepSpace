package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Pneumatics{

    DoubleSolenoid frntEnd = new DoubleSolenoid(0, 1);
    DoubleSolenoid backEnd = new DoubleSolenoid(2, 3);

    DoubleSolenoid grabberLiftFrnt = new DoubleSolenoid(4, 5);
    DoubleSolenoid grabberliftBack = new DoubleSolenoid(6, 7);

    DoubleSolenoid hatchGrabber = new DoubleSolenoid(8, 9);

    public Pneumatics(){

    }

    public void run(){
        
    }

}