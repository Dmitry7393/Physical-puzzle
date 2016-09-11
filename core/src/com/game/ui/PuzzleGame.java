package com.game.ui;

import java.util.Vector;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.game.game_objects.GameObject;
import com.game.game_objects.*;

public class PuzzleGame extends ApplicationAdapter implements InputProcessor, GestureDetector.GestureListener {
    World world;
    SpriteBatch batch;
    private static float width_game_field = 18.0f;
    private Vector<GameObject> object = new Vector<GameObject>();
    Body bodyEdgeScreen;
    Box2DDebugRenderer debugRenderer;
    Matrix4 debugMatrix;

    private boolean showArrowRotate = false;
    OrthographicCamera camera;
    BitmapFont font;
    Point coordinatesButtonRotateLeft = new Point(-26, 29);
    Point coordinatesButtonRotateRight = new Point(-22, 29);
    GestureDetector gestureDetector;
    boolean game_mode = false;
    boolean drawSprite = true;
    Vector3 testPoint = new Vector3();
    Vector3 pointTouchDown = new Vector3();
    Body hitBody = null;
    private MouseJoint mouseJoint = null;
    private int level = 1;
    Body b1 = null;
    Body b2 = null;
    private boolean mChangingZoom = false;
    TextureRegion textureRegion;
    TextureRegion textureRegion_button_start;
    TextureRegion textureRegion_button_stop;
    TextureRegion textureRegionButtonRotateLeft;
    TextureRegion textureRegionButtonRotateRight;
    // private boolean mZooming = false;

    boolean next_step = false;
    int count_check = 0;
    private float delta_x;
    private float delta_y;
    boolean check_first_collision = true;  //if collision occurs, we must delete this object
    int c_collision = 0;

    public void next_level() {
        for (int i = 0; i < object.size(); i++) {
            world.destroyBody(object.get(i).getBody());
            object.get(i).dispose();
        }
        object.clear();
        game_mode = false;
        level++;
        loadLevel();
    }

    @Override
    public void create() {
        camera = new OrthographicCamera(54, 32);
        camera.position.set(0, 16, 0);

        world = new World(new Vector2(0, -20.0f), false);
        batch = new SpriteBatch();
        CreateObject.setWorld(world);

        // Bottom line
        textureRegion = new TextureRegion(new Texture(Gdx.files.internal("image/background.png")));

        textureRegion_button_start = new TextureRegion(new Texture(Gdx.files.internal("image/button_start.png")));
        textureRegion_button_stop = new TextureRegion(new Texture(Gdx.files.internal("image/button_stop.png")));

        textureRegionButtonRotateLeft = new TextureRegion(new Texture(Gdx.files.internal("image/object_rotate_left.png")));
        textureRegionButtonRotateRight = new TextureRegion(new Texture(Gdx.files.internal("image/object_rotate_right.png")));

        // Right line
        BodyDef bodyDef_right = new BodyDef();
        bodyDef_right.type = BodyDef.BodyType.StaticBody;
        bodyDef_right.position.set(0, 0);
        FixtureDef fixtureDef_right = new FixtureDef();
        EdgeShape edgeShape_right = new EdgeShape();
        edgeShape_right.set(18.7f, 1, 18.7f, 54);
        fixtureDef_right.shape = edgeShape_right;
        bodyEdgeScreen = world.createBody(bodyDef_right);
        bodyEdgeScreen.createFixture(fixtureDef_right);
        edgeShape_right.dispose();

        debugRenderer = new Box2DDebugRenderer();

        font = new BitmapFont();
        font.setColor(Color.BLACK);

        //Keystroak and gestures
        InputMultiplexer im = new InputMultiplexer();
        gestureDetector = new GestureDetector(this);
        im.addProcessor(gestureDetector);
        im.addProcessor(this);
        Gdx.input.setInputProcessor(im);

        loadLevel();
    }

