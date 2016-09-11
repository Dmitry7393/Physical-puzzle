package com.game.game_objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class GameObject {
    protected float current_x;
    protected float current_y;
    public float angle = 0.0f;
    public float start_x, start_y;

    protected float width_box;
    protected float height_box;

    protected float v_x;
    protected float v_y;
    public boolean mouse_moved = false;
    public float density;
    public float restitution;

    public boolean allow_inc = false;
    public boolean allow_dec = false;
    public boolean isSensor = false;

    public float stock_start_x;
    public float stock_start_y;

    protected int maskBits = 1;

    protected float start_linearvelocity_x;
    protected float start_linearvelocity_y;

    public String path_texture;
    protected String type;

    //for rope
    public String spr_foundation;
    public String spr_rope;
    public String spr_ball;

    protected TextureRegion textureRegion;

    public GameObject() {

    }

    public abstract float getX();
    public abstract float getY() ;

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
    public abstract Body getBody();
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
