package commandRegistry;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import a2.Camera;
import a2.World3D;

public class Space_Command extends AbstractAction {
	private World3D remote;
	public Space_Command(World3D remote) {
		super();
		this.remote = remote;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("INPUT: SPACEBAR  |  TOGGLE AXIS");
		remote.toggleAxis();
	}

}
