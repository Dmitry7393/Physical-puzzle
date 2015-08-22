package box2d_game;

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
public class Rope {
	public float radius = 2.0f;
	private  Body ball;
	private float count_block;
	private Body[] segments;
	private float width_rope = 0.5f;
	private float height_rope = 3.0f;
	public Rope() { }
	 public Body[] createRope(World world, int length)
	    {
	    	//Create static body 
		    count_block = length;
	    	Body foundation;
	    	 // Create our body definition
	        BodyDef groundBodyDef =new BodyDef();  
	        // Set its world position
	        groundBodyDef.position.set(new Vector2(-3, 25));  
	        foundation = world.createBody(groundBodyDef);  
	        PolygonShape groundBox = new PolygonShape();  
	        groundBox.setAsBox(1, 1); 
	        FixtureDef fixtureDef = new FixtureDef();
	        fixtureDef.restitution = 0.1f;
	        fixtureDef.shape = groundBox;
	        foundation.createFixture(fixtureDef);
	        groundBox.dispose();
	    	////////////////////////
	    	 segments = new Body[length];
	    	 RevoluteJoint[] joints = new RevoluteJoint[length-1];
	    	 
	    	 BodyDef bodyDef = new BodyDef();
	    	 bodyDef.type = BodyType.DynamicBody;
	    	// float width = 1.5f;  float height = 3f;
	    	 PolygonShape shape = new PolygonShape();
	    	 shape.setAsBox(width_rope/2, height_rope/2);
	    	 for(int i = 0; i < segments.length; i++)
	    	 {
	    		 segments[i] = world.createBody(bodyDef);
	    		 segments[i].createFixture(shape, 2);
	    		 segments[i].setTransform(foundation.getPosition().x+3*i, foundation.getPosition().y-1, 0);
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
			    Vector2 v = new Vector2(foundation.getPosition().x, foundation.getPosition().y-1);
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
		        ball.setLinearVelocity(0,0);
		    	CircleShape shape_player = new CircleShape();
		        shape_player.setRadius(radius);
		        FixtureDef fixtureDef_player = new FixtureDef();
		        fixtureDef_player.shape = shape_player;
		        fixtureDef_player.density = 6;
		        fixtureDef_player.restitution =  0.1f;
		        ball.createFixture(fixtureDef_player);
		        shape_player.dispose();  
		        //create joint
		        RevoluteJointDef rjd2 = new RevoluteJointDef();
			    Vector2 v2 = new Vector2(ball.getPosition().x, ball.getPosition().y);
			    rjd2.initialize(ball, segments[length-1], v2);
			    rjd2.motorSpeed = 100.0f;
			    rjd2.enableLimit = false;
			    rjd2.enableMotor = true;
			    rjd2.collideConnected = false;
			    world.createJoint(rjd2);
	    	 return segments;
	    }
	 public void draw_texture(SpriteBatch batch, String path, String path2)
	 {
		 TextureRegion textureRegion = new TextureRegion(new Texture(Gdx.files.internal(path)));    
		 TextureRegion textureRegion2 = new TextureRegion(new Texture(Gdx.files.internal(path2))); 
		 batch.draw(textureRegion, ball.getPosition().x-1, ball.getPosition().y-1, // the bottom left corner of the box, unrotated
 				1f, 1f, // the rotation center relative to the bottom left corner of the box
 				2,  2, // the width and height of the box
 				radius, radius, // the scale on the x- and y-axis
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
}
