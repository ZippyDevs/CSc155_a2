package commandRegistry;
//	MOVE BACKWARDS	//
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import a2.Camera;

public class A_Command extends AbstractAction  {
	private Camera remote;
	public A_Command(Camera remote) {
		// TODO Auto-generated constructor stub
		super();
		this.remote = remote;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("INPUT: A  |  LEFT");
		remote.moveLeft();
	}

}
