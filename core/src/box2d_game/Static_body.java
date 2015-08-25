package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Static_body extends Game_object {
	private   Body groundBody;
	Static_body()
	{
		
	}
	 public void delete_old_shape()
	{
		 groundBody.destroyFixture(groundBody.getFixtureList().first());
	}
	 public void create_new_shape()
	 {
       PolygonShape groundBox = new PolygonShape();  
        groundBox.setAsBox(width_box, height_box); 
       // groundBody.createFixture(groundBox, 0.0f); //groundBox
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.restitution = restitution;
        fixtureDef.shape = groundBox;
        fixtureDef.isSensor = isSensor;
        fixtureDef.filter.categoryBits = 1;  //This is what I am  = 1
        fixtureDef.filter.maskBits = (short)maskBits;  //This is what  I collide with = 2
        groundBody.createFixture(fixtureDef);
        groundBody.setTransform(current_x, current_y, angle);
        groundBox.dispose();
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
	public boolean get_position_x() //if x > 16 - return true
	{
		if(groundBody.getPosition().x > 16) return true;
		return false;
	}
	public void set_box(float a, float b)
	{
		width_box = a;
		height_box = b;
	}
	public float get_a()
	{
		return width_box;
	}
	public float get_b()
	{
		return height_box;
	}
	public void set_coordinate(float x, float y)
	{
		current_x = x;
		current_y = y;
		start_x = x;
		start_y = y;
	}
	public void set_start_position(float x, float y)
	{
		current_x = x;
		current_y = y;
		groundBody.setTransform(current_x, current_y, angle);
		groundBody.setAngularVelocity(0.0f);
		groundBody.setLinearVelocity(0.0f, 0.0f);
	}
	public void create(World world)
	{
		 // Create our body definition
        BodyDef groundBodyDef =new BodyDef();  
        // Set its world position
        groundBodyDef.position.set(new Vector2(current_x, current_y));  
        groundBody = world.createBody(groundBodyDef);  
        create_new_shape();
	}
	public void set_angle()
	{
		groundBody.setTransform(current_x, current_y, angle);
	}
	public void set_image(String path)  //
	{
		path_texture = path;
		textureRegion = new TextureRegion(new Texture(Gdx.files.internal(path)));    
	}
	public Body get_body()
	{
		return groundBody;
	}
	public void dispose()
	{
		textureRegion.getTexture().dispose();
	}
	public void set_type(String type1)
	{
		type = type1;
	}
	public String get_type()
	{
		return type;
	}
	public void moveTo(float dx, float dy)
	{
		groundBody.setTransform(dx, dy, angle);
	}
	public void draw(SpriteBatch batch)
	{
		if(groundBody.isActive())
		{
		 	batch.draw(textureRegion, groundBody.getPosition().x-1, groundBody.getPosition().y-1, // the bottom left corner of the box, unrotated
					1f, 1f, // the rotation center relative to the bottom left corner of the box
					2,  2, // the width and height of the box
					get_a(), get_b(), // the scale on the x- and y-axis
					MathUtils.radiansToDegrees * groundBody.getAngle()); // the rotation angle*/
		}
	}
}
