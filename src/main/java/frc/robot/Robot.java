/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;

/*
2019 FRC Robot Code
Team: 7160
Last Update: February 14, 2019 
Written by : Jordan Lake, Conner Grant, David Scott
*/


public class Robot extends TimedRobot {
  

  Joystick _joystick = new Joystick(0);

  LiftController _lift = new LiftController(false, _joystick);
  Pneumatics _pneumatics = new Pneumatics();
  LimeLight _limelight = new LimeLight();
  DriveTrain _drivetrain = new DriveTrain(_joystick);

  @Override
  public void robotInit() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    _lift.run();
    _pneumatics.run();
    _limelight.run();
    _drivetrain.run();




  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
