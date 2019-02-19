package commandRegistry;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import a2.Camera;

//	MOVE FORWARD //
public class W_Command extends AbstractAction{

	private Camera remote;

	public W_Command(Camera remote) {
		// TODO Auto-generated constructor stub
		super();
		this.remote = remote;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("INPUT: W  |  FORWARD");
		remote.moveForward();
	}

}
