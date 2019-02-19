 package objectRegistry;

public class Pyramid {
	private float location[] = {0.0f, 0.0f, 0.0f};
	
	float[] pyramid_positions =
			//DRAW A SATELLITE//
	{	//-1.0f, -1.0f,  1.0f,  1.0f, -1.0f,  1.0f,  0.0f,  1.0f,  0.0f, //front
		 1.0f, -1.0f,  1.0f,  1.0f, -1.0f, -1.0f,  0.0f,  1.0f,  0.0f, //right
		 //1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f,  0.0f,  1.0f,  0.0f, //back
		-1.0f, -1.0f, -1.0f, -1.0f, -1.0f,  1.0f,  0.0f,  1.0f,  0.0f, //left
		//-1.0f, -1.0f, -1.0f,  1.0f, -1.0f,  1.0f, -1.0f, -1.0f,  1.0f, //LF
		 //1.0f, -1.0f,  1.0f, -1.0f, -1.0f, -1.0f,  1.0f, -1.0f, -1.0f  //RR
	};
	float[] tex_coords={
			0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
			0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
			0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
			0.0f, 0.0f, 1.0f, 0.0f, 0.5f, 1.0f,
			0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
			1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f
	};
	
	public Pyramid() {}
	
	public float[] getLocation() {
		return location;
	}
	public void setLocation(float[] location) {
		this.location = location;
	}
	public float[] getPosition() {
		return pyramid_positions;
	}
	public void setPosition(float[] pyramid_positions) {
		this.pyramid_positions = pyramid_positions;
	}
	public float[] getTex() {
		return tex_coords;
	}
	public void setTex(float[] tex_coords) {
		this.tex_coords = tex_coords;
	}
	
}
