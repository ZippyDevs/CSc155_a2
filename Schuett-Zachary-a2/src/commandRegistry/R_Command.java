package commandRegistry;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import a2.Camera;

public class R_Command extends AbstractAction {
	private Camera remote;
	public R_Command(Camera remote) {
		super();
		this.remote = remote;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("INPUT: R  |  RESET VIEW TO DEFAULT");
		remote.cameraReset();
	}

}
