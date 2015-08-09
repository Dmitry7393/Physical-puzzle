package box2d_game;

import java.util.Vector;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class Block_hinge {
	World world;
	Game_object obj1;
	Game_object obj2;
	private float cx, cy;
	private float density ;
	private float restitution;
	private float width, height;
	public void set_world(World world1)
	{
		world = world1;
	}
	public void set_coordinate(float x, float y)
	{
		cx = x;
		cy = y;
	}
	public void set_box(float a, float b)
	{
		width = a;
		height = b;
	}
	public void set_fixture(float d, float r)
	{
		density = d;
		restitution = r;
	}
	public Game_object get_obj1()
	{
		 obj1 = new Static_body();
		 obj1.set_coordinate(cx,cy);
		 obj1.set_box(0.4f,  0.4f); 
		 obj1.set_type("part_hinge_static");
		 obj1.set_image("data/Bronze.jpg"); 
		 obj1.create(world);
		 return obj1;
	}
	public Game_object get_obj2()
	{
		obj2 = new Rectangle();
	    obj2.set_coordinate(cx,cy);
	    obj2.set_box(width, height); 
	    obj2.set_type("part_hinge_dynamic");
	    obj2.set_fixture(density,restitution);
	    obj2.create(world);
	    obj2.set_image("data/Wood.jpg");
		return obj2;
	}
	public void create()
	{ 
	    RevoluteJointDef rjd = new RevoluteJointDef();
	    Vector2 v = new Vector2( obj1.current_x,  obj1.current_y);
	    rjd.initialize(obj1.get_body(), obj2.get_body(), v);
	    rjd.motorSpeed = 100.0f;
	    rjd.enableLimit = false;
	    rjd.enableMotor = true;
	    rjd.collideConnected = false;
	    world.createJoint(rjd);
	}

}
