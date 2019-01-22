package source.controller;

import java.io.File;
import java.io.IOException;

/**
 * It is class for data configuration.
 */
public class DataConfiguration {

    private static DataConfiguration instance = null;

    String dataPath = "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\RushHour";
    public boolean gameOpenedFirstTime = false;
    private GameInfo gameInfo;
    private DataErrorHandler handler;

    /**
     * Empty constructor that initializes values to their specified initial values.
     */
    private DataConfiguration()
    {
        instance = this;
        handler = DataErrorHandler.getInstance();
        gameInfo = GameInfo.getInstance();
        confirmData();
        gameInfo.extractInfo();
    }

    /**
     * Returns a new instance of the DataConfiguration class
     * @return new DataConfiguration object
     */
    public static DataConfiguration getInstance()
    {
        if(instance == null) {
            instance = new DataConfiguration();
        }
        return instance;
    }

    /**
     * It is to confirm the data.
     */
    private void confirmData()
    {
        File file;
        file = new File(dataPath);
        if (!file.exists())
        {
            setup();
        }

        file = new File (dataPath + "\\players");
        if (!file.exists())
        {
            handler.handlePlayersNotFoundError();
        }

        file = new File(dataPath + "\\info.json");
        if (!file.exists())
        {
            handler.handleInfoDamagedError();
        }
    }

    /**
     * It is for setup new file.
     */
    void setup()
    {
        File folder = new File(dataPath);
        folder.mkdirs();

        folder = new File(dataPath + "\\players");
        folder.mkdirs();

        createInfoFile();
        gameOpenedFirstTime = true;
    }

    /**
     * Creates information file.
     */
    private void createInfoFile()
    {
        File infoFile = new File(dataPath + "\\info.json");
        try {
            infoFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        gameInfo.updateInfo("");
    }

}
