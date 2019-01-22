package source.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MapGenerator {
    public static int size = 2267000;
    public static String[] grid = new String[size];
    public static String[] moves = new String[size];

    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\asus\\Desktop\\rush.txt"); // pathi değiştir
        BufferedReader br = new BufferedReader(new FileReader(file));
        String mapStr = "";
        char ch_V1;
        char ch_V2;
        char ch_H1;
        char ch_H2;
        int direction = 0; //RANDOM
        int cell = 0;
        String st;
        int i = 0;
        int previousCount = 0;
        int index = 0;
        int targetCount = 60;
        while ((st = br.readLine()) != null) {
            if (i >= 0&& i < 2267000) {  //buradaki değerleri değiştirin sadece, "size" kadar fark olsun arada ve değişkene atabilirsiniz
                moves[index] = st.split(" ")[0];
                grid[index] = st.split(" ")[1];
                index++;
            }
            i++;
        }

        for (int mapIndex = 0; mapIndex < size; mapIndex++) {
            StringBuilder map = new StringBuilder(grid[mapIndex]);

            mapStr = "";
            for (int a = -1; a < 7; a++) {
                for (int j = -1; j < 7; j++) {
                    if (a >= 0 && a < 6 && j >= 0 && j < 6) {
                        cell = a * 6 + j;
                        if (map.charAt(cell) == 'A') {
                            mapStr += "PC ";
                            map.setCharAt(cell + 1, 'p');
                        } else if (map.charAt(cell) == 'x') {
                            mapStr += "OO ";
                        } else if (map.charAt(cell) == 'o') {
                            mapStr += "SS ";
                        }else if (map.charAt(cell) == 'p') {
                            mapStr += "XX ";
                        } else {

                            char ch = map.charAt(cell);
                            if (cell + 6 < 36)
                                ch_V1 = map.charAt(cell + 6);
                            else
                                ch_V1 = '.';
                            if (cell + 12 < 36)
                                ch_V2 = map.charAt(cell + 12);
                            else
                                ch_V2 = '.';

                            if (cell + 1 < 36)
                                ch_H1 = map.charAt(cell + 1);
                            else
                                ch_H1 = '.';
                            if (cell + 2 < 36)
                                ch_H2 = map.charAt(cell + 2);
                            else
                                ch_H2 = '.';
                            direction = (int) (Math.random() * 2);

                            if (ch_H1 == ch && ch_H2 != ch) {

                                if (direction == 1)
                                    mapStr += "CR ";
                                else
                                    mapStr += "CL ";
                                map.setCharAt(cell + 1, 'p');
                            } else if (ch_H1 == ch && ch_H2 == ch) {

                                if (direction == 1)
                                    mapStr += "TR ";
                                else
                                    mapStr += "TL ";
                                map.setCharAt(cell + 1, 'p');
                                map.setCharAt(cell + 2, 'p');
                            } else if (ch_V1 == ch && ch_V2 != ch) {
                                if (direction == 1)
                                    mapStr += "CD ";
                                else
                                    mapStr += "CU ";

                                map.setCharAt(cell + 6, 'p');
                            } else if (ch_V1 == ch && ch_V2 == ch) {
                                if (direction == 1)
                                    mapStr += "TD ";
                                else
                                    mapStr += "TU ";

                                map.setCharAt(cell + 6, 'p');
                                map.setCharAt(cell + 12, 'p');
                            }
                        }
                    } else {
                        if (a == 2 && j == 6) //4th row last cell
                            mapStr += "SS ";
                        else
                            mapStr += "OO ";
                    }

                }
                mapStr = mapStr.substring(0, mapStr.length() - 1); //removes space at the end of the line but there is no need for it
                mapStr = mapStr + " | ";
            }

            if (Integer.parseInt(moves[mapIndex]) != previousCount && targetCount <= 60 && targetCount > 14) {
                System.out.println("MOVES: " + moves[mapIndex] + "\n");
                System.out.println(mapStr + "\n");
                previousCount = Integer.parseInt(moves[mapIndex]);
                targetCount--;
            }
            else if (Integer.parseInt(moves[mapIndex]) == previousCount)
            {
               //
            }
        }
    }

}

