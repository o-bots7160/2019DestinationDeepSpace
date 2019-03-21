package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain {

    Joystick driveJoy;
    Joystick manipJoy;

    WPI_VictorSPX _rghtFront = new WPI_VictorSPX(10);
    WPI_VictorSPX _rghtFollower = new WPI_VictorSPX(11);
    WPI_TalonSRX _leftFront = new WPI_TalonSRX(20);
    WPI_VictorSPX _leftFollower = new WPI_VictorSPX(21);

    WPI_VictorSPX _habClimb = new WPI_VictorSPX(40);

    SpeedControllerGroup left = new SpeedControllerGroup(_leftFront, _leftFollower);
    SpeedControllerGroup right = new SpeedControllerGroup(_rghtFront, _rghtFollower);

    //Solenoid _frontClimb1 = new Solenoid(1, 0);
    //Solenoid _backClimb1 = new Solenoid(1, 1);


    //DoubleSolenoid _frontClimbHab2 = new DoubleSolenoid(1,2,3);   
    //DoubleSolenoid _backClimbHab2 = new DoubleSolenoid(1, 4, 5);

    DifferentialDrive _diffDrive = new DifferentialDrive(left, right);

    double speed = 1.25;

    public DriveTrain(Joystick driveJoy, Joystick manipJoy){
        //_rghtFollower.follow(_rghtFront);
        //_leftFollower.follow(_leftFront);
        this.driveJoy = driveJoy;
        this.manipJoy = manipJoy;

        _rghtFront.setInverted(true);
        _rghtFollower.setInverted(false);

        _rghtFront.configFactoryDefault();
        _rghtFollower.configFactoryDefault();
        _leftFront.configFactoryDefault();
        _leftFollower.configFactoryDefault();
        _rghtFront.configClosedloopRamp(.2);
        _leftFront.configClosedloopRamp(.2);
        _rghtFront.configClosedloopRamp(.1, 0);
        _leftFront.configClosedloopRamp(.2, 0);

    }

    

    public void checkHeight(boolean tooHigh){
        if(!tooHigh)
            speed = 1.75;
        else 
            speed = 1.25;
    }

    public void run(){
        joyRun();
        limelightDriveToTarget();
    }



    void joyRun(){
        if(!(driveJoy.getRawButton(1) || driveJoy.getRawButton(2)))
            _diffDrive.arcadeDrive(-driveJoy.getY()/speed, driveJoy.getZ()/speed);

    }

    void rockingSpeed(){
        if(manipJoy.getRawButtonPressed(8)){
            _rghtFront.configClosedloopRamp(0);
            _leftFront.configClosedloopRamp(0);
            _rghtFront.configClosedloopRamp(0, 0);
            _leftFront.configClosedloopRamp(0, 0);
            speed = 1;
        }else{
            _rghtFront.configClosedloopRamp(.2);
            _leftFront.configClosedloopRamp(.2);
            _rghtFront.configClosedloopRamp(.1, 0);
            _leftFront.configClosedloopRamp(.2, 0);
            speed = 1.25;
        }
    }

    public void autoRun(double x,double z){
        _diffDrive.arcadeDrive(x, z);
    }
    
    public boolean getOffHab(Timer time){
        if(time.get() >=  4 && time.get() <= 6)
            autoRun(.6, 0);
        else if (time.get() >= 6){
            autoRun(0, 0);
            return true;
        }

        return false;
    }

    public void climb(){
        if(driveJoy.getPOV()==0)
            _habClimb.set(.5);
        else if(driveJoy.getPOV()==180)
            _habClimb.set(-.5);
        else
            _habClimb.set(0);
    }

    void limelightDriveToTarget(){
        if(driveJoy.getRawButton(2)){
            double steering_speed = LimeLight.leftRun(); //Align on to left-most target
            _diffDrive.arcadeDrive(-driveJoy.getY()/1.25, steering_speed/(-1.5));
        }else if(driveJoy.getRawButton(1)){
            double steering_speed = LimeLight.rightRun(); //Alight on to right-most target
            _diffDrive.arcadeDrive(-driveJoy.getY()/1.25, steering_speed/(-1.5));
        }else{
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
            }
        }
    

}

