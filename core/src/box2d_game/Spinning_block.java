package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
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
	 public void create_new_shape()
	 {
	       PolygonShape shape = new PolygonShape();
	        shape.setAsBox(width_box, height_box);
	        FixtureDef fixtureDef = new FixtureDef();
	        fixtureDef.shape = shape;
	        fixtureDef.density = density;
	        fixtureDef.restitution = restitution;
	        fixtureDef.filter.categoryBits = 1;  //This is what I am  = 1
	        fixtureDef.filter.maskBits = 1;  //This is what  I collide with = 2
	        obj2.createFixture(fixtureDef);
	        shape.dispose();
	 }
	 public void delete_old_shape()
	{
		obj2.destroyFixture(obj2.getFixtureList().first());
		// this.getBody().destroyFixture(this.getBody().getFixtureList().first());
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
	     groundBox.setRadius(1f);
	     
			CircleShape shape_player = new CircleShape();
	        shape_player.setRadius(0.4f);
	        
	   //  groundBox.setAsBox(0.2f, 0.2f); 
	     obj1.createFixture(shape_player, 0.0f);   //groundBox
	     groundBox.dispose();
	     //Create dynamic body
	     BodyDef bodyDef = new BodyDef();
	        bodyDef.type = BodyDef.BodyType.DynamicBody;
	        obj2 = world.createBody(bodyDef);
	        obj2.setTransform(current_x, current_y, angle);
	        obj2.setLinearVelocity(0,0);
	        create_new_shape();

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
		}
		public void change_box_size(int t)
		{
			if(t == 1)
			{
				if(allow_inc == true)
				{
					width_box = width_box*2;
					height_box = height_box*2;
					allow_inc = false;
					allow_dec = true;
				}
			}
			if(t == -1)
			{
				if(allow_dec == true)
				{
					width_box = width_box/2;
					height_box = height_box/2;
					allow_dec = false;
					allow_inc = true;
				}
			}
			delete_old_shape();
			create_new_shape();
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
			return width_box;
		}
		public float get_b()
		{
			return height_box;
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
		public boolean get_position_x() //if x > 16 - return true
		{
			if(obj2.getPosition().x > 16) return true;
			return false;
		}
		public void draw(SpriteBatch batch, boolean game_mode)
		{
		 	batch.draw(textureRegion, obj2.getPosition().x-1, obj2.getPosition().y-1, // the bottom left corner of the box, unrotated
					1f, 1f, // the rotation center relative to the bottom left corner of the box
					2,  2, // the width and height of the box
					get_a(), get_b(), // the scale on the x- and y-axis
					MathUtils.radiansToDegrees * obj2.getAngle()); // the rotation angle*/
		 	textureRegion_static = new TextureRegion(new Texture(Gdx.files.internal("image/nail.png")));    
		 	batch.draw(textureRegion_static, obj1.getPosition().x-1, obj1.getPosition().y-1, // the bottom left corner of the box, unrotated
					1f, 1f, // the rotation center relative to the bottom left corner of the box
					2,  2, // the width and height of the box
					0.4f, 0.4f, // the scale on the x- and y-axis
					MathUtils.radiansToDegrees * obj1.getAngle()); // the rotation angle
		}
		public void setActive(boolean b)
		{
			obj1.setActive(b);
			obj2.setActive(b);
		}
}
