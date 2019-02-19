package objectRegistry;

public class Custom {
	private float[] location = {0.0f, 0.0f, 0.0f};
	private float[] custom_positions = {
		0.0f, 0.0f, 0.5f, 0.0f, 0.5f, 0.5f, 
		0.0f, 0.0f, 0.0f, 0.5f, -0.5f, 0.5f,
		0.0f, 0.0f, -0.5f, 0.0f, -0.5f, -0.5f,
		0.0f, 0.0f, 0.0f, -0.5f, 0.5f, -0.5f 
	};
	
	public Custom() {}
	
	public float[] getLocation() {
		return location;
	}
	public void setLocation(float[] location) {
		this.location = location;
	}
	public float[] getPosition() {
		return custom_positions;
	}
	public void setPosition(float[] cube_positions) {
		this.custom_positions = cube_positions;
	}
}
