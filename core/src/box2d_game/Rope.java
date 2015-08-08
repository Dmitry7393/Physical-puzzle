package box2d_game;
// createRope(8);
public class Rope {
	 /* private Body[] createRope(int length)
	    {
	    	 Body[] segments = new Body[length];
	    	 RevoluteJoint[] joints = new RevoluteJoint[length-1];
	    	 RopeJoint[] ropeJoints = new RopeJoint[length - 1];
	    	 BodyDef  bodyDef = new BodyDef();
	    	 bodyDef.type = BodyType.DynamicBody;
	    	 float width = 1f;
	    	 float height = 2f;
	    	 PolygonShape shape = new PolygonShape();
	    	 shape.setAsBox(width / 2, height / 2);
	       /*  FixtureDef fixtureDef = new FixtureDef();
	         fixtureDef.density = 1;
	         fixtureDef.restitution = 0.4f;
	         fixtureDef.shape = shape;*/
	    	 /*for(int i = 0; i < segments.length; i++)
	    	 {
		    	 segments[i] = world.createBody(bodyDef);
		    	// segments[i].createFixture(fixtureDef);  
		    	 segments[i].createFixture(shape, 2);
		    	 segments[i].setTransform(-10, 24, 0);	
	    	 }
	    	 RevoluteJointDef jointDef = new RevoluteJointDef();
	    	 jointDef.localAnchorA.set(0,  -height / 2);
	    	 jointDef.localAnchorB.set(0, height / 2);
	    	 for(int i = 0; i < joints.length; i++)
	    	 {
	    	 	jointDef.bodyA = segments[i];
	    	 	jointDef.bodyB = segments[i+1];
	    	 	joints[i] = (RevoluteJoint)world.createJoint(jointDef);
	    	 }
	    	 RopeJointDef ropeJointDef = new RopeJointDef();
	    	 ropeJointDef.localAnchorA.y = -height / 2;
	    	 ropeJointDef.localAnchorB.y = height / 2;
	    	 ropeJointDef.maxLength = height;
			for(int i = 0; i < ropeJoints.length; i++)
			{
				  ropeJointDef.bodyA = segments[i];
				  ropeJointDef.bodyB = segments[i+1];
				  ropeJoints[i] = (RopeJoint)world.createJoint(ropeJointDef);
				  
			}
	    	/* jointDef.bodyA = object.get(0).get_body();
	    	 jointDef.bodyB = segments[0];
	    	 joints[0] = (RevoluteJoint)world.createJoint(jointDef);
	    	 
	    	 //Add a ball 
	         Game_object obj2 = new Circle();
	         obj2.set_coordinate(-5,10);
	         obj2.set_radius(3f);
	         obj2.set_fixture(1.1f,  0.1f);
	         obj2.create(world);
	         obj2.set_image("data/ball.png");
	         object.addElement(obj2);
	         jointDef.bodyA = segments[length-1];
	    	 jointDef.bodyB = object.get(1).get_body();
	    	 joints[1] = (RevoluteJoint)world.createJoint(jointDef);*/
	    	/* return segments;
	    }*/
}
