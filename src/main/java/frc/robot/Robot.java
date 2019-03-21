/*
2019 FRC Robot Code
Team: 7160
Last Update: March 8, 2019 
Written by : Jordan Lake, Conner Grant, David Scott, Nathaniel Seymour, and Ben Walunas
*/


package frc.robot;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;


public class Robot extends TimedRobot {
  

  Joystick driveJoy = new Joystick(0);
  Joystick manipJoy = new Joystick(1);


  ManipulaterController _manipulaterController = new ManipulaterController(true, driveJoy, manipJoy);
  LimeLight _limelight = new LimeLight();
  DriveTrain _drivetrain = new DriveTrain(driveJoy, manipJoy);

  enum robotModes{
    START, GETOFFHAB, MANUALDRIVE
  }

  robotModes robo;

  Timer time = new Timer();

  boolean gameRunning = false;

  I2C arduino = new I2C(I2C.Port.kOnboard, 8);
  DriverStation.Alliance alliance;
  byte[] receiveData = new byte[1];
  byte[] Blue = "b".getBytes();
  byte[] Red = "r".getBytes();

  @Override
  public void robotInit() {
    
    robo = robotModes.START;
    _manipulaterController.reset();
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
  }
  @Override
  public void disabledInit() {
    
  }

  @Override
  public void disabledPeriodic() {
    
  }

  @Override
  public void autonomousInit() {
    alliance = DriverStation.getInstance().getAlliance();

    if(alliance == DriverStation.Alliance.Blue){
      arduino.transaction(Blue, Blue.length, receiveData, receiveData.length);
    }else if(alliance == DriverStation.Alliance.Red){
      arduino.transaction(Red, Red.length, receiveData, receiveData.length);
    }

    gameRunning = true;
    robo = robotModes.START;
    _manipulaterController.reset();
    time.reset();
  }

  @Override
  public void robotPeriodic(){

    //System.out.println(robo);

    if(gameRunning){
      switch(robo){
        case START:
          time.start();
          robo = robotModes.GETOFFHAB;
          break;

        case GETOFFHAB:
          _manipulaterController.getOffHab(time);
          if(_drivetrain.getOffHab(time))
            robo = robotModes.MANUALDRIVE;
          break;

        case MANUALDRIVE:
          _drivetrain.checkHeight(_manipulaterController.tooHigh());
          _drivetrain.run();
          _manipulaterController.run();
          break;
      }
    }
  }
  @Override
  public void testInit() {
    _manipulaterController.reset();
  }

  @Override
  public void testPeriodic() {
    _drivetrain.checkHeight(_manipulaterController.tooHigh());
    _drivetrain.run();
    _manipulaterController.run();
  }

}
