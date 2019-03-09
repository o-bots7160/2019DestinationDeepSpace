package frc.robot;
import edu.wpi.first.wpilibj.Joystick;

import com.sun.org.apache.xpath.internal.functions.Function;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
public class LimeLight{
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry tvert = table.getEntry("tvert");
    double x = 0;
    double height = 0;
    double dist = 0;
    static double heightPixelConstant = 262.9565217391; //conversion factor of pixels to inches
    static double heightInchConstant = 5.75; //actual height of object in inches
    Joystick driveJoy = new Joystick(0);
    
    


    public void run(){
        x = tx.getDouble(0.0);
        double Kp = 0.1;
        double min_command = 0.05;
        height = tvert.getDouble(0.0);
        if(height > 0){
            dist = heightPixelConstant*heightInchConstant/height;
        }else{
            dist = 0.0;
            }
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(2);
        double steering_adjust = 0.0;
        double heading_error = -x;
        double max_driveSpeed = 1.0;
        double desired_distance = 20;
        double driving_adjust = 0;
        if (x > 1.0){
            steering_adjust = Kp*heading_error - min_command;
            }else if (x < 1.0){
            steering_adjust = Kp*heading_error + min_command;
            }
        
    }
    
}