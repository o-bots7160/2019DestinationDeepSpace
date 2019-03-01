package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain {

    Joystick joy1;

    WPI_VictorSPX _rghtFront = new WPI_VictorSPX(10);
    WPI_VictorSPX _rghtFollower = new WPI_VictorSPX(11);
    WPI_TalonSRX _leftFront = new WPI_TalonSRX(21);
    WPI_VictorSPX _leftFollower = new WPI_VictorSPX(21);

    WPI_VictorSPX _habClimb = new WPI_VictorSPX(40);

    DifferentialDrive _diffDrive = new DifferentialDrive(_leftFront, _rghtFront);


    public DriveTrain(Joystick joy){
        joy1 = joy;

    _rghtFront.configFactoryDefault();
    _rghtFollower.configFactoryDefault();
    _leftFront.configFactoryDefault();
    _leftFollower.configFactoryDefault();
    _rghtFollower.follow(_rghtFront);
    _leftFollower.follow(_leftFront);

    }

    public void joyRun(){

        _diffDrive.arcadeDrive(joy1.getY(), joy1.getZ());

    }

    public void autoRun(double x,double z){
        _diffDrive.arcadeDrive(x, z);
    }

    public void climb(double speed){
        _habClimb.set(speed);
    }

}