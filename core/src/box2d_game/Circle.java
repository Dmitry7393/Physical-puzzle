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
	Circle()
	{
		
	}
	public void set_radius(float r)
	{
		radius = r;
	}
	public void create(World world)
	{
		BodyDef bodyDef3 = new BodyDef();
        bodyDef3.type = BodyDef.BodyType.DynamicBody;
        ball = world.createBody(bodyDef3);
        CircleShape shape_player = new CircleShape();
        shape_player.setRadius(radius);
        FixtureDef fixtureDef_player= new FixtureDef();
        fixtureDef_player.shape = shape_player;
        fixtureDef_player.density = 1.1f;
        fixtureDef_player.restitution =  0.3f;
        ball.createFixture(fixtureDef_player);
        ball.setTransform(current_x, current_y, angle);
        ball.setLinearVelocity(0,0);
        shape_player.dispose();
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
}
