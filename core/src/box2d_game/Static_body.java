package box2d_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Static_body extends Game_object {
	private   Body groundBody;
	private float width_box;
	private float height_box;
	Static_body()
	{
		
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
        PolygonShape groundBox = new PolygonShape();  
        groundBox.setAsBox(width_box, height_box); 
        groundBody.createFixture(groundBox, 0.0f); 
      
        groundBox.dispose();
	}
	public void set_angle()
	{
		groundBody.setTransform(current_x, current_y, angle);
	}
	public void set_image(String path) //
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
}
