package objectRegistry;

public class ZAxis {
	private float[] location = {0.0f, 0.0f, 0.0f};
	
	float[] z_positions =
	{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 100.0f, 0.0f, -0.05f, 0.0f 	};
	
	float[] zTexCoords = 
	{0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f };
	
	
	public ZAxis() {}
	public float[] getLocation() {
		return location;
	}
	public void setLocation(float[] location) {
		this.location = location;
	}
	public float[] getZPosition() {
		return z_positions;
	}
	public void setZPosition(float[] z_positions) {
		this.z_positions = z_positions;
	}
	
	public float[] getZTex() {
		return zTexCoords;
	}
	public void setZTex(float[] zTexCoords) {
		this.zTexCoords = zTexCoords;
	}
}