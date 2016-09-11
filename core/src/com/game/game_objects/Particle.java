package com.game.game_objects;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.game.game_objects.GameObject;

public class Particle extends GameObject
{
	private Body ball;
    private float radius;
    public float getX(){
		return  ball.getPosition().x;
	}
	public float getY(){
		return  ball.getPosition().y;
	}
	public void set_radius(float r)
	{
		radius = r;
	}
	public void set_fixture(float d, float r)
	{
		density = d;
		restitution = r;
	}

	public void delete_old_shape()
	{
		ball.destroyFixture(ball.getFixtureList().first());
	}
	public void change_box_size(int t)
	{
		if(t == 1)
		{
			if(allow_inc == true)
			{
				radius = radius*2;
				allow_inc = false;
				allow_dec = true;
			}
		}
		if(t == -1)
		{
			if(allow_dec == true)
			{
				radius = radius/2;
				allow_dec = false;
				allow_inc = true;
			}
		}
		delete_old_shape();
		create_new_shape();
	}
	public void create_new_shape()
	{     
		CircleShape shape_player = new CircleShape();
        shape_player.setRadius(radius);
        FixtureDef fixtureDef_player = new FixtureDef();
        fixtureDef_player.shape = shape_player;
        fixtureDef_player.density = density;
        fixtureDef_player.restitution =  restitution;
        fixtureDef_player.filter.categoryBits = 1;  //This is what I am  = 1
        fixtureDef_player.filter.maskBits = 1;  //This is what  I collide with = 2
        ball.createFixture(fixtureDef_player);
        shape_player.dispose();
	}
	public void create(World world)
	{
		BodyDef bodyDef3 = new BodyDef();
        bodyDef3.type = BodyDef.BodyType.DynamicBody;
        ball = world.createBody(bodyDef3);
      
        ball.setTransform(current_x, current_y, angle);
        ball.setLinearVelocity(0,0);
        create_new_shape();    
	}
	public void set_linear_velocity(float vx, float vy)
	{
		v_x = vx;
		v_y = vy;
		ball.setLinearVelocity(v_x, v_y);
	}
	public boolean get_position_x() //if x > 16 - return true
	{
		if(ball.getPosition().x > 16) return true;
		return false;
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
		ball.setTransform(current_x, current_y, angle);
		ball.setAngularVelocity(0.0f);
		ball.setLinearVelocity(start_linearvelocity_x, start_linearvelocity_y);
	}
	public float get_a()
	{
		return radius;
	}
	public float get_b()
	{
		return radius;
	}
	public void set_image(String path) //
	{
		path_texture = path;
		textureRegion = new TextureRegion(new Texture(Gdx.files.internal(path)));    
	}
	public Body getBody()
	{
		return ball;
	}
	public void dispose()
	{
		textureRegion.getTexture().dispose();
		ball.setUserData(null);
		ball = null;
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
		ball.setTransform(dx, dy, angle);
	}
	public void draw(SpriteBatch batch)
	{
	 	/*batch.draw(textureRegion, ball.getPosition().x-1, ball.getPosition().y-1, // the bottom left corner of the box, unrotated
				1f, 1f, // the rotation center relative to the bottom left corner of the box
				2,  2, // the width and height of the box
				get_a(), get_b(), // the scale on the x- and y-axis
				MathUtils.radiansToDegrees * ball.getAngle()); // the rotation angle*/
	}
}
