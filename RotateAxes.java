package application;


import javax.inject.Inject; //Need this to inject more instructions into robot
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication; // Basic robot commands
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*; // Refer to everything under the robot's baisc movements
import com.kuka.roboticsAPI.deviceModel.LBR; //Create instances and control them

/**
 * Implementation of a robot application.
 * <p>
 * The application provides a {@link RoboticsAPITask#initialize()} and a 
 * {@link RoboticsAPITask#run()} method, which will be called successively in 
 * the application lifecycle. The application will terminate automatically after 
 * the {@link RoboticsAPITask#run()} method has finished or after stopping the 
 * task. The {@link RoboticsAPITask#dispose()} method will be called, even if an 
 * exception is thrown during initialization or run. 
 * <p>
 * <b>It is imperative to call <code>super.dispose()</code> when overriding the 
 * {@link RoboticsAPITask#dispose()} method.</b> 
 * 
 * @see UseRoboticsAPIContext
 * @see #initialize()
 * @see #run()
 * @see #dispose()
 */
public class RotateAxes extends RoboticsAPIApplication 
{
	@Inject
	private LBR lBR_iiwa_7_R800_1; //Calling lBR

	@Override
	public void initialize() 
	{
		// initialize your application here, for now leave empty
//		cab = (Controller)getContext().getControllers().toArray()[0];
	}

	@Override
	public void run() 
	{
		// your application execution starts here
		lBR_iiwa_7_R800_1.move(ptpHome().setJointVelocityRel(0.2));
		// ptp move to zero position at 20% speed
		lBR_iiwa_7_R800_1.move(ptp(0,0,0,(Math.PI/2)*(-1),0,Math.PI/2,0).setJointVelocityRel(0.2));
		// ptp move for 7 axis angles (rad) at 20% speed
//		move(linRel(x, y, z, a, b, c). setCartVelocity(velocity).setCartAcceleration(acceleration));
	}//End of run
} // End of project