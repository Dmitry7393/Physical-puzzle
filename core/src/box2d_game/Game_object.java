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
	
	Game_object()
	{
		
	}
	public void set_box(float a, float b)
	{
	}
	public String type_object_dragged()
	{
		return "";
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
