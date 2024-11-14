package application;

import javax.inject.Inject;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.geometricModel.Tool;

/**
 * Implementation of a robot application.
 */
public class TCPMove extends RoboticsAPIApplication {
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
		lbr.detachAll(); // detach all tool from robot
		fixedPart.attachTo(lbr.getFlange()); // attach tool to robot
		
		lbr.move(ptp(getApplicationData().getFrame("/StartPos")).setJointVelocityRel(0.2));
		lbr.move(lin(getApplicationData().getFrame("/X100")).setCartVelocity(100));
		
		fixedPart.getFrame("FixedPartTCP").move(lin(getApplicationData().getFrame("/X100")).setCartVelocity(100));
		fixedPart.getFrame("FixedPartTCP").move(lin(getApplicationData().getFrame("/StartPos")).setCartVelocity(200));
		
		lbr.move(lin(getApplicationData().getFrame("/StartPos")).setCartVelocity(200));
	}
}
