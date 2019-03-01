/*
2019 FRC Robot Code
Team: 7160
Last Update: February 14, 2019 
Written by : Jordan Lake, Conner Grant, David Scott
*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;


public class Robot extends TimedRobot {
  

  Joystick _joystick = new Joystick(0);
  Joystick _joystick2 = new Joystick(1);

  ManipulaterController _manipulaterController = new ManipulaterController(false, _joystick2);
  Pneumatics _pneumatics = new Pneumatics(_joystick2);
  LimeLight _limelight = new LimeLight();
  DriveTrain _drivetrain = new DriveTrain(_joystick);

  @Override
  public void robotInit() {
  }

  public void run(){
    _drivetrain.joyRun();;
    _manipulaterController.run();
    _pneumatics.run();
    //_limelight.run();
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
