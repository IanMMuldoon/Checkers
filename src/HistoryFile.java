import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class HistoryFile
{
    /*
    This class loads info from a text file with teh following tab-delimited format
    [id]/t[name]/t[wins]/t[losses]
     */

    final static String _fileName = "checkers.txt";

    private static File GetFile() throws Exception
    {
        File file = new File(HistoryFile._fileName.toString());
        if (!file.exists())
            file.createNewFile();

        return file;
    }

    public static void SaveName(String name)
    {
        File file = null;
        boolean nameexists = false;
        int nextID = 1;

        try
        {
            file = HistoryFile.GetFile();

            Scanner inputStream = new Scanner(file);
            while (inputStream.hasNextLine())
            {
                String line = inputStream.nextLine();
                /*if(line == ""){
                    line = inputStream.nextLine();
                }*/
                String[] values = line.split("\t");

                if (name.equals(values[1]))
                {
                    nameexists = true;
                    break;
                }

                int id  = Integer.parseInt( values[0]);
                if (id >= nextID)
                    nextID = id + 1;
            }
            inputStream.close();

            if (!nameexists)
            {
                String print = String.valueOf(nextID) + "\t" + name + "\t0\t0";

                //Create new line
                if (file.length() > 0)
                    print = "\n" + print;

                FileWriter fw = new FileWriter(file, true);
                fw.write(print);
                fw.close();
            }
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static HistoryRecord[] GetRecords()
    {
        List<HistoryRecord> names = new ArrayList<HistoryRecord>();
        File file = null;

        try
        {
            file = HistoryFile.GetFile();

            Scanner inputStream = new Scanner(file);
            while (inputStream.hasNextLine())
            {
                String line = inputStream.nextLine();
                String[] values = line.split("\t");

                names.add(new HistoryRecord(values));
            }
            inputStream.close();
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return names.toArray(new HistoryRecord[names.size()]);
    }

    private static void IncrementPlayerStat(int playerid, boolean recordwin)
    {
        try
        {
            // input the file content to the StringBuffer "input"
            BufferedReader file = new BufferedReader(new FileReader(HistoryFile.GetFile()));
            StringBuffer inputBuffer = new StringBuffer();
            String line;

            while ((line = file.readLine()) != null)
            {
                String[] values = line.split("\t");
                int id = Integer.parseInt(values[0]);
                if (id == playerid)
                {
                    if (recordwin)
                    {
                        int wins = Integer.parseInt(values[2]);
                       // System.out.println(wins);
                        values[2] = String.valueOf(wins + 1);
                    }
                    else
                    {
                        int losses = Integer.parseInt(values[3]);
                        values[3] = String.valueOf(losses + 1);
                    }

                    line = String.join("\t", values);
                }

                inputBuffer.append(line);
                if (file.ready())
                    inputBuffer.append('\n');
            }
            file.close();
            String inputStr = inputBuffer.toString();

            // write the new string with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream(HistoryFile.GetFile());
            fileOut.write(inputStr.getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
    }

    public static void RecordWin(int playerid)
    {
        HistoryFile.IncrementPlayerStat(playerid, true);
    }

    public static void RecordLoss(int playerid)
    {
        HistoryFile.IncrementPlayerStat(playerid, false);
    }
}