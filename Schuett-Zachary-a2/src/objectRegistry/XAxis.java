package objectRegistry;

public class XAxis {
	private float[] location = {0.0f, 0.0f, 0.0f};
	
	float[] x_positions =
	{0.0f, 0.0f, 0.05f, 100f, 0.0f, 0.05f, 0.0f, 0.05f, 0.0f 	};
	
	float[] xTexCoords = 
	{0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f };
	
	
	public XAxis() {}
	public float[] getLocation() {
		return location;
	}
	public void setLocation(float[] location) {
		this.location = location;
	}
	public float[] getXPosition() {
		return x_positions;
	}
	public void setXPosition(float[] x_positions) {
		this.x_positions = x_positions;
	}
	
	public float[] getXTex() {
		return x_positions;
	}
	public void setXTex(float[] xTexCoords) {
		this.x_positions = xTexCoords;
	}
}
