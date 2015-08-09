package box2d_game;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
 
public class Box2D_Game extends ApplicationAdapter implements InputProcessor {	  
	World world;
	SpriteBatch batch;
    private Vector<Game_object> object = new Vector<Game_object>();
    Body bodyEdgeScreen;
    Body bodyEdgeScreen_left;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;
    OrthographicCamera camera;
    BitmapFont font;
    boolean game_mode = false;
    float torque = 0.0f;
    boolean drawSprite = true;
    Vector3 testPoint = new Vector3();
    Body hitBody = null;
    private MouseJoint mouseJoint = null;
   // private float angle = 0.0f;
    @Override
    public void create()
    {
        camera = new OrthographicCamera(54, 32);
        camera.position.set(0, 16, 0);
        world = new World(new Vector2(0, -20.0f),  false);
        batch = new SpriteBatch();
        //Bottom line
        BodyDef bodyDef_bottom = new BodyDef();
        bodyDef_bottom.type = BodyDef.BodyType.StaticBody;
        bodyDef_bottom.position.set(0,0);
        FixtureDef fixtureDef_bottom = new FixtureDef();
        EdgeShape edgeShape_bottom = new EdgeShape();
        edgeShape_bottom.set(-26, 1, 26, 1);
        fixtureDef_bottom.shape = edgeShape_bottom;
        bodyEdgeScreen = world.createBody(bodyDef_bottom);
        bodyEdgeScreen.createFixture(fixtureDef_bottom);
        edgeShape_bottom.dispose();
        //Left line
        BodyDef bodyDef_left = new BodyDef();
        bodyDef_left.type = BodyDef.BodyType.StaticBody;
        bodyDef_left.position.set(0,0);
        FixtureDef fixtureDef_left = new FixtureDef();
        EdgeShape edgeShape_left = new EdgeShape();
        edgeShape_left.set(-26, 1, -26, 54);
        fixtureDef_left.shape = edgeShape_left;
        bodyEdgeScreen = world.createBody(bodyDef_left);
        bodyEdgeScreen.createFixture(fixtureDef_left);
        edgeShape_left.dispose();
        //Right line
        BodyDef bodyDef_right = new BodyDef();
        bodyDef_right.type = BodyDef.BodyType.StaticBody;
        bodyDef_right.position.set(0,0);
        FixtureDef fixtureDef_right = new FixtureDef();
        EdgeShape edgeShape_right = new EdgeShape();
        edgeShape_right.set(26, 1, 26, 54);
        fixtureDef_right.shape = edgeShape_right;
        bodyEdgeScreen = world.createBody(bodyDef_right);
        bodyEdgeScreen.createFixture(fixtureDef_right);
        edgeShape_right.dispose();
        //shape.dispose();
        Gdx.input.setInputProcessor(this);
       
        debugRenderer = new Box2DDebugRenderer();
        font = new BitmapFont();
        font.setColor(Color.BLACK);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                // Check to see if the collision is between the second sprite and the bottom of the screen
                // If so apply a random amount of upward force to both objects... just because
             
            /*	if((contact.getFixtureA().getBody() == object.get(0).get_body() &&
                        contact.getFixtureB().getBody() == object.get(1).get_body())
                        ||
                        (contact.getFixtureA().getBody() == object.get(1).get_body() &&
                                contact.getFixtureB().getBody() == object.get(0).get_body())) 
            			
            	{
                        System.out.println("collision");
                }*/
            }
            @Override
            public void endContact(Contact contact) {
            }
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });
    }
    public void add_rectangle(float x, float y, float box_width, float box_height, float density, float restitution, float angle, String name_texture)
    {
        Game_object obj = new Rectangle();
        obj.set_coordinate(x, y);
        obj.set_box(box_width,  box_height);
        obj.angle = angle;
        obj.set_fixture(density, restitution);
        obj.create(world);
        obj.set_type("rectangle");
        obj.set_image(name_texture);
        object.addElement(obj);    
    }
    public void add_ball(float x, float y, float radius, float density, float restitution, float angle, String name_texture)
    {
        Game_object obj2 = new Circle();
        obj2.set_coordinate(x, y);
        obj2.set_radius(radius);
        obj2.set_fixture(density, restitution);
        obj2.angle = angle;
        obj2.create(world);
        obj2.set_type("ball");
        obj2.set_image(name_texture);
        object.addElement(obj2);    
    }
   public void  add_static_body(float x, float y,  float box_width, float box_height, float angle, String name_texture)
    {
        Game_object obj = new Static_body();
        obj.set_coordinate(x, y);
        obj.set_box(box_width, box_height);
        obj.angle = angle;
        obj.set_type("static_body");
        obj.set_image(name_texture); 
        obj.create(world);
        object.addElement(obj);
    }
    public static int random_int(int Min, int Max)
    {
    	return (Min + (int)(Math.random() * ((Max - Min) + 1)));
    }
	public  void Split_str(String s1)
    {
           String[] arr = s1.split(" ");
           for(int i = 0; i < arr.length; i++)
           {
        	   if(i == arr.length-1)
        	   {
        		   if(arr[0].equals("rectangle"))
            	   {
            		   add_rectangle(Float.valueOf(arr[1]), Float.valueOf(arr[2]), 
            				   Float.valueOf(arr[3]), Float.valueOf(arr[4]), 
            				   Float.valueOf(arr[5]), Float.valueOf(arr[6]), 
            				   Float.valueOf(arr[7]), String.valueOf(arr[8]));     
            	   }
        		   if(arr[0].equals("ball"))
            	   {
            		   add_ball(Float.valueOf(arr[1]), Float.valueOf(arr[2]),   //position
            				   Float.valueOf(arr[3]), //radius
            				   Float.valueOf(arr[4]),  Float.valueOf(arr[5]),   //fixture
            				   Float.valueOf(arr[6]),  String.valueOf(arr[7])); //angle and texture  
            	   }
        		   if(arr[0].equals("static_body"))
            	   {
        			   add_static_body(Float.valueOf(arr[1]), Float.valueOf(arr[2]),
        					   		   Float.valueOf(arr[3]), Float.valueOf(arr[4]),  
        					   		   Float.valueOf(arr[5]), String.valueOf(arr[6]));
            	   }
        		  
        	   }   
           }           
    }
	public void Load_level(String n_level)
	{
	       BufferedReader br = null;
	        try
	        {
	                String sCurrentLine;
	                br = new BufferedReader(new FileReader(n_level));
	                while ((sCurrentLine = br.readLine()) != null)
	                {
	                        Split_str(sCurrentLine);
	                }
	        } catch (IOException e)
	        {
	                e.printStackTrace();
	        }
	        finally
	        {
	                try
	                {
	                        if (br != null)br.close();
	                }
	                catch (IOException ex)
	                {
	                        ex.printStackTrace();
	                }
	        }
	}
    public void render() {
        camera.update();
        if(game_mode == true)
        world.step(1f/60f, 6, 2);
      //  Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClearColor(0,0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.getProjectionMatrix().set(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(1, 1, 0);
        batch.begin();
        
        if(drawSprite)
        {
        	
        	for(int i = 0; i < object.size(); i++)
        	{
        	 	batch.draw(object.get(i).get_texture(), object.get(i).get_body().getPosition().x-1, object.get(i).get_body().getPosition().y-1, // the bottom left corner of the box, unrotated
        				1f, 1f, // the rotation center relative to the bottom left corner of the box
        				2,  2, // the width and height of the box
        				object.get(i).get_a(), object.get(i).get_b(), // the scale on the x- and y-axis
        				MathUtils.radiansToDegrees * object.get(i).get_body().getAngle()); // the rotation angle*/
        	}
        } 
        batch.end();
        debugRenderer.render(world, debugMatrix);
    }
    QueryCallback callback = new QueryCallback() {
            @Override
            public boolean reportFixture (Fixture fixture) 
            {
                    if (fixture.testPoint(testPoint.x, testPoint.y)) 
                    {
                            hitBody = fixture.getBody();
                            return false;
                    } 
                    else
                            return true;
            }
    };
    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        for(int i = 0; i < object.size(); i++)
    	{
        	object.get(i).dispose();
    	}
    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }
    @Override
    public boolean keyUp(int keycode) 
    {
        if(keycode == Input.Keys.SPACE)
        {
           game_mode = !game_mode;
           for(int i = 0; i < object.size(); i++)
           {
        	   object.get(i).set_start_position(object.get(i).start_x, object.get(i).start_y);  
           }
        }
        //Add new figure
        if(keycode == Input.Keys.NUM_1)
        {
        	add_rectangle((float)random_int(-24, 24), (float)random_int(0, 26), 10.0f,  1.0f, (float)1, 0.1f,0.0f,  "data/Wood.jpg");     
        }
        if(keycode == Input.Keys.NUM_2)
        {
        	add_ball(random_int(-24, 24), random_int(0, 26), 2f, 4.1f,  0.3f, 0.0f, "data/ball.png");    
        }
        if(keycode == Input.Keys.NUM_3)
        {
        	add_rectangle(random_int(-24, 24), random_int(0, 26), 3.0f,  3.0f, 15.0f, 0.2f,0.0f, "data/metall.jpg");
        }
        if(keycode == Input.Keys.NUM_4)
        {
           add_rectangle(random_int(-20, 20), random_int(0, 21), 3.0f,  3.0f, 0.6f, 0.2f, 0.0f,"data/wood_1.jpg");
        }
        if(keycode == Input.Keys.NUM_5)
        {
        	add_static_body(0, 1, 8.0f,  1.6f, 0.0f, "data/Wood.jpg");
        }
        if(keycode == Input.Keys.NUM_6)
        {
        	//add_block_hinge()
        	Block_hinge b = new Block_hinge();
        	b.set_world(world);
        	b.set_coordinate(7.0f, 3.0f);
        	b.set_box(7, 2);
        	b.set_fixture(2.0f, 0.0f);
        	object.addElement(b.get_obj2());
        	object.addElement(b.get_obj1());
        	b.create();
        }
        if(keycode == Input.Keys.W)
        {
        	String str = "";
        	Writer w  = new Writer("D:/level1.txt");
        	for(int j = 0; j < object.size(); j++)
        	{
        		if(object.get(j).get_type() == "rectangle")
        		{
        			str = object.get(j).get_type() + " " + object.get(j).start_x + " " + object.get(j).start_y + " " +
        					object.get(j).get_a() + " " + object.get(j).get_b() + " " + 
        					object.get(j).density + " " + object.get(j).restitution + " " +
        					object.get(j).angle + " " +
        					object.get(j).path_texture;
            		w.write(str);
        		}
        		if(object.get(j).get_type() == "static_body")
        		{
        			str = object.get(j).get_type() + " " + object.get(j).start_x + " " + object.get(j).start_y + " " +
        					object.get(j).get_a() + " " + object.get(j).get_b() + " " + 
        					object.get(j).angle + " " + object.get(j).path_texture;
            		w.write(str);
        		}
        		if(object.get(j).get_type() == "ball")
        		{
        			str = object.get(j).get_type() + " " + object.get(j).start_x + " " + object.get(j).start_y + " " +
        					object.get(j).get_a() + " " + 
        					object.get(j).density + " " + object.get(j).restitution + " " +
        					object.get(j).angle + " " +
        					object.get(j).path_texture;
            		w.write(str);
        		}
        	}
        }
        if(keycode == Input.Keys.L)
        {
              Load_level("D:/level1.txt");
        }
        if(keycode == Input.Keys.BACKSPACE)
        {
        	  for(int i = 0; i < object.size(); i++)
              {
           	    if(hitBody == object.get(i).get_body())
           	    {
           	    	object.get(i).get_body().setActive(false);
           	    	//object.get(i).get_body().setUserData(null);
           	    	//world.destroyBody(object.get(i).get_body());
           	    	object.remove(i);
           	    	break;
           	    }
              }
        }
        return true;
    }
 
    @Override
    public boolean keyTyped(char character) {
        return false;
    }
 
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        //square.applyForce(1f,1f,screenX,screenY,true);
    	// translate the mouse coordinates to world coordinates
        testPoint.set(screenX, screenY, 0);
        camera.unproject(testPoint);    
        hitBody = null;
        world.QueryAABB(callback, testPoint.x - 0.1f, testPoint.y - 0.1f, testPoint.x + 0.1f, testPoint.y + 0.1f);
        for(int i = 0; i < object.size(); i++)
        {
        	if(hitBody == object.get(i).get_body()) object.get(i).mouse_dragged = true;
        }
        if (hitBody != null  )  //&& hitBody != square && hitBody != ball2.get_body()
        {
        	//left_mouse_pressed = true;
                MouseJointDef def = new MouseJointDef();
                def.bodyA = bodyEdgeScreen;
                def.bodyB = hitBody;
                def.collideConnected = true;
                def.target.set(testPoint.x, testPoint.y);
              //  def.maxForce = 1000.0f * hitBody.getMass();
              //  System.out.println("left mouse pressed");
                mouseJoint = (MouseJoint)world.createJoint(def);
                hitBody.setAwake(true);
        } 
        System.out.println("mouse down");
        return true;
    }
 
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    	 // if a mouse joint exists we simply destroy it
    	hitBody = null;
        if (mouseJoint != null) {
                world.destroyJoint(mouseJoint);
                mouseJoint = null;
        }
        return false;
    }
 
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
    	  testPoint.set(screenX, screenY, 0);
          camera.unproject(testPoint);
          if(hitBody != null) //&& hitBody != ball2.get_body()
          { 
        	  for(int i = 0; i < object.size(); i++)
              {
              	if(hitBody == object.get(i).get_body())
              	{
              		if(object.get(i).mouse_moved == true)
              		{
              			if(object.get(i).get_type() == "part_hinge_static")
              			{
              				object.get(i-1).set_coordinate(testPoint.x, testPoint.y); //remember position
              				object.get(i-1).get_body().setTransform(testPoint.x, testPoint.y, i); //Object will be moving right away
              			}
              			if(object.get(i).get_type() == "part_hinge_dynamic")
              			{
              				object.get(i+1).set_coordinate(testPoint.x, testPoint.y);  //remember position
              				object.get(i+1).get_body().setTransform(testPoint.x, testPoint.y, i);
              			}
              			object.get(i).set_coordinate(testPoint.x, testPoint.y);  //remember position
                  		hitBody.setTransform(testPoint.x, testPoint.y, object.get(i).angle);
              		}
              	}
              }
      
          }   
       return false;
    }
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
    	 testPoint.set(screenX, screenY, 0);
         camera.unproject(testPoint);
        // System.out.println("testPoint.x = " + testPoint.x);
        // System.out.println("testPoint.y = " + testPoint.y);
        return false;
    }
    @Override
    public boolean scrolled(int amount) {
        if(hitBody != null)	
        { 
         	for(int i = 0; i < object.size(); i++)
            {
             	if(hitBody == object.get(i).get_body())
             	{
             		if(amount == -1)
                	{
             			object.get(i).angle = object.get(i).angle + 0.1f;
	             		hitBody.setTransform(testPoint.x, testPoint.y, object.get(i).angle);
                	}
             		if(amount == 1)
                	{
	             		object.get(i).angle = object.get(i).angle - 0.1f;
	             		hitBody.setTransform(testPoint.x, testPoint.y, object.get(i).angle);
                	}
             	}
            }
        }
        return false;
    }
}
