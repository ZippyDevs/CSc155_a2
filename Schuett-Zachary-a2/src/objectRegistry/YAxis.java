package objectRegistry;

public class YAxis {
	private float[] location = {0.0f, 0.0f, 0.0f};
	
	float[] y_positions =
	{0.0f, 0.0f, 0.0f, 0.05f, 0.0f, 0.0f, 0.0f, 100.0f, 0.0f 	};
	
	float[] yTexCoords = 
	{0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f };
	
	
	public YAxis() {}
	public float[] getLocation() {
		return location;
	}
	public void setYLocation(float[] location) {
		this.location = location;
	}
	public float[] getYPosition() {
		return y_positions;
	}
	public void setYPosition(float[] y_positions) {
		this.y_positions = y_positions;
	}
	
	public float[] getYTex() {
		return yTexCoords;
	}
	public void setYTex(float[] yTexCoords) {
		this.yTexCoords = yTexCoords;
	}
}