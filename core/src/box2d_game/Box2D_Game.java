package box2d_game;
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
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
 
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
    @Override
    public void create()
    {
        camera = new OrthographicCamera(54, 32);
        camera.position.set(0, 16, 0);
        world = new World(new Vector2(0, -20f),  false);
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
               /* if((contact.getFixtureA().getBody() == ball.get_body() &&
                        contact.getFixtureB().getBody() == ball2.get_body())
                        ||
                        (contact.getFixtureA().getBody() == ball2.get_body() &&
                                contact.getFixtureB().getBody() == ball.get_body())) {
                        System.out.println("collision ball with baskeball");
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
    public static int random_int(int Min, int Max)
    {
    	return (Min + (int)(Math.random() * ((Max - Min) + 1)));
    }
    
    public void render() {
        camera.update();
        if(game_mode == true)
        world.step(1f/60f, 6, 2);
        Gdx.gl.glClearColor(1, 1, 1, 1);
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
           if(game_mode == true)
           {      
        	   for(int i = 0; i < object.size(); i++)
        	   {
        		   object.get(0).set_linear_velocity(124, 0);
        	   }
              // ball.get_body().setLinearVelocity(0.2f, 0.3f);
           }
        }
        //Add new figure
        if(keycode == Input.Keys.NUM_1)
        {
            Game_object obj = new Rectangle();
            obj.set_coordinate(random_int(-24, 24), random_int(0, 26));
            obj.set_box(10.0f,  1.0f); 
            obj.create(world);
            obj.set_image("data/Wood.jpg");
            object.addElement(obj);         
        }
        if(keycode == Input.Keys.NUM_2)
        {
            Game_object obj2 = new Circle();
            obj2.set_coordinate(random_int(-24, 24), random_int(0, 26));
            obj2.set_radius(3f);
            obj2.create(world);
            obj2.set_image("data/ball.png");
            object.addElement(obj2);        
        }
        if(keycode == Input.Keys.NUM_3)
        {
            Game_object obj = new Rectangle();
            obj.set_coordinate(random_int(-24, 24), random_int(0, 26));
            obj.set_box(4.0f,  4.0f); 
            obj.create(world);
            obj.set_image("data/badlogic.jpg");
            object.addElement(obj);
        }
        if(keycode == Input.Keys.NUM_4)
        {
            Game_object obj = new Rectangle();
            obj.set_coordinate(random_int(-20, 20), random_int(0, 21));
            obj.set_box(2.0f,  2.0f); 
            obj.create(world);
            obj.set_image("data/badlogic.jpg");
            object.addElement(obj);
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
                MouseJointDef def = new MouseJointDef();
                def.bodyA = bodyEdgeScreen;
                def.bodyB = hitBody;
                def.collideConnected = true;
                def.target.set(testPoint.x, testPoint.y);
               // def.maxForce = 1000.0f * hitBody.getMass();
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
              		object.get(i).set_coordinate(testPoint.x, testPoint.y);
              		hitBody.setTransform(testPoint.x, testPoint.y, object.get(i).angle);
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
