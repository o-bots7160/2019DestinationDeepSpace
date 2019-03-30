package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain {

    Joystick joy;

    WPI_VictorSPX _rghtFront = new WPI_VictorSPX(10);
    WPI_VictorSPX _rghtFollower = new WPI_VictorSPX(11);
    WPI_TalonSRX _leftFront = new WPI_TalonSRX(20);
    WPI_VictorSPX _leftFollower = new WPI_VictorSPX(21);

    WPI_VictorSPX _habClimb = new WPI_VictorSPX(40);

    //Solenoid _frontClimb1 = new Solenoid(1, 0);
    //Solenoid _backClimb1 = new Solenoid(1, 1);


    //DoubleSolenoid _frontClimbHab2 = new DoubleSolenoid(1,2,3);   
    //DoubleSolenoid _backClimbHab2 = new DoubleSolenoid(1, 4, 5);
    
    //DoubleSolenoid sole1 = new DoubleSolenoid(4, 0, 1);
    //DoubleSolenoid sole2 = new DoubleSolenoid(4, 2, 3);
    DoubleSolenoid sole3 = new DoubleSolenoid(3, 0, 1);
    DoubleSolenoid sole4 = new DoubleSolenoid(3, 2, 3); //temporary pneumatics testing*/

    DifferentialDrive _diffDrive = new DifferentialDrive(_leftFront, _rghtFront);

    double speed = 1.25;

    public DriveTrain(Joystick joy){
        this.joy = joy;

        _rghtFront.configFactoryDefault();
        _rghtFollower.configFactoryDefault();
        _leftFront.configFactoryDefault();
        _leftFollower.configFactoryDefault();
        _rghtFront.configClosedloopRamp(.2);
        _leftFront.configClosedloopRamp(.2);
        _rghtFront.configClosedloopRamp(.1, 0);
        _leftFront.configClosedloopRamp(.2, 0);
        _rghtFollower.follow(_rghtFront);
        _leftFollower.follow(_leftFront);
    }

    

    public void checkHeight(boolean tooHigh){
        if(!tooHigh)
            speed = 2;
        else 
            speed = 1.25;
    }

    public void run(){
        joyRun();
        limelightDriveToTarget();
        //temporary set up
        /*
        if(joy.getRawButton(9)){
            sole3.set(DoubleSolenoid.Value.kForward);
        }else if(joy.getRawButton(10)){
            sole3.set(DoubleSolenoid.Value.kReverse);
            //sole5.set(false);
        }else{
            sole3.set(DoubleSolenoid.Value.kOff);
        }
        if(joy.getRawButton(11)){
            sole4.set(DoubleSolenoid.Value.kForward);
        } else if(joy.getRawButton(12)){
            sole4.set(DoubleSolenoid.Value.kReverse);
            //sole6.set(true);
        } else{
            sole4.set(DoubleSolenoid.Value.kOff);
        }*/
        //delete this if need to (between comments)
    }



    void joyRun(){
        if(!(joy.getRawButton(1) || joy.getRawButton(2)))
            _diffDrive.arcadeDrive(-joy.getY()/1.5, joy.getZ()/1.5);

    }

    public void autoRun(double x,double z){
        _diffDrive.arcadeDrive(x, z);
    }
    
    public boolean getOffHab(Timer time){
        if(time.get() >= 3 && time.get() <= 5)
            autoRun(.6, 0);
        else if (time.get() >= 5){
            autoRun(0, 0);
            return true;
        }

        return false;
    }

    public void climb(){
        if(joy.getPOV()==0)
            _habClimb.set(.5);
        else if(joy.getPOV()==180)
            _habClimb.set(-.5);
        else
            _habClimb.set(0);
    }

    void limelightDriveToTarget(){
        if(joy.getRawButton(2)){
            double steering_speed = LimeLight.leftRun(); //Align on to left-most target
            _diffDrive.arcadeDrive(-joy.getY()/speed, steering_speed/(-1.5));
        }else if(joy.getRawButton(1)){
            double steering_speed = LimeLight.rightRun(); //Alight on to right-most target
            _diffDrive.arcadeDrive(-joy.getY()/speed, steering_speed/(-1.5));
        }else{
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
            }
        }
    

}

