package source.controller;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The class for giving information of the game.
 */
class GameInfo {
    private static GameInfo instance = null;

    final int numberOfMaps = 50;
    String lastActivePlayer;

    /**
     * The inner class which holds last active player's info.
     */
    private class Info
    {
         String lastActivePlayer;
    }
    private Info info;

    /**
     * Empty constructor that initializes values to their specified initial values.
     */
    private GameInfo()
    {
        instance = this;
        info = new Info();
    }

    /**
     * Returns a new instance of the class
     * @return new GameInfo object
     */
    public static GameInfo getInstance()
    {
        if(instance == null) {
            instance = new GameInfo();
        }
        return instance;
    }

    /**
     * Extracts info.
     * @return true if it is extractad, false otherwise.
     */
    boolean extractInfo()
    {
        Gson gson = new Gson();
        FileReader reader = null;
        try {
            reader = new FileReader(DataConfiguration.getInstance().dataPath + "\\info.json");

            try {
                info = gson.fromJson(reader, Info.class);
            }
            catch (JsonParseException e)
            {
                DataErrorHandler.getInstance().handleInfoDamagedError();
            }
            if (info == null)
            {
                DataErrorHandler.getInstance().handleInfoDamagedError();
            }
            lastActivePlayer = info.lastActivePlayer;
            if (lastActivePlayer == null)
            {
                DataErrorHandler.getInstance().handleInfoDamagedError();
            }

            return true;

        } catch (FileNotFoundException e) {
            return false;
        }

    }

    /**
     * Updates the last players info.
     * @param lastPlayer the last player who played the game.
     */
    void updateInfo(String lastPlayer)
    {
        Gson gson = new Gson();
        lastActivePlayer = lastPlayer;

        if (info == null)
        {
            info = new Info();
        }

        info.lastActivePlayer = lastPlayer;

        FileWriter fileOut = null;
        try {
            fileOut = new FileWriter(DataConfiguration.getInstance().dataPath + "\\info.json");
            fileOut.write(gson.toJson(info));
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
