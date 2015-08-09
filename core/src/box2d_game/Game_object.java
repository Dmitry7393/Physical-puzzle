package box2d_game;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class Game_object {
	Body b;
	protected float current_x;
	protected float current_y;
	public float angle = 0.0f;
	protected TextureRegion textureRegion;
	protected float start_x, start_y;
	public boolean mouse_dragged = false;
	protected float v_x;
	protected float v_y;
	public boolean mouse_moved = true;
	protected float density;
	protected float restitution;
	protected String path_texture;
	String type;
	Game_object()
	{
		
	}
	public void set_fixture(float d, float r)
	{
	}
	public void set_world(World world1)
	{
	}
	public void set_type(String type1)
	{
		
	}
	public void set_box(float a, float b)
	{
	}
	public String name_texture()
	{
		return "";
	}
	public String type_object_dragged()
	{
		return "";
	}
	public String get_type()
	{
		return "";
	}
	public void set_linear_velocity(float vx, float vy)
	{
		
	}
	public float get_a()
	{
		return 0;
	}
	public void dispose()
	{
		
	}
	public float get_b()
	{
		return 0;
	}
	public void start_position(float x, float y)
	{

	}
	public void set_radius(float r)
	{
		
	}
	public void create(World world)
	{
		
	}
	public void set_coordinate(float x, float y)
	{
		
	}
	public void set_start_position(float x, float y)
	{
		
	}
	public Body get_body()
	{
		return b;
	}
	public TextureRegion get_texture()
	{
		return textureRegion;
	}
	public void set_image(String path) //
	{
		  
	}
	public float rotation_center_x = 1f;
	public float rotation_center_y = 1f;
	public float width = 2f;
	public float height = 2f;
	public float scale_x = 4.7f;
	public float scale_y = 4.7f;
}
