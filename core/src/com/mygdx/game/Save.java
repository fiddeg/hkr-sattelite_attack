package com.mygdx.game;

import com.badlogic.gdx.Gdx;

import java.io.*;

/**
 * Created by Fidde on 2016-12-09.
 */
public class Save {

    public static HighScoreIO io;

    public static void save() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("highscore.sav"));
            out.writeObject(io);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    public static void load() {
        try {
            if (!saveFileExists()){
                init();
                return;
            }
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("highscore.sav"));
            io = (HighScoreIO) input.readObject();
            input.close();
        } catch (Exception e){
            e.printStackTrace();
            Gdx.app.exit();
        }
    }

    public static boolean saveFileExists(){
        File file = new File("highscore.sav");
        return file.exists();
    }

    public static void init() {
        io = new HighScoreIO();
        io.init();
        save();
    }
}
