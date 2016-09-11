package com.game.game_objects;

import com.badlogic.gdx.physics.box2d.World;

public  class  CreateObject {
    private static World world;
    private static float width_game_field = 18.0f;
    public static void setWorld(World w){
        world = w;
    }
    public static GameObject getRectangle(float x, float y, float box_width, float box_height, float density, float restitution,
                              float angle, String name_texture, boolean mouse_moved) {
        GameObject obj = new Rectangle();
        if (x > width_game_field) {
            obj.allow_dec = false;
            obj.allow_inc = true;
            obj.stock_start_x = x;
            obj.stock_start_y = y;

        } else {
            obj.allow_dec = true;
        }
        obj.set_coordinate(x, y);
        obj.set_box(box_width, box_height);
        obj.angle = angle;

        obj.mouse_moved = mouse_moved;
        obj.set_fixture(density, restitution);
        obj.create(world);
        obj.set_type("rectangle");
        obj.set_image(name_texture);

        return obj;
    }
    public static GameObject getBall(float x, float y, float radius, float density, float restitution, float angle,
                         String name_texture, boolean mouse_moved) {
        GameObject obj2 = new Circle();
        if (x > width_game_field) {
            obj2.allow_dec = false;
            obj2.allow_inc = true;
            obj2.stock_start_x = x;
            obj2.stock_start_y = y;
        } else {
            obj2.allow_dec = true;
        }
        obj2.set_coordinate(x, y);
        obj2.set_radius(radius);
        obj2.set_fixture(density, restitution);
        obj2.angle = angle;

        obj2.mouse_moved = mouse_moved;
        obj2.create(world);
        obj2.set_type("ball");
        obj2.set_image(name_texture);

       return obj2;
    }
    public static GameObject getStaticBody(float x, float y, float box_width, float box_height, float restitution, float angle,
                                String name_texture, boolean mouse_moved, boolean isSensor) {
        GameObject obj = new Static_body();
        if (x > width_game_field) {
            obj.allow_dec = false;
            obj.allow_inc = true;
            obj.stock_start_x = x;
            obj.stock_start_y = y;
        } else {
            obj.allow_dec = true;
        }
        obj.set_coordinate(x, y);
        obj.set_box(box_width, box_height);
        obj.angle = angle;
        obj.restitution = restitution;
        obj.isSensor = isSensor;
        obj.mouse_moved = mouse_moved;
        obj.set_type("static_body");

        obj.set_image(name_texture);
        obj.create(world);
        return obj;
    }

    public static GameObject getBlockHinge(float x, float y, float box_width, float box_height, float density, float restitution,
                                     float angle, String path, boolean mouse_moved) {
        GameObject g = new Spinning_block();
        if (x > width_game_field) {
            g.allow_dec = false;
            g.allow_inc = true;
            g.stock_start_x = x;
            g.stock_start_y = y;
        } else {
            g.allow_dec = true;
        }
        g.set_coordinate(x, y);
        g.set_box(box_width, box_height);
        g.set_type("block_hinge");
        g.angle = angle;
        g.mouse_moved = mouse_moved;
        g.set_fixture(density, restitution);
        g.set_image(path);
        g.create(world);
       return g;
    }

    public static GameObject getRope(float x, float y, int length, float density, String spr_f, String spr_rope, String spr_ball,
                         boolean mouse_moved) {
        GameObject r = new Rope();
        r.set_coordinate(x, y);
        r.set_type("rope");
        // r.mouse_moved = mouse_moved;
        r.density = density;
        r.createRope(world, length);
        r.set_texture_rope(spr_f, spr_rope, spr_ball);
        r.path_texture = "";
       return r;
    }
}
