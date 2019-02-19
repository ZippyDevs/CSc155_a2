  
	package a2;
	import objectRegistry.*;
	import commandRegistry.*;
	import a2.Camera;
	import graphicslib3D.*;
	import graphicslib3D.GLSLUtils.*;

import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.*;
	import javax.swing.*;
	import static com.jogamp.opengl.GL4.*;
	import com.jogamp.opengl.*;
	import com.jogamp.opengl.awt.GLCanvas;
	import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.common.nio.Buffers;
	import com.jogamp.opengl.GLContext;

	
	public class World3D extends JFrame implements GLEventListener
	{	
		private float cameraX,  cameraY,   cameraZ;		
		private Camera remote = new Camera(0, 0, 25);
		
		private W_Command       wKeystroke = new W_Command      (remote);
		private A_Command       aKeystroke = new A_Command      (remote);
		private S_Command       sKeystroke = new S_Command      (remote);
		private D_Command       dKeystroke = new D_Command      (remote);
		private Q_Command       qKeystroke = new Q_Command      (remote);
		private E_Command       eKeystroke = new E_Command      (remote);
		private R_Command       zKeystroke = new R_Command      (remote);
		private U_Arrow_Command uKeystroke = new U_Arrow_Command(remote);
		private D_Arrow_Command nKeystroke = new D_Arrow_Command(remote);
		private L_Arrow_Command lKeystroke = new L_Arrow_Command(remote);
		private R_Arrow_Command rKeystroke = new R_Arrow_Command(remote);
		private Space_Command   _Keystroke = new Space_Command  (this);
		
		private GLCanvas myCanvas;
		private int rendering_program;
		private int vao[] = new int[1];
		private int vbo[] = new int[14];

		private float cubeLocX, cubeLocY, cubeLocZ;
		private float pyrLocX,  pyrLocY,   pyrLocZ;
		private float sprLocX,  sprLocY,   sprLocZ;
		private GLSLUtils util = new GLSLUtils();
		
		private Cube    cube;
		private Pyramid pyramid;
		private Sphere  sphere;
		private Custom  custom;
		
		private int sunTexture;
		private int planet1Texture;
		private int moon1Texture;
		private int planet2Texture;
		private int moon2Texture;
		private int redTex;
		private int grnTex;
		private int bluTex;
		private int customTexture;
		

		
		private Texture joglSun;
		private Texture joglPlanet1;
		private Texture joglMoon1;
		private Texture joglPlanet2;
		private Texture joglMoon2;
		private Texture joglRed;
		private Texture joglGrn;
		private Texture joglBlu;
		private Texture joglCus;
		
		private boolean toggleAxis = false;
		
		private	MatrixStack mvStack = new MatrixStack(20);
				
		public World3D()
		{	setTitle("Zachary Schuett | Assignment 2");
			setSize(600, 600);
			myCanvas = new GLCanvas();
			myCanvas.addGLEventListener(this);
			getContentPane().add(myCanvas);
			this.setVisible(true);
			FPSAnimator animator = new FPSAnimator(myCanvas, 50);
			animator.start();
			
			cube    = new Cube();                                       //create cube object
			pyramid = new Pyramid();                                    //create pyramid object
			sphere  = new Sphere(25);                                   //create sphere object			
			
			//---------------------KEY COMMANDS-------------------------//
			JComponent contentPane = (JComponent)this.getContentPane();
			int        mapName     = JComponent.WHEN_IN_FOCUSED_WINDOW;
			InputMap   imap        = contentPane.getInputMap (mapName);
			ActionMap  amap        = contentPane.getActionMap();
			
			KeyStroke wKey     = KeyStroke.getKeyStroke(    'w');
			KeyStroke aKey     = KeyStroke.getKeyStroke(    'a');
			KeyStroke sKey     = KeyStroke.getKeyStroke(    's');
			KeyStroke dKey     = KeyStroke.getKeyStroke(    'd');
			KeyStroke qKey     = KeyStroke.getKeyStroke(    'q');
			KeyStroke eKey     = KeyStroke.getKeyStroke(    'e');
			KeyStroke zKey     = KeyStroke.getKeyStroke(    'r');
			KeyStroke uKey     = KeyStroke.getKeyStroke(   "UP");
			KeyStroke nKey     = KeyStroke.getKeyStroke( "DOWN");
			KeyStroke lKey     = KeyStroke.getKeyStroke( "LEFT");
			KeyStroke rKey     = KeyStroke.getKeyStroke("RIGHT");
			KeyStroke _Key	   = KeyStroke.getKeyStroke("SPACE");

			imap.put(wKey, "wCom");
			imap.put(aKey, "aCom");
			imap.put(sKey, "sCom");
			imap.put(dKey, "dCom");
			imap.put(qKey, "qCom");
			imap.put(eKey, "eCom");
			imap.put(zKey, "zCom");
			imap.put(uKey, "uCom");
			imap.put(nKey, "nCom");
			imap.put(lKey, "lCom");
			imap.put(rKey, "rCom");
			imap.put(_Key, "_Com");
			
			amap.put("wCom", wKeystroke);
			amap.put("aCom", aKeystroke);
			amap.put("sCom", sKeystroke);
			amap.put("dCom", dKeystroke);
			amap.put("qCom", qKeystroke);
			amap.put("eCom", eKeystroke);
			amap.put("zCom", zKeystroke);
			amap.put("uCom", uKeystroke);
			amap.put("nCom", nKeystroke);
			amap.put("lCom", lKeystroke);
			amap.put("rCom", rKeystroke);
			amap.put("_Com", _Keystroke);
			
			this.requestFocus();
	      //------------------------------------------------------------//			
		}

		public void display(GLAutoDrawable drawable)
		{	GL4 gl = (GL4) GLContext.getCurrentGL();

			gl.glClear(GL_DEPTH_BUFFER_BIT);
			float bkg[] = { 0.0f, 0.0f, 0.0f, 1.0f };
			FloatBuffer bkgBuffer = Buffers.newDirectFloatBuffer(bkg);
			gl.glClearBufferfv(GL_COLOR, 0, bkgBuffer);
			gl.glClear(GL_DEPTH_BUFFER_BIT);
			gl.glUseProgram(rendering_program);

			int mv_loc = gl.glGetUniformLocation(rendering_program, "mv_matrix");
			int proj_loc = gl.glGetUniformLocation(rendering_program, "proj_matrix");

			float aspect = (float) myCanvas.getWidth() / (float) myCanvas.getHeight();
			Matrix3D pMat = perspective(60.0f, aspect, 0.1f, 1000.0f);

			// push view matrix onto the stack
			//----------------------- Camera
			mvStack.pushMatrix();
			mvStack.translate(-cameraX, -cameraY, -cameraZ);
			mvStack.multMatrix(remote.getView());
			double amt = (double)(System.currentTimeMillis())/1000.0;

			gl.glUniformMatrix4fv(proj_loc, 1, false, pMat.getFloatValues(), 0);
			//----------------------- Axis	
			if(toggleAxis) {
				//RED AXIS
				mvStack.pushMatrix();
				mvStack.translate(0, 0, 0);
				//mvStack.pushMatrix();
				gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
				gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
				gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[5]);
				gl.glEnableVertexAttribArray(0);
				gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[6]);
				gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
				gl.glEnableVertexAttribArray(1);
				gl.glActiveTexture(GL_TEXTURE0);
				gl.glBindTexture(GL_TEXTURE_2D, redTex);
				gl.glEnable(GL_DEPTH_TEST);
				gl.glDrawArrays(GL_LINES, 0, 2);
				mvStack.popMatrix();
				
				//GREEN AXIS
				mvStack.pushMatrix();
				mvStack.translate(0, 0, 0);
				//mvStack.pushMatrix();
				gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
				gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
				gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[7]);
				gl.glEnableVertexAttribArray(0);
				gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[8]);
				gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
				gl.glEnableVertexAttribArray(1);
				gl.glActiveTexture(GL_TEXTURE0);
				gl.glBindTexture(GL_TEXTURE_2D, grnTex);
				gl.glEnable(GL_DEPTH_TEST);
				gl.glDrawArrays(GL_LINES, 0, 2);
				mvStack.popMatrix();
				
				//BLUE AXIS
				mvStack.pushMatrix();
				mvStack.translate(0, 0, 0);
				//mvStack.pushMatrix();
				gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
				gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
				gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[9]);
				gl.glEnableVertexAttribArray(0);
				gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[10]);
				gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
				gl.glEnableVertexAttribArray(1);
				gl.glActiveTexture(GL_TEXTURE0);
				gl.glBindTexture(GL_TEXTURE_2D, bluTex);
				gl.glEnable(GL_DEPTH_TEST);
				gl.glFrontFace(GL_CCW);
				gl.glDrawArrays(GL_LINES, 0, 2);
				mvStack.popMatrix();
				//mvStack.popMatrix();
				//mvStack.popMatrix();
				//mvStack.popMatrix();
			}

			
			// ----------------------  Sun
			mvStack.pushMatrix(); //1
			mvStack.translate(sprLocX, sprLocY, sprLocZ);
			mvStack.pushMatrix(); //2
			mvStack.rotate((System.currentTimeMillis())/50.0,0.0,1.0,0.22);
			mvStack.scale(3, 3, 3);
			gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[2]);
			gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(0);
			gl.glEnable(GL_DEPTH_TEST);
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[3]);
			gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(1);
			gl.glActiveTexture(GL_TEXTURE0);
			gl.glBindTexture(GL_TEXTURE_2D, sunTexture);
			int sphereNumVertices = sphere.getIndices().length;
			gl.glDrawArrays(GL_TRIANGLES, 0, sphereNumVertices); 
			mvStack.popMatrix(); //1
			
			//-----------------------  Planet I  
			mvStack.pushMatrix(); //2
			mvStack.translate(Math.sin(amt)*6.0f, Math.cos(amt)*6.0f, 0);
			mvStack.pushMatrix(); //3
			mvStack.rotate((System.currentTimeMillis())/50.0,4.0,1.0,0.22);
			//mvStack.scale(3, 3, 3);
			gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[2]);
			gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(0);
			gl.glEnable(GL_DEPTH_TEST);
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[3]);
			gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(1);
			gl.glActiveTexture(GL_TEXTURE0);
			gl.glBindTexture(GL_TEXTURE_2D, planet1Texture);
			gl.glDrawArrays(GL_TRIANGLES, 0, sphereNumVertices); 
			mvStack.popMatrix(); //2

			//-----------------------  Moon I
			mvStack.pushMatrix(); //4
			mvStack.translate(Math.sin(amt*2)*4.0f, 0.0f, Math.cos(amt*2)*4.0f);			
			mvStack.pushMatrix(); //5
			mvStack.rotate((System.currentTimeMillis())/50.0,0.0,1.0,0.22);
			mvStack.scale(.5, .5,.5);
			gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[2]);
			gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(0);
			gl.glEnable(GL_DEPTH_TEST);
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[3]);
			gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(1);
			gl.glActiveTexture(GL_TEXTURE0);
			gl.glBindTexture(GL_TEXTURE_2D, moon1Texture);
			gl.glDrawArrays(GL_TRIANGLES, 0, sphereNumVertices); 
			mvStack.popMatrix(); //3
			mvStack.popMatrix(); //4
			mvStack.popMatrix(); //5    END OF FIRST GROUP
  			
			
			//-------------------------------------Planet II
			mvStack.pushMatrix(); //1
			mvStack.translate(Math.sin(amt)*8.0f, 0.0f, Math.cos(amt)*8.0f);			
			mvStack.pushMatrix(); //2
			mvStack.rotate((System.currentTimeMillis())/50.0,0.0,1.0,0.22);
			//mvStack.scale(3, 3, 3);
			gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[2]);
			gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(0);
			gl.glEnable(GL_DEPTH_TEST);
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[3]);
			gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(1);
			gl.glActiveTexture(GL_TEXTURE0);
			gl.glBindTexture(GL_TEXTURE_2D, planet2Texture);
			gl.glDrawArrays(GL_TRIANGLES, 0, sphereNumVertices); 
			mvStack.popMatrix(); //1
			
			//--------------------------------------Moon II
			mvStack.pushMatrix(); //3
			mvStack.translate(Math.sin(amt*1.5)*2.0f, 0.0f, Math.cos(amt*1.5)*2.0f);
			mvStack.pushMatrix(); //4
			mvStack.rotate((System.currentTimeMillis())/50.0,1.0,0.0,1.0);
			mvStack.scale(.5, .5, .5);
			gl.glUniformMatrix4fv(mv_loc, 1, false, mvStack.peek().getFloatValues(), 0);
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
			gl.glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(0);
			gl.glEnable(GL_DEPTH_TEST);
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[11]);
			gl.glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
			gl.glEnableVertexAttribArray(1);
			gl.glActiveTexture(GL_TEXTURE0);
			gl.glBindTexture(GL_TEXTURE_2D, customTexture);
			gl.glDrawArrays(GL_TRIANGLES, 0, 18); 
			mvStack.popMatrix(); //2
			mvStack.popMatrix(); //3
			mvStack.popMatrix();
			mvStack.popMatrix();
			mvStack.popMatrix(); //4  END OF SECOND GROUP
		}
		
		

		public void init(GLAutoDrawable drawable)
		{	GL4 gl = (GL4) GLContext.getCurrentGL();
			rendering_program = createShaderProgram();
			setupVertices();
			cameraX  = 0.0f;  cameraY = 0.0f;  cameraZ = 25.0f;
			cubeLocX = 0.0f; cubeLocY = 0.0f; cubeLocZ = 0.0f;
			pyrLocX  = 0.0f;  pyrLocY = 0.0f;  pyrLocZ = 0.0f;
			sprLocX  = 0.0f; sprLocY  = 0.0f;  sprLocZ = 0.0f;
			//cusLocX  = 0.0f;  cusLocY = 0.0f;  cusLocZ = 0.0f;
			
			joglSun    = loadTexture("src/a2/textures/sun.jpg");
			sunTexture = joglSun.getTextureObject();
			
			joglPlanet1    = loadTexture("src/a2/textures/jupiter.jpg");
			planet1Texture = joglPlanet1.getTextureObject();
			
			joglMoon1    = loadTexture("src/a2/textures/pluto.jpg");
			moon1Texture = joglMoon1.getTextureObject();
			
			joglPlanet2    = loadTexture("src/a2/textures/jupiter.jpg");
			planet2Texture = joglPlanet2.getTextureObject();
			
			joglMoon2    = loadTexture("src/a2/textures/moon.jpg");
			moon2Texture = joglMoon2.getTextureObject();
			
			joglRed = loadTexture("src/a2/textures/red.jpg");
			redTex  = joglRed.getTextureObject();
			
			joglGrn = loadTexture("src/a2/textures/green.jpg");
			grnTex  = joglGrn.getTextureObject();
			
			joglBlu = loadTexture("src/a2/textures/blue.jpeg");
			bluTex  = joglBlu.getTextureObject();
			
			joglCus = loadTexture("src/a2/textures/custom.jpg");
			customTexture = joglCus.getTextureObject();
			
			
		}

		private void setupVertices()
		{	GL4 gl = (GL4) GLContext.getCurrentGL();
		//--------------------------added Sphere framework--------------------//
		    Vertex3D[ ] vertices = sphere.getVertices();
		    int  [ ] indices     = sphere.getIndices();
		    float[ ] pvalues     = new float[indices.length*3]; // vertex positions
		    float[ ] tvalues     = new float[indices.length*2]; // texture coordinates
		    float[ ] nvalues     = new float[indices.length*3]; // normal vectors
		    
		    for (int i=0; i<indices.length; i++)
		    { pvalues[i*3]   = (float) (vertices[indices[i]]).getX();
		      pvalues[i*3+1] = (float) (vertices[indices[i]]).getY();
		      pvalues[i*3+2] = (float) (vertices[indices[i]]).getZ();
		      tvalues[i*2]   = (float) (vertices[indices[i]]).getS();
		      tvalues[i*2+1] = (float) (vertices[indices[i]]).getT();
		      nvalues[i*3]   = (float) (vertices[indices[i]]).getNormalX();
		      nvalues[i*3+1] = (float) (vertices[indices[i]]).getNormalY();
		      nvalues[i*3+2 ]= (float) (vertices[indices[i]]).getNormalZ();
		    }
		//--------------------------------------------------------------------//

			gl.glGenVertexArrays(vao.length, vao, 0);
			gl.glBindVertexArray(vao[0]);
			gl.glGenBuffers(vbo.length, vbo, 0);

			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
			FloatBuffer cubeBuf = Buffers.newDirectFloatBuffer(cube.getPosition());
			gl.glBufferData(GL_ARRAY_BUFFER, cubeBuf.limit()*4, cubeBuf, GL_STATIC_DRAW);
			
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
			FloatBuffer pyrBuf = Buffers.newDirectFloatBuffer(pyramid.getPosition());
			gl.glBufferData(GL_ARRAY_BUFFER, pyrBuf.limit()*4, pyrBuf, GL_STATIC_DRAW);
			
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[11]);
			FloatBuffer pyrTex = Buffers.newDirectFloatBuffer(pyramid.getTex());
			gl.glBufferData(GL_ARRAY_BUFFER, pyrTex.limit()*4, pyrTex, GL_STATIC_DRAW);
			
			//--------------------------Sphere framework------------------------------//
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[2]);
			FloatBuffer sphBuf = Buffers.newDirectFloatBuffer(pvalues);
			gl.glBufferData(GL_ARRAY_BUFFER, sphBuf.limit()*4, sphBuf, GL_STATIC_DRAW);
			//------------------------------------------------------------------------//
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[3]);
			FloatBuffer sprTex = Buffers.newDirectFloatBuffer(tvalues);
			gl.glBufferData(GL_ARRAY_BUFFER, sprTex.limit()*4, sprTex, GL_STATIC_DRAW);
			//--------------------------Custom Framework------------------------------//
			Custom custom = new Custom();
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[4]);
			FloatBuffer cusBuf = Buffers.newDirectFloatBuffer(custom.getPosition());
			gl.glBufferData(GL_ARRAY_BUFFER, cusBuf.limit()*4, cusBuf, GL_STATIC_DRAW);
			//------------------------------------------------------------------------//
			//------------------------X axis (R)--------------------------------------//
			XAxis xAxis = new XAxis();
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[5]);
			FloatBuffer redAxis = Buffers.newDirectFloatBuffer(xAxis.getXPosition());
			gl.glBufferData(GL_ARRAY_BUFFER, redAxis.limit()*4, redAxis, GL_STATIC_DRAW);
			//------------------------------------------------------------------------//
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[6]);
			FloatBuffer redTex = Buffers.newDirectFloatBuffer(xAxis.getXTex());
			gl.glBufferData(GL_ARRAY_BUFFER, redTex.limit()*4, redTex, GL_STATIC_DRAW);
			//------------------------Y axis (G)--------------------------------------//
			YAxis yAxis = new YAxis();
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[7]);
			FloatBuffer grnAxis = Buffers.newDirectFloatBuffer(yAxis.getYPosition());
			gl.glBufferData(GL_ARRAY_BUFFER, grnAxis.limit()*4, grnAxis, GL_STATIC_DRAW);
			//------------------------------------------------------------------------//
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[8]);
			FloatBuffer grnTex = Buffers.newDirectFloatBuffer(yAxis.getYTex());
			gl.glBufferData(GL_ARRAY_BUFFER, grnTex.limit()*4, grnTex, GL_STATIC_DRAW);
			//------------------------Z axis (B)--------------------------------------//
			ZAxis zAxis = new ZAxis();
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[9]);
			FloatBuffer bluAxis = Buffers.newDirectFloatBuffer(zAxis.getZPosition());
			gl.glBufferData(GL_ARRAY_BUFFER, bluAxis.limit()*4, bluAxis, GL_STATIC_DRAW);
			//------------------------------------------------------------------------//
			gl.glBindBuffer(GL_ARRAY_BUFFER, vbo[10]);
			FloatBuffer bluTex = Buffers.newDirectFloatBuffer(zAxis.getZTex());
			gl.glBufferData(GL_ARRAY_BUFFER, bluTex.limit()*4, bluTex, GL_STATIC_DRAW);
			//------------------------------------------------------------------------//
		}

		private Matrix3D perspective(float fovy, float aspect, float n, float f)
		{	float q = 1.0f / ((float) Math.tan(Math.toRadians(0.5f * fovy)));
			float A = q / aspect;
			float B = (n + f) / (n - f);
			float C = (2.0f * n * f) / (n - f);
			Matrix3D r = new Matrix3D();
			r.setElementAt(0,0,A);
			r.setElementAt(1,1,q);
			r.setElementAt(2,2,B);
			r.setElementAt(3,2,-1.0f);
			r.setElementAt(2,3,C);
			return r;
		}

		public static void main(String[] args) { new World3D(); }
		public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
		public void dispose(GLAutoDrawable drawable) {}

		private int createShaderProgram()
		{	GL4 gl = (GL4) GLContext.getCurrentGL();

			String vshaderSource[] = util.readShaderSource("src/a2/vert.shader");
			String fshaderSource[] = util.readShaderSource("src/a2/frag.shader");

			int vShader = gl.glCreateShader(GL_VERTEX_SHADER);
			int fShader = gl.glCreateShader(GL_FRAGMENT_SHADER);

			gl.glShaderSource(vShader, vshaderSource.length, vshaderSource, null, 0);
			gl.glShaderSource(fShader, fshaderSource.length, fshaderSource, null, 0);

			gl.glCompileShader(vShader);
			gl.glCompileShader(fShader);

			int vfprogram = gl.glCreateProgram();
			gl.glAttachShader(vfprogram, vShader);
			gl.glAttachShader(vfprogram, fShader);
			gl.glLinkProgram(vfprogram);
			return vfprogram;
		}

		public Texture loadTexture(String textureFileName)
		{	Texture tex = null;
			try { tex = TextureIO.newTexture(new File(textureFileName), false); }
			catch (Exception e) { e.printStackTrace(); }
			return tex;
		}
		
		public boolean getToggleAxis() {
			return toggleAxis;
		}
		public void toggleAxis() {
			this.toggleAxis = !toggleAxis;
		}
		
		

		
		
	}

