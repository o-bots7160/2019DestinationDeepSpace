package frc.robot;
import edu.wpi.first.wpilibj.Joystick;



import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
public class LimeLight{
    static NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    static NetworkTableEntry tx = table.getEntry("tx");
    static NetworkTableEntry tvert = table.getEntry("tvert");
    static double x = 0;
    static double height = 0;
    static double dist = 0;
    static double heightPixelConstant = 262.9565217391; //conversion factor of pixels to inches
    static double heightInchConstant = 5.75; //actual height of object in inches
    Joystick driveJoy = new Joystick(0);

    


    public static Double leftRun(){
        x = tx.getDouble(0.0);
        double Kp = 0.1;
        double min_command = 0.05;
        if(NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").getDouble(0.0) != 2)
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(2);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
        double steering_adjust = 0.0;
        double heading_error = -x;
        System.out.println(x);
        if (x > 1.0){
            steering_adjust = Kp*heading_error - min_command;
            }else if (x < 1.0){
            steering_adjust = Kp*heading_error + min_command;
            }
        return steering_adjust;
    }
    public static Double rightRun(){
        x = tx.getDouble(0.0);
        
        double Kp = 0.1;
        double min_command = 0.05;
        if(NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").getDouble(0.0) != 3)
            NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(3);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
        double steering_adjust = 0.0;
        double heading_error = -x;
        if (x > 1.0){
            steering_adjust = Kp*heading_error - min_command;
            }else if (x < 1.0){
            steering_adjust = Kp*heading_error + min_command;
            }
        return steering_adjust;
    }
}
