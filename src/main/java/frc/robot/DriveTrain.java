package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain {

    Joystick joy;

    WPI_VictorSPX _rghtFront = new WPI_VictorSPX(10);
    WPI_VictorSPX _rghtFollower = new WPI_VictorSPX(11);
    WPI_TalonSRX _leftFront = new WPI_TalonSRX(20);
    WPI_VictorSPX _leftFollower = new WPI_VictorSPX(21);

    WPI_VictorSPX _habClimb = new WPI_VictorSPX(40);

    DifferentialDrive _diffDrive = new DifferentialDrive(_leftFront, _rghtFront);

    DoubleSolenoid grabberLift = new DoubleSolenoid(0, 1);

    public DriveTrain(Joystick joy){
        this.joy = joy;

    _rghtFront.configFactoryDefault();
    _rghtFollower.configFactoryDefault();
    _leftFront.configFactoryDefault();
    _leftFollower.configFactoryDefault();
    _rghtFollower.follow(_rghtFront);
    _leftFollower.follow(_leftFront);

    }

    public void joyRun(){

        _diffDrive.arcadeDrive(-joy.getY()/1.5, joy.getZ()/1.5);

    }

    public void autoRun(double x,double z){
        _diffDrive.arcadeDrive(x, z);
    }

    public void climb(){
        if(joy.getPOV()==0)
            _habClimb.set(.5);
        else if(joy.getPOV()==180)
            _habClimb.set(-.5);
        else
            _habClimb.set(0);
    }

}