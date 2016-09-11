package com.game.ui;

import com.badlogic.gdx.Gdx;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Writer {
    private String mPath = "";

    public Writer(String path) {
        mPath = path;
    }

    PrintWriter out;

    public void write(String text) {
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(mPath, true)));
            out.println(text);
        } catch (IOException e) {
            Gdx.app.log("IOException", "exception");

        } catch (Exception e) {
            Gdx.app.log("Exception", "exception");
        }
    }

    public void close() {

        out.close();


    }

}
