package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.PS4Controller;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  VictorSPX victorFL = new VictorSPX(10);
  VictorSPX victorFR = new VictorSPX(11);
  VictorSPX victorBR = new VictorSPX(12);
  VictorSPX victorBL = new VictorSPX(13);
  PS4Controller PS4c = new PS4Controller(0);
  CANSparkMax claw = new CANSparkMax(20, MotorType.kBrushless);

  private RobotContainer m_robotContainer;

  @Override
  public void robotInit() {
    victorBL.follow(victorFL);
    victorBR.follow(victorFR);
    victorFL.setInverted(true);
    victorBL.setInverted(InvertType.FollowMaster);
    victorBR.setInverted(InvertType.FollowMaster);
    
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
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
    victorFR.set(ControlMode.PercentOutput, (0.5*PS4c.getLeftX() * 0.2f) + (PS4c.getRightY()*0.25));
    victorFL.set(ControlMode.PercentOutput, (-0.5*PS4c.getLeftX() * 0.2f) + (PS4c.getRightY()*0.25));
 
    if (PS4c.getCircleButton()) {
      claw.set(0.4f);
    } else if (PS4c.getCrossButton()) {
      claw.set(-0.4f);
    } else {
      claw.set(0.0f);
    }

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
