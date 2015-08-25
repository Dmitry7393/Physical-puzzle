package game;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;

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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
public class Box2D_Game extends ApplicationAdapter implements InputProcessor {	  
	World world;
	SpriteBatch batch;
    private Vector<Game_object> object = new Vector<Game_object>();
    Body bodyEdgeScreen;
    //Body bodyEdgeScreen_left;*/
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
    private int level = 1;
    Body b1  = null;
    Body b2  = null;
    TextureRegion textureRegion;
    private float width_game_field = 18.0f;
    private String path_to_level = "D:/level1.txt";
boolean next_step = false;
    private boolean editor_mode = false;
    int count_check = 0; 
    private float delta_x;
    private float delta_y;
    Rope r;
	public void next_level()
	{
	  for(int i = 0; i < object.size(); i++)
	   {
		   object.get(i).setActive(false);
	   }
	  object.clear();
	    game_mode = false;
		level++;
		String str = "D:/level" + level + ".txt";
		System.out.println("str = " + str);
		Load_level(str);
	}
    @Override
    public void create()
    {
        camera = new OrthographicCamera(54, 32);
        camera.position.set(0, 16, 0);
        world = new World(new Vector2(0, -20.0f),  false);
        batch = new SpriteBatch();
        
        Load_level(path_to_level);
        //Bottom line
        textureRegion = new TextureRegion(new Texture(Gdx.files.internal("image/background.png")));  
        BodyDef bodyDef_bottom = new BodyDef();
        bodyDef_bottom.type = BodyDef.BodyType.StaticBody;
        bodyDef_bottom.position.set(0,-50);
        FixtureDef fixtureDef_bottom = new FixtureDef();
        EdgeShape edgeShape_bottom = new EdgeShape();
        edgeShape_bottom.set(-26, 1, 26, 1);
        fixtureDef_bottom.shape = edgeShape_bottom;
        bodyEdgeScreen = world.createBody(bodyDef_bottom);
        bodyEdgeScreen.createFixture(fixtureDef_bottom);
        edgeShape_bottom.dispose();
      //Right line
              BodyDef bodyDef_right = new BodyDef();
              bodyDef_right.type = BodyDef.BodyType.StaticBody;
               bodyDef_right.position.set(0,0);
                FixtureDef fixtureDef_right = new FixtureDef();
                EdgeShape edgeShape_right = new EdgeShape();
                edgeShape_right.set(18.7f, 1, 18.7f, 54);
                fixtureDef_right.shape = edgeShape_right;
             bodyEdgeScreen = world.createBody(bodyDef_right);
                bodyEdgeScreen.createFixture(fixtureDef_right);
              edgeShape_right.dispose();
        Gdx.input.setInputProcessor(this);
       
        debugRenderer = new Box2DDebugRenderer();
        font = new BitmapFont();
        font.setColor(Color.BLACK);

        world.setContactListener(new ContactListener() {
        	
            @Override
            public void beginContact(Contact contact) 
            {
            	//System.out.println("setContactListener");
                // Check to see if the collision is between the second sprite and the bottom of the screen
                // If so apply a random amount of upward force to both objects... just because
           //  if(level == 1 || level == 2)
         //    {
            	//if(b1.getPosition().x > 5) next_level();
            	 if(b1 != null && b2 != null)
            	 {
    	            	   if((contact.getFixtureA().getBody() == b1 &&
    	                           contact.getFixtureB().getBody() == b2)
    	                           ||
    	                           (contact.getFixtureA().getBody() == b2 &&
    	                                   contact.getFixtureB().getBody() == b1)) 	
    	               	 	{
    	            		  JOptionPane.showMessageDialog(null, "Level " + level + " completed!");
    	            		  next_step = true;
    	                    }  
            	 }  
            // }	
            /* if(count_check <= 1)
             {
            	 Body temp_moved;
	             Body temp_game;
	             for(int i = 0; i < object.size(); i++) //mouse_moved
	             {
		              if(object.get(i).mouse_moved == true)
		              {
		               temp_moved = object.get(i).get_body();
		                  for(int j = 0; j < object.size(); j++) //game object
		                  {
			                 //  if(i != j)  // && object.get(j).mouse_moved == false
			                  // {
			                    temp_game = object.get(j).get_body();
			                    if((contact.getFixtureA().getBody() == temp_moved &&
			                                     contact.getFixtureB().getBody() == temp_game)
			                                     ||
			                                     (contact.getFixtureA().getBody() == temp_game &&
			                                             contact.getFixtureB().getBody() == temp_moved))  
			                            {
				                    		System.out.println("collsion1!");
				                    		if(object.get(i).mouse_moved == true)
				                    		{
				                    			game_mode = false;
				                    			object.get(i).set_coordinate(21, 4);
				                    		}
			                            }
			                               
			                   }
		                   
		                  }
		              }
	             
	             }
	             count_check++;  */
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
    public void add_rectangle(float x, float y, float box_width, float box_height, float density, float restitution, float angle, String name_texture, boolean mouse_moved)
    {
        Game_object obj = new Rectangle();
        if(x > width_game_field)
        {
        	obj.allow_dec = false;
        	obj.allow_inc = true;
        	obj.stock_start_x = x;
        	obj.stock_start_y = y;
        	
        }
        else
        {
        	obj.allow_dec = true;
        }
        obj.set_coordinate(x, y);
        obj.set_box(box_width,  box_height);
        obj.angle = angle;
      
        obj.mouse_moved = mouse_moved;
        obj.set_fixture(density, restitution);
        obj.create(world);
        obj.set_type("rectangle");
        obj.set_image(name_texture);
        object.addElement(obj);    
    }
    public void add_ball(float x, float y, float radius, float density, float restitution, float angle, String name_texture, boolean mouse_moved)
    {
        Game_object obj2 = new Circle();
        if(x > width_game_field)
        {
        	obj2.allow_dec = false;
        	obj2.allow_inc = true;
        	obj2.stock_start_x = x;
        	obj2.stock_start_y = y;
        }
        else
        {
        	obj2.allow_dec = true;
        }
        obj2.set_coordinate(x, y);
        obj2.set_radius(radius);
        obj2.set_fixture(density, restitution);
        obj2.angle = angle;
        
        obj2.mouse_moved = mouse_moved;
        obj2.create(world);
        obj2.set_type("ball");
        obj2.set_image(name_texture);
        object.addElement(obj2);    
    }
   public void  add_static_body(float x, float y,  float box_width, float box_height, float restitution, float angle, String name_texture, boolean mouse_moved, boolean isSensor)
    {
        Game_object obj = new Static_body();
        if(x > width_game_field)
        {
        	obj.allow_dec = false;
        	obj.allow_inc = true;
        	obj.stock_start_x = x;
        	obj.stock_start_y = y;
        	//obj.maskBits = 2;
        }
        else
        {
        	obj.allow_dec = true;
        }
        obj.set_coordinate(x, y);
        obj.set_box(box_width, box_height);
        obj.angle = angle;
        obj.restitution = restitution;
        obj.isSensor = isSensor;
        obj.mouse_moved = mouse_moved;
        obj.set_type("static_body");
        obj.set_image(name_texture); 
        obj.create(world);
        object.addElement(obj);
    }
   public void  add_block_hinge(float x, float y,  float box_width, float box_height,  float density, float restitution, float angle, String path, boolean mouse_moved)
	{
	   	Game_object g = new Spinning_block();
	    if(x > width_game_field)
        {
        	g.allow_dec = false;
        	g.allow_inc = true;
        	g.stock_start_x = x;
        	g.stock_start_y = y;
        }
        else
        {
        	g.allow_dec = true;
        }
	   	g.set_coordinate(x, y);
	   	g.set_box(box_width, box_height);
		g.set_type("block_hinge");
		g.angle = angle;
		g.mouse_moved = mouse_moved;
	   	g.set_fixture(density, restitution);
	   	g.set_image(path);
	   	g.create(world);
	   	object.addElement(g);
	}
   public void add_rope(float x, float y, int length, float density, String spr_f, String spr_rope, String spr_ball, boolean mouse_moved)
   {
       Game_object r = new Rope();
       r.set_coordinate(x, y);
       r.set_type("rope");
       r.w = world;
   //    r.mouse_moved = mouse_moved;
       r.density = density;
       r.createRope(world, length);
       r.set_texture_rope(spr_f, spr_rope, spr_ball);
       r.path_texture = "";
       object.add(r);
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
        		   if(arr[0].equals("block_hinge"))
            	   {
        			   add_block_hinge(Float.valueOf(arr[1]), Float.valueOf(arr[2]),  
        					   Float.valueOf(arr[3]), Float.valueOf(arr[4]),
        					   Float.valueOf(arr[5]), Float.valueOf(arr[6]), 
        					   Float.valueOf(arr[7]), //angle
        					   String.valueOf(arr[8]),  Boolean.valueOf(arr[9]));				       
            	   }
        		   if(arr[0].equals("rectangle"))
            	   {
            		   add_rectangle(Float.valueOf(arr[1]), Float.valueOf(arr[2]), 
            				   Float.valueOf(arr[3]), Float.valueOf(arr[4]), 
            				   Float.valueOf(arr[5]), Float.valueOf(arr[6]), 
            				   Float.valueOf(arr[7]), String.valueOf(arr[8]),
            				   Boolean.valueOf(arr[9]));     
            	   }
        		   if(arr[0].equals("ball"))
            	   {
            		   add_ball(Float.valueOf(arr[1]), Float.valueOf(arr[2]),   //position
            				   Float.valueOf(arr[3]), //radius
            				   Float.valueOf(arr[4]),  Float.valueOf(arr[5]),   //fixture
            				   Float.valueOf(arr[6]),  String.valueOf(arr[7]), //angle and texture  
            				   Boolean.valueOf(arr[8]));
            	   }
        		   if(arr[0].equals("static_body"))
            	   {
        			   add_static_body(Float.valueOf(arr[1]), Float.valueOf(arr[2]), //x and y
        					   		   Float.valueOf(arr[3]), Float.valueOf(arr[4]),  //width and height
        					   		   Float.valueOf(arr[5]), Float.valueOf(arr[6]),  //restitution_angle
        					   		   String.valueOf(arr[7]),
        					   		   Boolean.valueOf(arr[8]), Boolean.valueOf(arr[9]));
            	   }
        		   if(arr[0].equals("rope"))
            	   {
        			   //add_rope(-3, 25, 9, 233.0f, "image/nail.png", "image/rope.jpg", "data/ball.png", true);
        			   add_rope(Float.valueOf(arr[1]), Float.valueOf(arr[2]), //x and y
        					   		   Integer.valueOf(arr[3]), Float.valueOf(arr[4]),  //length, density
        					   		   String.valueOf(arr[5]),String.valueOf(arr[6]),String.valueOf(arr[7]), Boolean.valueOf(arr[8]));
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
	public void compute()
	{
		for(int i = 0; i < object.size(); i++)
		{
		   if(object.get(i).get_body().getPosition().x > width_game_field)
		   {
			   object.get(i).change_box_size(-1);
			   if(editor_mode == false)
			   object.get(i).moveTo(object.get(i).stock_start_x, object.get(i).stock_start_y);
			   
		   }
		   else
		   {
			   object.get(i).change_box_size(1);
		   }
		}	
	}
    public double distance_two_point(double x1, double y1, double x2, double y2)
    {
    	return Math.sqrt(Math.pow((x2-x1), 2) + Math.pow((y2-y1), 2));
    }
    public void render() {
        camera.update();
        if(game_mode == true)
        world.step(1f/60f, 6, 2);
      //  Gdx.gl.glClearColor(1, 1, 1, 1);
        compute();
        Gdx.gl.glClearColor(0,0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.getProjectionMatrix().set(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(1, 1, 0);
   	 for(int i = 0; i < object.size(); i++)
   	 {
   		 if(object.get(i).path_texture.equals("image/invisible.png"))
   		 {
   			 b2 = object.get(i).get_body();
   		 }
   		 if(object.get(i).get_type() == "ball")
   		 {
   			 b1 = object.get(i).get_body();
   		 }	
   	 }
     //   System.out.println("particle_speed" + object.get(object.size()-1).get_body().getLinearVelocity().x);
      //  System.out.println("particle_speed" + object.get(object.size()-1).get_body().getLinearVelocity().y);
        for(int i = 0; i < object.size(); i++)
        {
        	if(object.get(i).get_type() == "particle")
        	{
        		if(object.get(i).get_body().getLinearVelocity().y < -15)
                {
                // System.out.println("aesdf");
                   JOptionPane.showMessageDialog(null, "fall");
                   object.get(i).get_body().setActive(false);
                 object.remove(i);
                 break;
                }
        	}
        }
        if(next_step == true)
    	{
        	next_level();
        	next_step = false;
    	}
        batch.begin();   
        if(drawSprite)
        {
        	batch.draw(textureRegion, -1, 16, // the bottom left corner of the box, unrotated
    				1f, 1f, // the rotation center relative to the bottom left corner of the box
    				2,  2, // the width and height of the box
    				27, 17, // the scale on the x- and y-axis
    				0.0f); // the rotation angle
        	for(int i = 0; i < object.size(); i++)
        	{
        		object.get(i).draw(batch);
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
           count_check = 0;
           for(int i = 0; i < object.size(); i++)
           {
        	   object.get(i).set_start_position(object.get(i).start_x, object.get(i).start_y);  
        	   object.get(i).get_body().setActive(true);
           }
        }
    	//Create explosion
        if(keycode == Input.Keys.F)
        {
        	if(game_mode == true)
        	{
        		float coord_bomb_x = 0.0f;
            	float coord_bomb_y = 0.0f;
            	float v_x;
            	float v_y;
            	double distance = 0.0f;
            	for(int i = 0; i < object.size(); i++)
            	{
            		//Delete bomb
            		if(object.get(i).path_texture.equals("image/explosion.png") && object.get(i).get_body().isActive() == true)
            		{
            			coord_bomb_x = object.get(i).get_body().getPosition().x;
            			coord_bomb_y = object.get(i).get_body().getPosition().y;
            			object.get(i).get_body().setActive(false);  	
            			//Push elements
                		for(int j = 0; j < object.size(); j++)
                		{
                			if(object.get(j).get_body().getPosition().x < width_game_field )
                			{
                				v_x = object.get(j).get_body().getPosition().x - coord_bomb_x;
                    			v_y = object.get(j).get_body().getPosition().y - coord_bomb_y;
                    			distance = distance_two_point(object.get(j).get_body().getPosition().x , object.get(j).get_body().getPosition().y, coord_bomb_x, coord_bomb_y);
                    			//нормализация вектора
                    			v_x = v_x / (float)distance;
                    			v_y = v_y / (float)distance;
                    			object.get(j).get_body().setLinearVelocity((float)((150f*v_x)/(distance)), (float)((150f*v_y)/(distance)));
                			}

                		}
            		}
            	}
        	}
        }
        if(keycode == Input.Keys.MINUS)
        {
        	editor_mode = false;
        }
        if(keycode == Input.Keys.PLUS)
        {
        	editor_mode = true;
        }
        if(keycode == Input.Keys.CONTROL_LEFT)
        {
        	for(int i = 0; i < object.size(); i++)
        	{
        		//if(object.get(i).mouse_moved == true)
        		//	object.get(i).get_body().setTransform(3, 11, 10);
        		object.get(i).set_coordinate(3, 11);
        		object.get(i).moveTo(3, 11);
        	}
        	
        }
        if(editor_mode == true)
        {
        	if(keycode == Input.Keys.NUM_1)
            {
            	add_rectangle((float)random_int(-24, 12), (float)random_int(0, 26), 3.0f,  0.5f, 2, 0.1f,0.0f,  "data/Wood.jpg", true);     
            }
            if(keycode == Input.Keys.NUM_2)
            {
            	add_ball(random_int(-24, 12), random_int(0, 26), 1.5f, //x y radius
            			2.1f,  0.2f, 0.0f, "data/ball.png", true);  //density, rest, angle  
            }
            if(keycode == Input.Keys.NUM_3)
            {
            	add_rectangle(random_int(-24, 12), random_int(0, 26), 2.0f,  2.0f,
            			15.0f, 0.1f, 0.0f, "data/Steel-Box.jpg", true);
            	
            }
            if(keycode == Input.Keys.NUM_4)
            {
               add_rectangle(random_int(-20, 12), random_int(0, 21), 2.0f,  2.0f, 
            		   3.6f, 0.2f, 0.0f,"data/box.jpg", true);
            }
            if(keycode == Input.Keys.NUM_5)
            {
            	add_static_body(0, 1, 4.5f,  0.6f, 0.0f, 0.0f, "image/texture_wood.png", true, false);
            }
            if(keycode == Input.Keys.NUM_6)
            {
            	
            	add_block_hinge(3, 1, 5.5f, 0.6f, 9, 0.4f, 0.0f,  "image/wood2.jpg", true);
            }
            if(keycode == Input.Keys.NUM_7)
            {
            	add_static_body(3, 5, 3.0f,  2.1f,  1.5f, 0.0f, "image/spring.png", true, false);
            }
            if(keycode == Input.Keys.NUM_8)
            {
            	add_rectangle(random_int(-24, 12), random_int(0, 26), 2.0f,  2.0f,
            			1.0f, 0.1f, 0.0f, "image/paper_box.jpg", true);
            }
            if(keycode == Input.Keys.NUM_9)
            {
            	add_rope(-3, 26, 9, 25.0f, "image/nail.png", "image/rope.jpg", "image/p.png", true);
            }
            if(keycode == Input.Keys.N)
            {
            	add_static_body(5, 2, 4.0f,  1.0f, 0.0f, 0.0f, "image/invisible.png", true, true);
            }
            
            if(keycode == Input.Keys.C)
            {
            	for(int i = 0; i < object.size(); i++)
            	{
            		object.get(i).mouse_moved = true;
            	}
            }
            
            if(keycode == Input.Keys.W)
            {
            	String str = "";
            	Writer w  = new Writer(path_to_level);
            	for(int j = 0; j < object.size(); j++)
            	{
            		if(object.get(j).get_type() == "rectangle")
            		{
            			str = object.get(j).get_type() + " " + object.get(j).start_x + " " + object.get(j).start_y + " " +
            					object.get(j).get_a() + " " + object.get(j).get_b() + " " + 
            					object.get(j).density + " " + object.get(j).restitution + " " +
            					object.get(j).angle + " " +
            					object.get(j).path_texture + " " + object.get(j).get_position_x();
                		w.write(str);
            		}
            		if(object.get(j).get_type() == "static_body")
            		{
            			str = object.get(j).get_type() + " " + object.get(j).start_x + " " + object.get(j).start_y + " " +
            					object.get(j).get_a() + " " + object.get(j).get_b() + " " + 
								object.get(j).restitution + " " + object.get(j).angle + " " +
            					object.get(j).path_texture + " " + object.get(j).get_position_x()
            					+ " " + object.get(j).isSensor;
                		w.write(str);
            		}
            		if(object.get(j).get_type() == "ball")
            		{
            			str = object.get(j).get_type() + " " + object.get(j).start_x + " " + object.get(j).start_y + " " +
            					object.get(j).get_a() + " " + 
            					object.get(j).density + " " + object.get(j).restitution + " " +
            					object.get(j).angle + " " +
            					object.get(j).path_texture  + " " + object.get(j).get_position_x();
                		w.write(str);
            		}	
            		if(object.get(j).get_type() == "block_hinge")
            		{
            			str = object.get(j).get_type() + " " + object.get(j).start_x + " " + object.get(j).start_y + " " +
            					object.get(j).get_a() + " " + object.get(j).get_b() + " " +
            					object.get(j).density + " " + object.get(j).restitution + " " +
            					object.get(j).angle + " " +
            					object.get(j).path_texture  + " " + object.get(j).get_position_x();
                		w.write(str);
            		}
            		if(object.get(j).get_type() == "rope")
            		{
            			str = object.get(j).get_type() + " " + object.get(j).start_x + " " + object.get(j).start_y + " " +
            					object.get(j).get_length_rope() + " " + object.get(j).density + " " +
            					object.get(j).spr_foundation + " " + object.get(j).spr_rope + " " + object.get(j).spr_ball
            					+ " " + object.get(j).mouse_moved;
                		w.write(str);
            		}
            	}
            }
            if(keycode == Input.Keys.L)
            {
                 // Load_level(path_to_level);
            	next_level();
            }
            if(keycode == Input.Keys.T)
            {
            	if(hitBody != null)	
                { 
                 	for(int i = 0; i < object.size(); i++)
                    {
                     	if(hitBody == object.get(i).get_body() && object.get(i).get_type() != "part_hinge_static")
                     	{
                     			object.get(i).angle = -1.5676f;
        	             		hitBody.setTransform(testPoint.x, testPoint.y, object.get(i).angle);
                     	}
                    }
                }
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
            if(keycode == Input.Keys.Y)
            {
            	System.out.println("force");
            	  //object.get(0).get_body().applyForceToCenter(0f,10f,true);
            //	  object.get(0).get_body().setAngularVelocity(3f);
            	//  object.get(0).get_body().applyTorque(43,true);
            	//  object.get(0).get_body().setLinearVelocity(-7f,30f);
            	Game_object p = new Particle();
            	  p.set_type("particle");
            	  p.set_radius(0.01f);
            	  p.set_coordinate(-19, 17);
            	  p.set_fixture(400000, 0.1f);
            	  p.start_linearvelocity_x = 50.0f;
            	  p.start_linearvelocity_y = 0f;
            	//  p.get_body().setLinearVelocity(50f, 0f);
            	  p.create(world);
            	  object.addElement(p);
            	//  object.get(object.size()-1).get_body().setLinearVelocity(50f, 0f);
            }
            if(keycode == Input.Keys.E)
            {
            	add_static_body(0, 1, 2.8f,  3.5f, 0.0f, 0.0f, "image/explosion.png", true, false);
            }

        }
        return true;
    }
    @Override
    public boolean keyTyped(char character) {
        return false;
    }
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    	if(game_mode == false)
    	{
    			
    		    testPoint.set(screenX, screenY, 0);
    	        camera.unproject(testPoint);    
    	        hitBody = null;
    	        world.QueryAABB(callback, testPoint.x - 0.1f, testPoint.y - 0.1f, testPoint.x + 0.1f, testPoint.y + 0.1f);
    	        for(int i = 0; i < object.size(); i++)
    	        {
    	        	if(hitBody == object.get(i).get_body())
    	        	{
    	        		if(object.get(i).get_type() == "part_hinge_static")
    	        		{
    	        			hitBody = null;
    	        			break;
    	        		}
    	        	}
    	        }
    	        if (hitBody != null)  
    	        {
    	        	    //new code
    	        		delta_x = testPoint.x - hitBody.getPosition().x;
    	        		delta_y = testPoint.y - hitBody.getPosition().y;
    	                MouseJointDef def = new MouseJointDef();
    	                def.bodyA = bodyEdgeScreen;
    	                def.bodyB = hitBody;
    	                def.collideConnected = true;
    	                def.target.set(testPoint.x, testPoint.y);
    	                def.maxForce = 1000.0f * hitBody.getMass();
    	                mouseJoint = (MouseJoint)world.createJoint(def);
    	                hitBody.setAwake(true);
    	        } 
    	}
        return true;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    	hitBody = null;
        if (mouseJoint != null) {
                world.destroyJoint(mouseJoint);
                mouseJoint = null;
        }
        return false;
    }
 
    /** another temporary vector **/
    Vector2 target = new Vector2();
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer)
    {
    	if(game_mode == false)
    	{
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
                			object.get(i).set_coordinate(testPoint.x-delta_x, testPoint.y-delta_y);  //remember position
                    		hitBody.setTransform(testPoint.x-delta_x, testPoint.y-delta_y, object.get(i).angle);
                    		object.get(i).moveTo(testPoint.x-delta_x, testPoint.y-delta_y);
                		}
                	}
                }
            }   
    	}
       return false;
    }
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
    	// testPoint.set(screenX, screenY, 0);
      //  camera.unproject(testPoint);
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
             	if(hitBody == object.get(i).get_body() && object.get(i).get_type() != "part_hinge_static")
             	{
             		if(amount == -1)
                	{
             			object.get(i).angle = object.get(i).angle + 0.1f;
             			System.out.println(" angle = " + object.get(i).angle);
	             		hitBody.setTransform(testPoint.x, testPoint.y, object.get(i).angle);
                	}
             		if(amount == 1)
                	{
	             		object.get(i).angle = object.get(i).angle - 0.1f;
	             		System.out.println(" angle = " + object.get(i).angle);
	             		hitBody.setTransform(testPoint.x, testPoint.y, object.get(i).angle);
                	}
             	}
            }
        }
        return false;
    }
}
