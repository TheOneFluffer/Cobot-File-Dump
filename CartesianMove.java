package application;


import javax.inject.Inject;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;
import com.kuka.roboticsAPI.deviceModel.LBR;

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
public class CartesianMove extends RoboticsAPIApplication {
	@Inject
	private LBR lbr;

	@Override
	public void initialize() {
		// initialize your application here
	}

	@Override
	public void run() {
		// your application execution starts here
//		lBR_iiwa_7_R800_1.move(ptpHome());
		lbr.move(ptp(0,0,0,(Math.PI/2)*(-1),0,Math.PI/2,0).setJointVelocityRel(0.2));
		// ptp move for 7 axis angles (rad) and 20% speed
		lbr.move(linRel(0,0,200).setCartVelocity(100));
		// linear relative move z=200 wrt tool frame
		lbr.move(linRel(-100,0,0).setCartVelocity(200));
		// linear relative move x=-100 wrt tool frame
	}
}