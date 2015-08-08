package box2d_game;

import java.util.Vector;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class Block_hinge {
	
	public void create(Vector<Game_object> object, World world)
	{
		Game_object obj = new Static_body();
	    obj.set_coordinate(4, 8);
	    obj.set_box(0.4f,  0.4f); 
	    obj.set_image("data/Wood.jpg"); 
	    obj.create(world);
	    object.addElement(obj);
	    Game_object obj2 = new Rectangle();
	    obj2.set_coordinate(4,8);
	    obj2.set_box(8.0f,  1.0f); 
	    obj2.set_fixture(1, 0.1f);
	    obj2.create(world);
	    obj2.set_image("data/Wood.jpg");
	    object.addElement(obj2);  
	    RevoluteJointDef rjd = new RevoluteJointDef();
	    Vector2 v = new Vector2( object.get(0).current_x,  object.get(0).current_y);
	    rjd.initialize(object.get(0).get_body(), object.get(1).get_body(), v);
	    rjd.motorSpeed = 100.0f;
	    rjd.enableLimit = false;
	    rjd.enableMotor = true;
	    rjd.collideConnected = false;
	    world.createJoint(rjd);
	}

}
