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
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

// createRope(8);
public class Rope extends Game_object {
	private float radius1 = 2.0f;
	private  Body ball;
	
	private Body foundation;
	private int count_block;
	private Body[] segments;
	private float[]  start_position_x;
	private float[]  start_position_y;
	private float start_position_ball_x;
	private float start_position_ball_y;
	private float width_rope =  1f; //0.5f;
	private float height_rope = 2f; //2.0f;
	 TextureRegion textureRegion ;   
	 TextureRegion textureRegion2;
	 TextureRegion textureRegion_static ;
	public Rope() { }
 public void createRope(World world, int length)
    {
    	//Create static body 
	    count_block = length;
	    start_position_x = new float[length];
	    start_position_y = new float[length];
    	 // Create our body definition
        BodyDef groundBodyDef =new BodyDef();  
        // Set its world position
        groundBodyDef.position.set(new Vector2(current_x, current_y));  
        foundation = world.createBody(groundBodyDef);  
        CircleShape shape_foundation = new CircleShape();
        shape_foundation.setRadius(radius1/2); 
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.restitution = 0.1f;
        fixtureDef.shape = shape_foundation;
        foundation.createFixture(fixtureDef);
        shape_foundation.dispose();
    	////////////////////////
    	 segments = new Body[length];
    	 RevoluteJoint[] joints = new RevoluteJoint[length-1];
    	 BodyDef bodyDef = new BodyDef();
    	 bodyDef.type = BodyType.DynamicBody;
    	 PolygonShape shape = new PolygonShape();
    	 shape.setAsBox(width_rope/2, height_rope/2);
    	 for(int i = 0; i < segments.length; i++)
    	 {
    		 segments[i] = world.createBody(bodyDef);
    		 segments[i].createFixture(shape, density);
    		 start_position_x[i] = foundation.getPosition().x+2*i;
    		 start_position_y[i] = foundation.getPosition().y;
    		 segments[i].setTransform(foundation.getPosition().x+2*i, foundation.getPosition().y, 1.57f); //-2
 			segments[i].setAngularVelocity(0.0f);
 			segments[i].setLinearVelocity(0.0f, 0.0f);

    	 }
    	 RevoluteJointDef jointDef = new RevoluteJointDef();
    	 jointDef.localAnchorA.y = -height_rope/2;
    	 jointDef.localAnchorB.y = height_rope/2;
    	 for(int i = 0; i < joints.length; i++)
    	 {
    		 jointDef.bodyA = segments[i];
    		 jointDef.bodyB = segments[i+1];
    		 joints[i] = (RevoluteJoint)world.createJoint(jointDef);
    	 }
	        //create connect between two object
	        RevoluteJointDef rjd = new RevoluteJointDef();
		    //Vector2 v = new Vector2(foundation.getPosition().x, foundation.getPosition().y); //-2
	        Vector2 v = new Vector2(segments[0].getPosition().x, segments[0].getPosition().y); //-2
		    rjd.initialize(foundation, segments[0], v);
		    rjd.motorSpeed = 100.0f;
		    rjd.enableLimit = false;
		    rjd.enableMotor = true;
		    rjd.collideConnected = false;
		    world.createJoint(rjd);
		    
        	//Create ball 
			BodyDef bodyDef3 = new BodyDef();
	        bodyDef3.type = BodyDef.BodyType.DynamicBody;
	        ball = world.createBody(bodyDef3);
	        ball.setTransform(segments[length-1].getPosition().x, segments[length-1].getPosition().y, 0);
			//ball.setAngularVelocity(0.0f);
			ball.setLinearVelocity(0.0f, 0.0f);
	        start_position_ball_x = segments[length-1].getPosition().x;
	        start_position_ball_y = segments[length-1].getPosition().y;
	        ball.setLinearVelocity(0,0);
	    	CircleShape shape_player = new CircleShape();
	        shape_player.setRadius(radius1);
	        FixtureDef fixtureDef_player = new FixtureDef();
	        fixtureDef_player.shape = shape_player;
	        fixtureDef_player.density = 6;
	        fixtureDef_player.restitution =  0.1f;
	        ball.createFixture(fixtureDef_player);
	        shape_player.dispose();  
	        //create joint
	        RevoluteJointDef rjd2 = new RevoluteJointDef();
		    Vector2 v2 = new Vector2(ball.getPosition().x, ball.getPosition().y);
		    rjd2.initialize(ball, segments[length-1],  v2);
		    rjd2.motorSpeed = 100.0f;
		    rjd2.enableLimit = false;
		    rjd2.enableMotor = true;
		    rjd2.collideConnected = false;
		    world.createJoint(rjd2);
    	// return segments;
    }
	public void set_texture_rope(String spr_f, String spr_r, String spr_b)
	{
		 spr_foundation = spr_f; 
		 spr_rope = spr_r;
		 spr_ball = spr_b;
		  textureRegion = new TextureRegion(new Texture(Gdx.files.internal(spr_b)));    
		  textureRegion2 = new TextureRegion(new Texture(Gdx.files.internal(spr_r)));
		  textureRegion_static = new TextureRegion(new Texture(Gdx.files.internal(spr_f)));
	}
	public void set_start_position(float x, float y)
	{
		//current_x = x;
		//current_y = y;
		foundation.setLinearVelocity(0.0f, 0.0f);
		foundation.setAngularVelocity(0.0f);
		ball.setTransform(start_position_ball_x, start_position_ball_y, 0);
		ball.setAngularVelocity(0.0f);
		ball.setLinearVelocity(0.0f, 0.0f);
		for(int i = 0; i < count_block; i++)
		{
			segments[i].setTransform(start_position_x[i], start_position_y[i], 1.57f);
			segments[i].setAngularVelocity(0.0f);
			segments[i].setLinearVelocity(0.0f, 0.0f);
		}
	//	createRope(w, 9);
		 
	}
	public void draw(SpriteBatch batch)
	{
		//Draw ball 
		 batch.draw(textureRegion, ball.getPosition().x-1, ball.getPosition().y-1, // the bottom left corner of the box, unrotated
 				1f, 1f, // the rotation center relative to the bottom left corner of the box
 				2,  2, // the width and height of the box
 				radius1, radius1, // the scale on the x- and y-axis
 				MathUtils.radiansToDegrees * 0); // the rotation angle
		 //Draw static body
		 batch.draw(textureRegion_static, foundation.getPosition().x-1, foundation.getPosition().y-1, // the bottom left corner of the box, unrotated
	 				1f, 1f, // the rotation center relative to the bottom left corner of the box
	 				2,  2, // the width and height of the box
	 				1, 1, // the scale on the x- and y-axis
	 				MathUtils.radiansToDegrees * 0); // the rotation angle
		 for(int i = 0; i < count_block; i++)
		 {
			 batch.draw(textureRegion2, segments[i].getPosition().x-1,  segments[i].getPosition().y-1, // the bottom left corner of the box, unrotated
		 				1f, 1f, // the rotation center relative to the bottom left corner of the box
		 				2,  2, // the width and height of the box
		 				width_rope/4, height_rope/2, // the scale on the x- and y-axis
		 				MathUtils.radiansToDegrees * segments[i].getAngle()); // the rotation angle
		 }
	}
	public void set_coordinate(float x, float y)
	{
		current_x = x;
		current_y = y;
		start_x = x;
		start_y = y;
	}
	public Body get_body()
	{
		return foundation;
	}
	public int get_length_rope()
	{
		return count_block;
	}
	public void set_type(String type1)
	{
		type = type1;
	}
	public String get_type()
	{
		return type;
	}
	public void setActive(boolean b)
	{
		for(int i = 0; i < count_block; i++)
		{
			segments[i].setActive(b);
		}
		ball.setActive(b);
	}
}
