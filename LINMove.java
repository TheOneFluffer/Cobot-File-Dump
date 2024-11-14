package application;

import javax.inject.Inject;
import com.kuka.roboticsAPI.applicationModel.RoboticsAPIApplication;
import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;
import com.kuka.roboticsAPI.deviceModel.LBR;

/**
 * Implementation of a robot application.
 */
public class LINMove extends RoboticsAPIApplication {
	@Inject
	private LBR lbr;

	@Override
	public void initialize() {
		// initialize your application here
	}

	@Override
	public void run() {
		// Move to StartPos at 20% joint velocity
		lbr.move(ptp(getApplicationData().getFrame("/StartPos")).setJointVelocityRel(0.2));

		// Linear move to X100 at 100mm/s
		lbr.move(lin(getApplicationData().getFrame("/X100")).setCartVelocity(100));

		// Linear move back to StartPos at 200mm/s
		lbr.move(lin(getApplicationData().getFrame("/StartPos")).setCartVelocity(200));
	}
}
