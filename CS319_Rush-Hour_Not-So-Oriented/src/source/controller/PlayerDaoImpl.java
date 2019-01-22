
package source.controller;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import interfaces.PlayerDao;
import source.model.*;

import java.io.*;
import java.util.ArrayList;


/**
 * It is responsible for extracting the player data
 * from the local directory in to the game.
 */
@SuppressWarnings("Duplicates")
public class PlayerDaoImpl implements PlayerDao{

    private Gson gson = new Gson();


    /**
     * Extracts the player information from local directory, creates player arraylist.
     * @return  a Player array to save the extracted information.
     */
    @Override
    public ArrayList<Player> extractPlayers()
    {
        ArrayList<Player> players = new ArrayList<>();
        File folder = new File(DataConfiguration.getInstance().dataPath + "\\players");
        File[] list = folder.listFiles();
        FileReader reader;
        Player player;
        for (int i = 0; i < list.length; i++)
        {
            if (!list[i].isDirectory())
            {
                continue;
            }
            reader = createReader(list[i].getPath() + "/playerInfo.json", list[i].getName());
            if (reader == null)
            {
                continue;
            }
            try {
                player = gson.fromJson(reader, Player.class);
                players.add(player);
            } catch (JsonParseException e)
            {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                DataErrorHandler.handlePlayerDamagedError(list[i].getName());
            }

        }
        return players;
    }


    /**
     * Extracts the last selected players name.
     * @return last selected players name.
     */
    @Override
    public String extractLastPlayerName()
    {
        return GameInfo.getInstance().lastActivePlayer;
    }


    /**
     * Creates a folder and an info text for the new player.
     * @param playerName Name of the player.
     * @param settings Settings of the player.
     * @return the new player that is added to the game system.
     */
    @Override
    public Player createPlayer(String playerName, Settings settings) {
        OriginalLevel originalLevel;
        String playerPath = DataConfiguration.getInstance().dataPath + "\\players\\" + playerName;

        //creates player folder
        File newFolder = new File(playerPath);
        newFolder.mkdirs();
        newFolder.setWritable(true);

        createFile(playerPath + "/playerInfo.json");


        boolean unlocked;
        ArrayList<LevelInformation> levels = new ArrayList<>();
        System.out.println(GameInfo.getInstance().numberOfMaps);
        for (int i = 1; i <= GameInfo.getInstance().numberOfMaps; i++)
        {
            InputStream input = PlayerDaoImpl.class.getClassLoader().getResourceAsStream("data/levels/level" + i + ".json");
            InputStreamReader reader = new InputStreamReader(input);
            originalLevel = gson.fromJson(reader, OriginalLevel.class);
            levels.add(new LevelInformation(0, "notStarted", i, originalLevel.expectedMovesForThreeStars, originalLevel.expectedMovesForTwoStars, 0, i == 1, "", originalLevel.time));
        }
        Player newPlayer = new Player(playerName, 0, levels, playerPath, settings, 3, 3);
        newPlayer.resetLastUnlockedLevelNo();
        String text = gson.toJson(newPlayer);
        writeFile(playerPath + "/playerInfo.json", text);
        return newPlayer;
    }


    /**
     * Deletes the folder of the player.
     * @param player the player that is wanted to be deleted.
     * @return a boolean to check whether the operation is successful.
     */
    @Override
    public boolean deletePlayer(Player player){
        File file = new File(player.getPath() + "/playerInfo.json");
        file.delete();

        File folder = new File(player.getPath());
        if (folder.delete()) {
            return true;
        }
        return false;
    }


    /**
     * Saves last active player to make it comfortable to start.
     * @param playerName the name of the last active player.
     */
    @Override
    public void saveLastActivePlayer(String playerName)
    {
        GameInfo.getInstance().updateInfo(playerName);
    }


    /**
     * Saves all the information about a player to the representative folder.
     * @param player the player wanted to be saved.
     */
    @Override
    public void savePlayer(Player player)
    {
        String text = gson.toJson(player);
        writeFile(player.getPath() + "/playerInfo.json", text);
    }


    /**
     * Changes players name.
     * @param player the player to be changed by name.
     */
    @Override
    public void changePlayerName(Player player){
        File folder = new File(player.getPath());
        folder.renameTo(new File(folder.getParent() + "/" + player.getPlayerName()));
        player.setPath(DataConfiguration.getInstance().dataPath + "\\players\\"+  player.getPlayerName());
        savePlayer(player);
    }


    /**
     * Writes a file
     * @param path the desired path
     * @param text the desired tect
     */
    private void writeFile(String path, String text) {

        //text = decrypt(text);
        FileWriter fileOut = null;
        try {
            fileOut = new FileWriter(path);
            fileOut.write(text);
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Creates reader to for path.
     * @param path the desired path.
     * @param name the desired name.
     * @return the file.
     */
    private FileReader createReader(String path, String name)
    {
        try {
            return new FileReader(path);

        } catch (FileNotFoundException e) {
            DataErrorHandler.handlePlayerDamagedError(name);
            //createReader(path, name);
        }
        return null;
    }


    /**
     * Creates file for the path.
     * @param path the desired path.
     */
    private void createFile(String path)
    {
        File newFile = new File(path);
        try {
            newFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Private class for the info of last active player.
     */
    private static class Info {
        private  String lastActivePlayer;
        private  int numberOfMaps;
    }

    // May be needed to fix file issues
    /*
    private void convertLevelsFromTxtToJson()
    {
        OriginalLevel level = new OriginalLevel();
        Scanner scan = null;
        boolean bonus;
        int time, movesForThreeStars, movesForTwoStars;
        String tmp, map, line;
        for (int i = 1; i <= 50; i++)
        {
            bonus = false;
            time = -1;
            movesForThreeStars = 0;
            movesForTwoStars = 0;
            map = "";
            try {
                scan = new Scanner(new File("src/data/levels/level" + i + ".txt"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (scan.nextLine().trim().equals("<IsBonusMap>"))
            {
                time = Integer.parseInt(scan.nextLine().trim());
                bonus = true;
            }
            if (bonus) {
                while (!scan.nextLine().trim().equals("<ExpectedNumberOfMovesForThreeStars>")) ;
                tmp = scan.nextLine().trim();
                movesForThreeStars = Integer.parseInt(tmp);
            }
            else
            {
                tmp = scan.nextLine().trim();
                movesForThreeStars = Integer.parseInt(tmp);
            }

            while (!scan.nextLine().trim().equals("<ExpectedNumberOfMovesForTwoStars>")) ;
            tmp = scan.nextLine().trim();
            movesForTwoStars = Integer.parseInt(tmp);

            while (!scan.nextLine().trim().equals("<Map>")) ;
            line = scan.nextLine().trim();
            while (!line.equals("<Map/>"))
            {
                map = map + line + " | ";
                line = scan.nextLine().trim();
            }
            map = map.substring(0, map.length() - 3);

            level.time = time;
            level.expectedMovesForThreeStars = movesForThreeStars;
            level.expectedMovesForTwoStars = movesForTwoStars;
            level.map = map;

            createFile("src/data/levels/level" + i +".json");
            String text = gson.toJson(level);
            writeFile("src/data/levels/level" + i +".json", text);

        }
    }
    */

}