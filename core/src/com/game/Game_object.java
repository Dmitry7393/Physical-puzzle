package com.game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Game_object {
	protected float current_x;
	protected float current_y;
	public float angle = 0.0f;
	protected TextureRegion textureRegion;
	protected float start_x, start_y;
	protected float width_box;
	protected float height_box;
	protected float v_x;
	protected float v_y;
	public boolean mouse_moved = false;
	protected float density;
	protected float restitution;
	protected String path_texture;
	protected String type;
	protected boolean allow_inc = false;
	protected boolean allow_dec = false;
	protected float stock_start_x;
	protected float stock_start_y;
	protected boolean isSensor = false;
	protected int maskBits = 1;
	protected float start_linearvelocity_x;
	protected float start_linearvelocity_y;
	//for rope
	String spr_foundation;
	String spr_rope;
	String spr_ball;
	public World w;

	Game_object() {

	}

	public abstract float getX();
	public abstract float getY() ;
	public void setActive(boolean b)
	{
		
	}
	public void set_coordinate(float x, float y)
	{
		
	}
	public void set_coordinate(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
	{
		
	}
	public void set_box(float a, float b)
	{
	}
	public void change_box_size(int t)
	{
		
	}
	public abstract void set_fixture(float d, float r);

	public void set_type(String type1)
	{
		
	}
	public String get_type()
	{
		return "";
	}

	public float get_a()
	{
		return 0;
	}
	public abstract void dispose();
	public void moveTo(float dx, float dy)
	{
		
	}
	public float get_b()
	{
		return 0;
	}

	public boolean get_position_x() //if x > 16 - return true
	{
		return false;
	}
	public void set_radius(float r)
	{
		
	}
	public void create(World world)
	{
		
	}

	public void set_start_position(float x, float y)
	{
		
	}
	public abstract Body get_body();
	public void set_image(String path) //
	{
		  
	}
	public void set_texture_rope(String spr_foundation, String spr_rope, String spr_ball)
	{
		
	}
	public void draw(SpriteBatch batch, boolean game_mode)
	{
		
	}
	//for rope
	public int get_length_rope()
	{
		return 0;
	}
	public void createRope(World world, int length)
    {
	 
    }
	public float width = 2f;
	public float height = 2f;
}
