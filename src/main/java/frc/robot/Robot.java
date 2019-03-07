/*
2019 FRC Robot Code
Team: 7160
Last Update: March 1, 2019 
Written by : Jordan Lake, Conner Grant, David Scott
*/


package frc.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;


public class Robot extends TimedRobot {
  

  Joystick _joystick = new Joystick(0);


  ManipulaterController _manipulaterController = new ManipulaterController(true, _joystick);
  LimeLight _limelight = new LimeLight();
  DriveTrain _drivetrain = new DriveTrain(_joystick);
  Compressor c = new Compressor(0);

  @Override
  public void robotInit() {
    _manipulaterController.reset();
  }
  @Override
  public void disabledInit() {
    
  }

  @Override
  public void disabledPeriodic() {
  
    
}

  public void run(){
    _drivetrain.joyRun();
    _manipulaterController.liftRun();
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
    run();
  }

  @Override
  public void teleopInit() {
    _manipulaterController.reset();
  }

  @Override
  public void teleopPeriodic() {
    run();
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
