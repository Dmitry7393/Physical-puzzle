package box2d_game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Rectangle extends Game_object
{
	Body square;
	private PolygonShape  shape;
	private FixtureDef fixtureDef = new FixtureDef();
	Rectangle()
	{
		
	}
	public void delete_old_shape()
	{
		square.destroyFixture(square.getFixtureList().first());
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
	public void create_new_shape()
	{     
		shape = new PolygonShape();
        shape.setAsBox(width_box, height_box);
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.restitution = restitution;
      //  fixtureDef.filter.categoryBits = 1;  //This is what I am  = 1
     //   fixtureDef.filter.maskBits = 1;  //This is what  I collide with = 2
        square.createFixture(fixtureDef);
        shape.dispose();
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
	public void set_coordinate(float x, float y)
	{
		current_x = x;
		current_y = y;
		start_x = x;
		start_y = y;
	}
	public boolean get_position_x() //if x > 16 - return true
	{
		if(square.getPosition().x > 16) return true;
		return false;
	}
	public void set_start_position(float x, float y)
	{
		current_x = x;
		current_y = y;
		square.setTransform(current_x, current_y, angle);
		square.setAngularVelocity(0.0f);
		square.setLinearVelocity(0.0f, 0.0f);
	}
	public float get_b()
	{
		return height_box;
	}
	public void create(World world)
	{
	        BodyDef bodyDef = new BodyDef();
	        bodyDef.type = BodyDef.BodyType.DynamicBody;
	        square = world.createBody(bodyDef);
	        square.setTransform(current_x, current_y, angle);
	        square.setLinearVelocity(0,0);
	        create_new_shape();
	     //   square.resetMassData();
	}
	public void set_fixture(float d, float r)
	{
		density = d;
		restitution = r;
	}
	public void set_linear_velocity(float vx, float vy)
	{
		v_x = vx;
		v_y = vy;
		square.setLinearVelocity(v_x, v_y);
	}
	public void set_angle()
	{
		square.setTransform(current_x, current_y, angle);
	}
	public void set_image(String path) //
	{
		path_texture = path;
		textureRegion = new TextureRegion(new Texture(Gdx.files.internal(path)));    
	}
	public Body get_body()
	{
		return square;
	}
	public void set_type(String type1)
	{
		type = type1;
	}
	public String get_type()
	{
		return type;
	}
	public void dispose()
	{
		textureRegion.getTexture().dispose();
	}
	public void moveTo(float dx, float dy)
	{
		square.setTransform(dx, dy, angle);
	}
	public void draw(SpriteBatch batch)
	{
		System.out.println("draw from class Rectangle");
	}
}
