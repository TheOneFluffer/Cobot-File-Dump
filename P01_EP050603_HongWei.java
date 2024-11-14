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
public class P01_EP050603_HongWei extends RoboticsAPIApplication {
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
		//Start...
		lbr.detachAll(); // detach all tool from robot
		fixedPart.attachTo(lbr.getFlange()); // attach tool to robot
		
		//1. Move to zero position
		lbr.move(ptpHome().setJointVelocityRel(0.2));// ptp move to zero position at 20% speed
		//End of 1
		
		//2. Move to start position
		lbr.move(ptp(0,0,0,(Math.PI/2)*(-1),0,Math.PI/2,0).setJointVelocityRel(0.2));// cartesian move to start at 20% speed
		//End of 2
		
		//3. Move to frame P1 (w.r.t FixedPartTCP)
		fixedPart.getFrame("FixedPartTCP").move(lin(getApplicationData().getFrame("/A1P1")).setCartVelocity(100));
		//End of 3
		
		//4. Use MotionBatch to do the following movements
		//Move to coordinates "1347".
		MotionBatch mb = new MotionBatch(
				linRel(0,0,0).setCartVelocity(200), //Position 1
				linRel(0,200,0).setCartVelocity(200), //Position 3
				linRel(-75,-200,0).setCartVelocity(200), //Position 4
				linRel(-75,0,0).setCartVelocity(200), //Position 7
				lin(getApplicationData().getFrame("/A1P1")).setCartVelocity(200)
				//Teach frame
				);
		fixedPart.getFrame("FixedPartTCP").move(mb);
		//End of 4
		
		//5 Return start pos
		lbr.move(lin(getApplicationData().getFrame("/StartPos")).setCartVelocity(200));
		//5 Return start pos
	}
}