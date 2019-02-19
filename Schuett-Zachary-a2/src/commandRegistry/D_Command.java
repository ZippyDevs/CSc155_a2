package commandRegistry;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import a2.Camera;

//	MOVE RIGHT	//
public class D_Command extends AbstractAction {
	private Camera remote;
	public D_Command(Camera remote) {
		// TODO Auto-generated constructor stub
		super();
		this.remote = remote;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("INPUT: D  |  RIGHT");
		remote.moveRight();
	}

}
