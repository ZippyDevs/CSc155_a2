package commandRegistry;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import a2.Camera;

//	MOVE LEFT	//
public class S_Command extends AbstractAction{
	private Camera remote;
	public S_Command(Camera remote) {
		// TODO Auto-generated constructor stub
		super();
		this.remote = remote;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("INPUT: S  |  BACKWARD");
		remote.moveBackward();
	}

}
