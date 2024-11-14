package application;


import javax.inject.Inject;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.Tool;
import com.kuka.roboticsAPI.motionModel.MotionBatch;

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
public class MBTCPMove extends RoboticsAPIApplication {
	@Inject
	private LBR lbr;
	private Tool fixedPart;

	@Override
	public void initialize() {
		// initialize your application here
		fixedPart = getApplicationData().createFromTemplate("FixedPart"); // get tool data

	}

	@Override
	public void run() {
		// your application execution starts here
		lbr.detachAll(); // detach all tool from robot
		fixedPart.attachTo(lbr.getFlange()); // attach tool to robot
		
		lbr.move(ptp(getApplicationData().getFrame("/StartPos")).setJointVelocityRel(0.2));
		
		MotionBatch mb = new MotionBatch(
				lin(getApplicationData().getFrame("/StartPos")).setCartVelocity(200),
				lin(getApplicationData().getFrame("/X100")).setCartVelocity(100),
				lin(getApplicationData().getFrame("/StartPos")).setCartVelocity(200)
				);
		fixedPart.getFrame("FixedPartTCP").move(mb);
	}
}