    public void loadLevel() {
        FileHandle handle = Gdx.files.internal("Level/level" + Integer.toString(level) + ".txt");
        String text = handle.readString();
        String wordsArray[] = text.split("\\r?\\n");
        for (String sCurrentLine : wordsArray) {
            Split_str(sCurrentLine);
        }
    }

    public void collision2() {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                if (b1 != null && b2 != null) {
                    if ((contact.getFixtureA().getBody() == b1 && contact.getFixtureB().getBody() == b2)
                            || (contact.getFixtureA().getBody() == b2 && contact.getFixtureB().getBody() == b1)) {
                        // JOptionPane.showMessageDialog(null, "Level " + level + " completed!");
                        next_step = true;
                    }
                }
                if (check_first_collision) {
                    Body temp_moved;
                    Body temp_game;
                    for (int i = 0; i < object.size(); i++) // mouse_moved
                    {
                        if (object.get(i).mouse_moved) {
                            temp_moved = object.get(i).getBody();
                            for (int j = 0; j < object.size(); j++) // game object
                            {
                                // if(i != j) // && object.get(j).mouse_moved ==
                                // false
                                // {
                                temp_game = object.get(j).getBody();
                                if ((contact.getFixtureA().getBody() == temp_moved
                                        && contact.getFixtureB().getBody() == temp_game)
                                        || (contact.getFixtureA().getBody() == temp_game
                                        && contact.getFixtureB().getBody() == temp_moved)) {
                                    // JOptionPane.showMessageDialog(null,
                                    // "collision");
                                    if (object.get(i).mouse_moved) {
                                        game_mode = false;
                                        object.get(i).set_coordinate(21, 4);
                                    }
                                }

                            }

                        }
                    }
                }
            }

