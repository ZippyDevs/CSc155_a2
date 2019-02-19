package a2;
import graphicslib3D.Matrix3D;
import graphicslib3D.Vector3D;

public class Camera {
	
	private double        uAxis;
	private double        vAxis;
	private double        nAxis;
	private float     pitch = 0;
	private float     pan   = 0;
	private Matrix3D          m;



	public Camera(double x, double y, double z) {
		m = new Matrix3D();

	}
	public void computeView() {
		// REQUIRED FOR A2 //
		

		float sinPitch = (float)Math.sin(Math.toRadians(pitch));
		float sinPan   = (float)Math.sin(Math.toRadians(pan)  );
		float cosPitch = (float)Math.cos(Math.toRadians(pitch));
		float cosPan   = (float)Math.cos(Math.toRadians(pan)  );

		Vector3D newAxis = new Vector3D(uAxis, vAxis, nAxis);
		Vector3D cameraU = new Vector3D(cosPan, 0, -sinPan);
		Vector3D cameraV = new Vector3D(sinPan * sinPitch, cosPitch, cosPan * sinPitch);
		Vector3D cameraN = new Vector3D(sinPan * cosPitch, -sinPitch, cosPitch * cosPan);
		
		double[] mArray  = new double[] {cameraU.getX(), cameraV.getX(), cameraN.getX(),                          0,
						  				 cameraU.getY(), cameraV.getY(), cameraN.getY(),                          0,
										 cameraU.getZ(), cameraV.getZ(), cameraN.getZ(),                          0,
										-(cameraU.dot(newAxis)), -(cameraV.dot(newAxis)),-(cameraN.dot(newAxis)), 1						   };
		m.setValues(mArray);
		
		
	}
	
	public Matrix3D getView() {
		return m;
	}
	
	 
	
	//----------WASD CMDS----------------------------//
	public void moveForward()  { // W KEY
		this.nAxis = nAxis - .05;
		computeView();
	}
	public void moveBackward() { // S KEY
		this.nAxis = nAxis + .05;
		computeView();
	}
	public void moveLeft()     { // A KEY
		this.uAxis = uAxis - .05;
		computeView();
	}
	public void moveRight()    { // D KEY
		this.uAxis = uAxis + .05;
		computeView();
	}
	public void moveUp()       { // Q KEY
		this.vAxis = vAxis + .05;
		computeView();
	}
	public void moveDown()     { // E KEY
		this.vAxis = vAxis - .05;
		computeView();
	}
	//----------------------------------------------//
	
	//---------ARROW KEY CMDS-----------------------//
	public void pitchDown()  { //    UP ARROW
		this.pitch++;
		computeView();
	}
	public void pitchUp()    { //  DOWN ARROW
		this.pitch--;
		computeView();
	}
	public void panLeft()  { //    LEFT ARROW
		this.pan--;
		computeView();
	}
	public void panRight() { //   RIGHT ARROW
		this.pan++;
		computeView();
	}
	

	//----------------------------------------------//
	
	//-----------------CAMERA RESET-----------------//
	public void cameraReset() { // R KEY
		
	}
	//----------------------------------------------//


}
