package box2d_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class Spinning_block extends Game_object {
	private Body obj1;
	private Body obj2;
	protected TextureRegion textureRegion_static;
	protected TextureRegion textureRegion_dynamic;
	 public  Spinning_block()
	 {
		 
	 }
	 public void set_type(String type1)
		{
			type = type1;
		}
		public String get_type()
		{
			return type;
		}
	 public void create(World world)
	 {
	     BodyDef groundBodyDef = new BodyDef();  
	     // Set its world position
	     groundBodyDef.position.set(new Vector2(current_x, current_y));  
	     obj1 = world.createBody(groundBodyDef);  
	     PolygonShape groundBox = new PolygonShape();  
	     groundBox.setAsBox(0.2f, 0.2f); 
	     obj1.createFixture(groundBox, 0.0f); 
	     groundBox.dispose();
	     //Create dynamic body
	     BodyDef bodyDef = new BodyDef();
	        bodyDef.type = BodyDef.BodyType.DynamicBody;
	        obj2 = world.createBody(bodyDef);
	        obj2.setTransform(current_x, current_y, angle);
	        obj2.setLinearVelocity(0,0);
	       PolygonShape shape = new PolygonShape();
	        shape.setAsBox(width_dynamic, height_dynamic);
	        FixtureDef fixtureDef = new FixtureDef();
	        fixtureDef.shape = shape;
	        fixtureDef.density = density;
	        fixtureDef.restitution = restitution;
	        obj2.createFixture(fixtureDef);
	        shape.dispose();
	        //create connect between two object
	        RevoluteJointDef rjd = new RevoluteJointDef();
		    Vector2 v = new Vector2(current_x, current_y);
		    rjd.initialize(obj1, obj2, v);
		    rjd.motorSpeed = 100.0f;
		    rjd.enableLimit = false;
		    rjd.enableMotor = true;
		    rjd.collideConnected = false;
		    world.createJoint(rjd);
	 }
		public void set_coordinate(float x, float y)
		{
			current_x = x;
			current_y = y;
			start_x = x;
			start_y = y;
		}
		public void moveTo(float dx, float dy)
		{
			obj1.setTransform(dx, dy, angle);
			obj2.setTransform(dx, dy, angle);
		}
		public void set_box(float a, float b)
		{
			width_box = a;
			height_box = b;
			start_width = a;
			start_height = b;
			storage_width = a / 2;
			storage_height = b / 2;
		}
		public void set_start_position(float x, float y)
		{
			current_x = x;
			current_y = y;
			obj2.setTransform(current_x, current_y, angle);
			obj2.setAngularVelocity(0.0f);
			obj2.setLinearVelocity(0.0f, 0.0f);
			obj1.setTransform(current_x, current_y, angle);
			obj1.setAngularVelocity(0.0f);
			obj1.setLinearVelocity(0.0f, 0.0f);
		}
		public float get_a()
		{
			return width_dynamic;
		}
		public float get_b()
		{
			return height_dynamic;
		}
		public void set_fixture(float d, float r)
		{
			density = d;
			restitution = r;
		}
		public void set_image(String path) //
		{
			path_texture = path;
			textureRegion = new TextureRegion(new Texture(Gdx.files.internal(path)));    
		}
		
		public Body get_body()
		{
			return obj2;
		}
		public void dispose()
		{
			textureRegion.getTexture().dispose();
		}
}
