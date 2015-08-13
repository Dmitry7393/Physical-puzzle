package box2d_game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Circle extends Game_object
{
	Body ball;
    private float radius;
    private float start_radius;
	Circle()
	{
		
	}
	public void set_radius(float r)
	{
		radius = r;
		start_width = r;
		storage_width = r/2;
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
	public void set_game_size()
	{
		radius = start_width;
		delete_old_shape();
		create_new_shape();
	}
	public void create_new_shape()
	{     
		CircleShape shape_player = new CircleShape();
        shape_player.setRadius(radius);
        FixtureDef fixtureDef_player= new FixtureDef();
        fixtureDef_player.shape = shape_player;
        fixtureDef_player.density = density;
        fixtureDef_player.restitution =  restitution;
        ball.createFixture(fixtureDef_player);
        shape_player.dispose();
	}
	public void set_object_storage()
	{
		radius = storage_width;
		delete_old_shape();
		create_new_shape();
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
		ball.setLinearVelocity(0.0f, 0.0f);
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
	public Body get_body()
	{
		return ball;
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
