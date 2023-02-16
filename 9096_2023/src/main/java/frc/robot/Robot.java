package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.Joystick;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;


public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  WPI_VictorSPX victorFL = new WPI_VictorSPX(10);
  WPI_VictorSPX victorFR = new WPI_VictorSPX(11);
  WPI_VictorSPX victorBR = new WPI_VictorSPX(12);
  WPI_VictorSPX victorBL = new WPI_VictorSPX(13);
  Joystick ctrl = new Joystick(0);
  CANSparkMax claw = new CANSparkMax(20, MotorType.kBrushless);
  MotorControllerGroup left = new MotorControllerGroup(victorFL, victorBL);
  MotorControllerGroup right = new MotorControllerGroup(victorFR, victorBR);
  DifferentialDrive drive = new DifferentialDrive(left, right);

  private RobotContainer m_robotContainer;

  @Override
  public void robotInit() {
    victorBL.follow(victorFL);
    victorBR.follow(victorFR);
    victorFL.setInverted(true);
    victorBL.setInverted(InvertType.FollowMaster);
    victorBR.setInverted(InvertType.FollowMaster);

    m_robotContainer = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {

    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    //victorFL.set(ControlMode.PercentOutput, ctrl.getY());//(0.5*ctrl.getX() * 0.2f) + (ctrl.getY()*0.25));
    //victorFR.set(ControlMode.PercentOutput, ctrl.getY());//(-0.5*ctrl.getX() * 0.2f) + (ctrl.getY())*0.25);
    drive.arcadeDrive(ctrl.getY(), ctrl.getX());
    //victorFL.set(0.5f);//(-0.5*ctrl.getX() * 0.2f) + (ctrl.getY())*0.25);
    /*if (ctrl.get()) {
      claw.set(0.4f);
    } else if (ctrl.getCrossButton()) {
      claw.set(-0.4f);
    } else {
      claw.set(0.0f);
    }
    */

  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
