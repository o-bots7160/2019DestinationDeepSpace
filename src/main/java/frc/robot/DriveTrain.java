package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain {

    Joystick joy1;

    WPI_VictorSPX _rghtFront = new WPI_VictorSPX(10);
    WPI_VictorSPX _rghtFollower = new WPI_VictorSPX(11);
    WPI_VictorSPX _leftFront = new WPI_VictorSPX(21);
    WPI_VictorSPX _leftFollower = new WPI_VictorSPX(21);

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

    public void run(){

        _diffDrive.arcadeDrive(joy1.getY(), joy1.getZ());

    }


}