            @Override
            public void endContact(Contact contact) {
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });
    }


    public static int random_int(int Min, int Max) {
        return (Min + (int) (Math.random() * ((Max - Min) + 1)));
    }

    public void Split_str(String s1) {
        String[] arr = s1.split(" ");
        for (int i = 0; i < arr.length; i++) {
            if (i == arr.length - 1) {
                if (arr[0].equals("block_hinge")) {
                    object.addElement(CreateObject.getBlockHinge(Float.valueOf(arr[1]), Float.valueOf(arr[2]), Float.valueOf(arr[3]),
                            Float.valueOf(arr[4]), Float.valueOf(arr[5]), Float.valueOf(arr[6]), Float.valueOf(arr[7]), // angle
                            String.valueOf(arr[8]), Boolean.valueOf(arr[9])));
                }
                if (arr[0].equals("rectangle")) {
                    object.addElement(CreateObject.getRectangle(Float.valueOf(arr[1]), Float.valueOf(arr[2]), Float.valueOf(arr[3]),
                            Float.valueOf(arr[4]), Float.valueOf(arr[5]), Float.valueOf(arr[6]), Float.valueOf(arr[7]),
                            String.valueOf(arr[8]), Boolean.valueOf(arr[9])));
                    ;
                }
                if (arr[0].equals("ball")) {
                    object.addElement(CreateObject.getBall(Float.valueOf(arr[1]), Float.valueOf(arr[2]), // position
                            Float.valueOf(arr[3]), // radius
                            Float.valueOf(arr[4]), Float.valueOf(arr[5]), // fixture
                            Float.valueOf(arr[6]), String.valueOf(arr[7]), // angle
                            // and
                            // texture
                            Boolean.valueOf(arr[8])));
                }
                if (arr[0].equals("static_body")) {
                    object.addElement(CreateObject.getStaticBody(Float.valueOf(arr[1]), Float.valueOf(arr[2]), // x
                            // and
                            // y
                            Float.valueOf(arr[3]), Float.valueOf(arr[4]), // width
                            // and
                            // height
                            Float.valueOf(arr[5]), Float.valueOf(arr[6]), // restitution_angle
                            String.valueOf(arr[7]), Boolean.valueOf(arr[8]), Boolean.valueOf(arr[9])));
                }
                if (arr[0].equals("rope")) {
                    object.addElement(CreateObject.getRope(Float.valueOf(arr[1]), Float.valueOf(arr[2]), // x
                            // and
                            // y
                            Integer.valueOf(arr[3]), Float.valueOf(arr[4]), // length,
                            // density
                            String.valueOf(arr[5]), String.valueOf(arr[6]), String.valueOf(arr[7]),
                            Boolean.valueOf(arr[8])));
                }

            }
        }
    }


    public void compute() {
        boolean editor_mode = false;
        for (int i = 0; i < object.size(); i++) {
            if (object.get(i).getBody().getPosition().x > width_game_field) {
                object.get(i).change_box_size(-1);
                if (editor_mode == false)
                    object.get(i).moveTo(object.get(i).stock_start_x, object.get(i).stock_start_y);

            } else {
                object.get(i).change_box_size(1);
            }
        }
    }

    public double distance_two_point(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    public void launchGravity() {
        game_mode = !game_mode;
        render();
        count_check = 0;
        c_collision = 0;
        check_first_collision = true;
        for (int i = 0; i < object.size(); i++) {
            object.get(i).set_start_position(object.get(i).start_x, object.get(i).start_y);
            object.get(i).getBody().setActive(true);
        }
    }

    public void render() {
        camera.update();
        if (game_mode) {
            world.step(1f / 60f, 6, 2);  // 6, 2);
            c_collision++;
            collision2();
            if (c_collision == 3)
                check_first_collision = false;
        }
        compute();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.getProjectionMatrix().set(camera.combined);
        debugMatrix = batch.getProjectionMatrix().cpy().scale(1, 1, 0);
        for (int i = 0; i < object.size(); i++) {
            if (object.get(i).path_texture.equals("image/invisible.png")) {
                b2 = object.get(i).getBody();
            }
            if (object.get(i).get_type() == "ball") {
                b1 = object.get(i).getBody();
            }
        }
        for (int i = 0; i < object.size(); i++) {
            if (object.get(i).get_type() == "particle") {
                if (object.get(i).getBody().getLinearVelocity().y < -15) {
                    object.get(i).getBody().setActive(false);
                    object.remove(i);
                    break;
                }
            }
        }
        if (next_step) {
            next_level();
            next_step = false;
        }
        //batch.setProjectionMatrix(camera.combined);
        batch.begin();
        if (drawSprite) {
            batch.draw(textureRegion, -1, 16, // the bottom left corner of the box, unrotated
                    1f, 1f, // the rotation center relative to the bottom left
                    // corner of the box
                    2, 2, // the width and height of the box
                    27, 17, // the scale on the x- and y-axis
                    0.0f); // the rotation angle
            batch.draw(textureRegion_button_start, 19.6f, 29,
                    1f, 1f,
                    2, 2,
                    1.5f, 1.5f,
                    0.0f);
            batch.draw(textureRegion_button_stop, 23, 29,
                    1f, 1f,
                    2, 2,
                    1.5f, 1.5f,
                    0.0f);
            if (showArrowRotate) {
                batch.draw(textureRegionButtonRotateLeft, (float) coordinatesButtonRotateLeft.getX(), (float) coordinatesButtonRotateLeft.getY(),
                        1f, 1f,
                        2, 2,
                        1.5f, 1.5f,
                        0.0f);
                batch.draw(textureRegionButtonRotateRight, (float) coordinatesButtonRotateRight.getX(), (float) coordinatesButtonRotateRight.getY(),
                        1f, 1f,
                        2, 2,
                        1.5f, 1.5f,
                        0.0f);
            }
            for (int i = 0; i < object.size(); i++) {
                object.get(i).draw(batch, game_mode);
            }
            font.getData().setScale(0.2f, 0.2f);
            font.draw(batch, Float.toString(camera.position.x) + " " + Float.toString(camera.position.y) +
                    " " + Float.toString(camera.zoom), -20, 20);
        }
        batch.end();
        debugRenderer.render(world, debugMatrix);
    }

    QueryCallback callback = new QueryCallback() {
        @Override
        public boolean reportFixture(Fixture fixture) {
            if (fixture.testPoint(testPoint.x, testPoint.y)) {
                hitBody = fixture.getBody();
                return false;
            } else
                return true;
        }
    };

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        for (int i = 0; i < object.size(); i++) {
            object.get(i).dispose();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.SPACE) {
            launchGravity();
        }
        // Create explosion
        if (keycode == Input.Keys.F) {
            if (game_mode) {
                float coord_bomb_x;
                float coord_bomb_y;
                float v_x;
                float v_y;
                double distance;
                for (int i = 0; i < object.size(); i++) {
                    // Delete bomb
                    if (object.get(i).path_texture.equals("image/explosion.png")
                            && object.get(i).getBody().isActive() == true) {
                        coord_bomb_x = object.get(i).getBody().getPosition().x;
                        coord_bomb_y = object.get(i).getBody().getPosition().y;
                        object.get(i).getBody().setActive(false);
                        // Push elements
                        for (int j = 0; j < object.size(); j++) {
                            if (object.get(j).getBody().getPosition().x < width_game_field) {
                                v_x = object.get(j).getBody().getPosition().x - coord_bomb_x;
                                v_y = object.get(j).getBody().getPosition().y - coord_bomb_y;
                                distance = distance_two_point(object.get(j).getBody().getPosition().x,
                                        object.get(j).getBody().getPosition().y, coord_bomb_x, coord_bomb_y);
                                // нормализация вектора
                                v_x = v_x / (float) distance;
                                v_y = v_y / (float) distance;
                                object.get(j).getBody().setLinearVelocity((float) ((150f * v_x) / (distance)),
                                        (float) ((150f * v_y) / (distance)));
                            }

                        }
                    }
                }
            }
        }
        if (keycode == Input.Keys.NUM_1) {
            object.add(CreateObject.getRectangle((float) random_int(-24, 12), (float) random_int(0, 26), 3.0f, 0.5f, 2, 0.1f, 0.0f,
                    "data/Wood.jpg", true));
            //  add_rectangle();
        }
        if (keycode == Input.Keys.NUM_2) {
            object.addElement(CreateObject.getBall(random_int(-24, 12), random_int(0, 26), 1.5f, // x y radius
                    2.1f, 0.2f, 0.0f, "data/ball.png", true));
        }
        if (keycode == Input.Keys.NUM_3) {
            object.addElement(CreateObject.getRectangle(random_int(-24, 12), random_int(0, 26), 2.0f, 2.0f, 15.0f, 0.1f, 0.0f, "data/Steel-Box.jpg",
                    true));
        }
        if (keycode == Input.Keys.NUM_4) {
            object.addElement(CreateObject.getRectangle(random_int(-20, 12), random_int(0, 21), 2.0f, 2.0f, 3.6f, 0.2f, 0.0f, "data/box.jpg", true));
        }
        if (keycode == Input.Keys.NUM_5) {
            object.addElement(CreateObject.getStaticBody(0, 1, 4.5f, 0.6f, 0.0f, 0.0f, "image/texture_wood.png", true, false));
        }
        if (keycode == Input.Keys.NUM_6) {
            object.addElement(CreateObject.getBlockHinge(3, 1, 5.5f, 0.6f, 9, 0.4f, 0.0f, "image/wood2.jpg", true));
        }
        if (keycode == Input.Keys.NUM_7) {
            object.addElement(CreateObject.getStaticBody(3, 5, 3.0f, 2.1f, 1.5f, 0.0f, "image/spring.png", true, false));
        }
        if (keycode == Input.Keys.NUM_8) {
          /*  add_rectangle(random_int(-24, 12), random_int(0, 26), 2.0f, 2.0f, 1.0f, 0.1f, 0.0f, "image/paper_box.jpg",
                    true);*/
        }
        if (keycode == Input.Keys.NUM_9) {
            object.addElement(CreateObject.getRope(-3, 26, 9, 25.0f, "image/nail.png", "image/rope.jpg", "image/p.png", true));
        }
        if (keycode == Input.Keys.E) {
            object.addElement(CreateObject.getStaticBody(0, 1, 2.8f, 3.5f, 0.0f, 0.0f, "image/explosion.png", true, false));
        }
        if (keycode == Input.Keys.N) {
            object.addElement(CreateObject.getStaticBody(5, 2, 4.0f, 1.0f, 0.0f, 0.0f, "image/invisible.png", true, true));
        }

        if (keycode == Input.Keys.C) {
            for (int i = 0; i < object.size(); i++) {
                object.get(i).mouse_moved = true;
            }
        }
        if (keycode == Input.Keys.S) {
            object.addElement(CreateObject.getStaticBody(5, 2, 2.0f, 2.0f, 0.0f, 0.0f, "image/strelka.png", true, true));
        }
        if (keycode == Input.Keys.D) {
            object.addElement(CreateObject.getStaticBody(5, 2, 2.0f, 2.0f, 0.0f, 0.0f, "image/strelka2.png", true, true));
        }
        if (keycode == Input.Keys.R) {
            String str = "";
            Writer w = new Writer("D:/leveln.txt");
            for (int j = 0; j < object.size(); j++) {
                if (object.get(j).get_type() == "rectangle") {
                    str = object.get(j).get_type() + " " + object.get(j).start_x + " " + object.get(j).start_y + " "
                            + object.get(j).get_a() + " " + object.get(j).get_b() + " " + object.get(j).density + " "
                            + object.get(j).restitution + " " + object.get(j).angle + " " + object.get(j).path_texture
                            + " " + object.get(j).get_position_x();
                    w.write(str);
                }
                if (object.get(j).get_type() == "static_body") {
                    str = object.get(j).get_type() + " " + object.get(j).start_x + " " + object.get(j).start_y + " "
                            + object.get(j).get_a() + " " + object.get(j).get_b() + " " + object.get(j).restitution
                            + " " + object.get(j).angle + " " + object.get(j).path_texture + " "
                            + object.get(j).get_position_x() + " " + object.get(j).isSensor;
                    w.write(str);
                }
                if (object.get(j).get_type() == "ball") {
                    str = object.get(j).get_type() + " " + object.get(j).start_x + " " + object.get(j).start_y + " "
                            + object.get(j).get_a() + " " + object.get(j).density + " " + object.get(j).restitution
                            + " " + object.get(j).angle + " " + object.get(j).path_texture + " "
                            + object.get(j).get_position_x();
                    w.write(str);
                }
                if (object.get(j).get_type() == "block_hinge") {
                    str = object.get(j).get_type() + " " + object.get(j).start_x + " " + object.get(j).start_y + " "
                            + object.get(j).get_a() + " " + object.get(j).get_b() + " " + object.get(j).density + " "
                            + object.get(j).restitution + " " + object.get(j).angle + " " + object.get(j).path_texture
                            + " " + object.get(j).get_position_x();
                    w.write(str);
                }
                if (object.get(j).get_type() == "rope") {
                    str = object.get(j).get_type() + " " + object.get(j).start_x + " " + object.get(j).start_y + " "
                            + object.get(j).get_length_rope() + " " + object.get(j).density + " "
                            + object.get(j).spr_foundation + " " + object.get(j).spr_rope + " " + object.get(j).spr_ball
                            + " " + object.get(j).mouse_moved;
                    w.write(str);
                }
                w.close();
            }
        }
        if (keycode == Input.Keys.T) {
            if (hitBody != null) {
                for (int i = 0; i < object.size(); i++) {
                    if (hitBody == object.get(i).getBody() && object.get(i).get_type() != "part_hinge_static") {
                        object.get(i).angle = -1.5676f;
                        hitBody.setTransform(testPoint.x, testPoint.y, object.get(i).angle);
                    }
                }
            }
        }
        if (keycode == Input.Keys.DEL) {
            for (int i = 0; i < object.size(); i++) {
                if (hitBody == object.get(i).getBody()) {
                    object.get(i).getBody().setActive(false);
                    // object.get(i).get_body().setUserData(null);
                    // world.destroyBody(object.get(i).get_body());
                    object.remove(i);
                    break;
                }
            }
        }
        if (keycode == Input.Keys.H) {
            GameObject obj = new Polygon_shape();
            obj.set_coordinate(3, 19, 2, 18, 3, 21, 5, 22);
            //obj.set_box(box_width, box_height);
            obj.angle = 0;

            obj.mouse_moved = true;
            obj.set_fixture(3, 0.1f);
            obj.create(world);
            obj.set_type("rectangle");
            obj.set_image("image/box.png");
            object.addElement(obj);
        }
        if (keycode == Input.Keys.A) {
            camera.zoom += 0.1;
        }
        if (keycode == Input.Keys.Q) {
            if (camera.zoom > 0.3)
                camera.zoom -= 0.1;
        }
        if (keycode == Input.Keys.LEFT) {
            camera.translate(-0.1f, 0, 0);
        }
        if (keycode == Input.Keys.RIGHT) {
            camera.translate(0.1f, 0, 0);
        }
        if (keycode == Input.Keys.DOWN) {
            camera.translate(0, -0.1f, 0);
        }
        if (keycode == Input.Keys.UP) {
            camera.translate(0, 0.1f, 0);
        }
        Gdx.app.log("CCC", Float.toString(camera.position.x) + " " + Float.toString(camera.position.y));
        camera.update();
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    private boolean rotateObject() {
        if (distance_two_point(testPoint.x, testPoint.y, coordinatesButtonRotateLeft.getX(), coordinatesButtonRotateLeft.getY()) < 1.5f * 1.5f) {
            if (hitBody != null && !game_mode) {
                for (int i = 0; i < object.size(); i++) {
                    if (hitBody == object.get(i).getBody() && object.get(i).get_type() != "part_hinge_static"
                            && object.get(i).mouse_moved) {
                        object.get(i).angle = object.get(i).angle + 0.1f;
                        hitBody.setTransform(object.get(i).getX(), object.get(i).getY(), object.get(i).angle);
                    }
                }
            }
            return true;
        }
        if (distance_two_point(testPoint.x, testPoint.y, coordinatesButtonRotateRight.getX(), coordinatesButtonRotateRight.getY()) < 1.5f * 1.5f) {
            if (hitBody != null && !game_mode) {
                for (int i = 0; i < object.size(); i++) {
                    if (hitBody == object.get(i).getBody() && object.get(i).get_type() != "part_hinge_static"
                            && object.get(i).mouse_moved) {

                        object.get(i).angle = object.get(i).angle - 0.1f;
                        hitBody.setTransform(object.get(i).getX(), object.get(i).getY(), object.get(i).angle);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (mChangingZoom) {
            return true;
        }
        testPoint.set(screenX, screenY, 0);
        camera.unproject(testPoint);
        pointTouchDown = new Vector3(testPoint);

        if (game_mode) {
            if (distance_two_point(testPoint.x, testPoint.y, 24, 30) < 1f * 1f) {
                launchGravity();
            }
        }
        if (!game_mode) {
            if (distance_two_point(testPoint.x, testPoint.y, 20.6f, 30) < 1f * 1f) {
                launchGravity();
            }
            if (!rotateObject()) {
                showArrowRotate = false;
                hitBody = null;
                world.QueryAABB(callback, testPoint.x - 0.1f, testPoint.y - 0.1f, testPoint.x + 0.1f, testPoint.y + 0.1f);
            }

            for (int i = 0; i < object.size(); i++) {
                if (hitBody == object.get(i).getBody()) {
                    if (object.get(i).get_type() == "part_hinge_static") {
                        hitBody = null;
                        break;
                    }
                }
            }

            if (hitBody != null) {
                showArrowRotate = true;
                delta_x = testPoint.x - hitBody.getPosition().x;
                delta_y = testPoint.y - hitBody.getPosition().y;
                MouseJointDef def = new MouseJointDef();
                def.bodyA = bodyEdgeScreen;
                def.bodyB = hitBody;
                def.collideConnected = true;
                def.target.set(testPoint.x, testPoint.y);
                def.maxForce = 1000.0f * hitBody.getMass();
                mouseJoint = (MouseJoint) world.createJoint(def);
                hitBody.setAwake(true);
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        testPoint.set(screenX, screenY, 0);
        camera.unproject(testPoint);

        if (mouseJoint != null) {
            world.destroyJoint(mouseJoint);
            mouseJoint = null;
        }
        mChangingZoom = false;
        pointTouchDown = new Vector3(0, 0, 0);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        //camera.translate(-deltaX*0.1f, deltaY*0.1f);
        if (mChangingZoom) {
            return true;
        }
        testPoint.set(screenX, screenY, 0);
        camera.unproject(testPoint);
        if (hitBody == null && pointTouchDown.x != 0 && pointTouchDown.y != 0) {
            float dX = -1 * (testPoint.x - pointTouchDown.x);
            float dY = -1 * (testPoint.y - pointTouchDown.y);
            //Check for boundaries
            float currentZoom = 1.0f - camera.zoom;
            float boundsX = currentZoom * 27;
            float boundsY = currentZoom * 15;
            if (camera.position.x + dX > (-boundsX) && camera.position.x + dX < boundsX)
                camera.translate(dX, 0, 0);
            if (camera.position.y + dY > (16 - boundsY) && camera.position.y + dY < 16 + boundsY)
                camera.translate(0, dY, 0);
        }
        if (!game_mode) {
            if (hitBody != null) // && hitBody != ball2.get_body()
            {
                for (int i = 0; i < object.size(); i++) {
                    if (hitBody == object.get(i).getBody()) {
                        if (object.get(i).mouse_moved) {
                            object.get(i).set_coordinate(testPoint.x - delta_x, testPoint.y - delta_y); // remember
                            // position
                            hitBody.setTransform(testPoint.x - delta_x, testPoint.y - delta_y, object.get(i).angle);
                            object.get(i).moveTo(testPoint.x - delta_x, testPoint.y - delta_y);
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
      /*  if(hitBody == null) {
            Gdx.app.log("DRAGGED", Float.toString(testPoint.x) + " " + Float.toString(testPoint.y));
            float deltaX = -1 * (testPoint.x - pointTouchDown.x);
            float deltaY = -1 * (testPoint.y - pointTouchDown.y);
            camera.translate(deltaX, deltaY, 0);
        }*/
        // not
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        mChangingZoom = true;
        float zoomValue = 0.01f;
        if (initialDistance > distance) {
            if (camera.zoom < 0.999) {
                float nextZoom = camera.zoom + zoomValue;
                float currentBoundsX = (1.0f - nextZoom) * 27;
                float currentBoundsY = (1.0f - nextZoom) * 15;
                Gdx.app.log("CurrentBouds", Float.toString(currentBoundsX) + " " + Float.toString(currentBoundsY));
                //Если после отдаления камера окажется за границей - переместить вправо по Х
                if (camera.position.x < (-currentBoundsX)) { //left border x
                    float moveX = Math.abs(camera.position.x) - Math.abs(currentBoundsX);
                    camera.position.x += moveX;
                    return true;
                }
                if (camera.position.x > (currentBoundsX)) { //right border x
                    float moveX = Math.abs(camera.position.x) - Math.abs(currentBoundsX);
                    camera.position.x -= moveX;
                    return true;
                }
                if (camera.position.y > (currentBoundsY + 16)) {
                    float moveY = camera.position.y - (currentBoundsY + 16);
                    camera.position.y -= moveY;
                    return true;
                }
                if (camera.position.y < (16 - currentBoundsY)) {
                    float moveY = (16 - currentBoundsY) - camera.position.y;
                    camera.position.y += moveY;
                    return true;
                }
                camera.zoom += zoomValue; //zoom out
            }
        } else {
            if (camera.zoom > 0.3)
                camera.zoom -= zoomValue; //zoom in
        }

        return true;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}
