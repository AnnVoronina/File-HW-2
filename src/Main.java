import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void SaveGame(String dir, GameProgress gmp) {
        try (FileOutputStream fos = new FileOutputStream(dir);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gmp);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipFile(String dir, List<String> dirList) {

        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(dir + "/SaveGame.zip"))) {
            for (var d : dirList) {
                try (FileInputStream fis = new FileInputStream(d)) {
                    ZipEntry entry = new ZipEntry(new File(d).getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }


    public static void main(String[] args) throws IOException {
        String dir = "/Users/annavoronina/Game";
        StringBuilder str = new StringBuilder();
        String[] dirName = {"", "src", "res", "savegames", "temp"};

        try {
            for (int i = 0; i < dirName.length; i++) {

                if (new File(dir + "/" + dirName[i]).mkdir()) {
                    str.append("Directory: " + dir + "/" + dirName[i] + " create\n");
                }
            }
            new File("/Users/annavoronina/Game/src/main").mkdir();
            str.append("Directory: /Users/annavoronina/Game/src/main create\n");
            new File("/Users/annavoronina/Game/src/main", "Main.java").createNewFile();
            str.append("File: Main.java in /Users/annavoronina/Game/src/main  create\n");
            new File("/Users/annavoronina/Game/src/main/Utils.java").createNewFile();
            str.append("File: Utils.java in /Users/annavoronina/Game/src/main create\n");
            new File("/Users/annavoronina/Game/res/drawables").mkdir();
            str.append("Directory: /Users/annavoronina/Game/res/drawables create\n");
            new File("/Users/annavoronina/Game/res/vectors").mkdir();
            str.append("Directory: /Users/annavoronina/Game/res/vectors create\n");
            new File("/Users/annavoronina/Game/res/icons").mkdir();
            str.append("Directory: /Users/annavoronina/Game/res/icons create\n");
            new File("/Users/annavoronina/Game/src/test").mkdir();
            str.append("Directory: /Users/annavoronina/Game/src/test create\n");
            new File("/Users/annavoronina/Game/temp").mkdir();
            str.append("Directory: /Users/annavoronina/Game/temp create\n");
            new File("/Users/annavoronina/Game/temp", "temp.txt").createNewFile();
            str.append("File: temp.txt in /Users/annavoronina/Game/temp create\n");

        } catch (IOException exception) {
            str.append(exception.getMessage());

        }

        try (FileOutputStream fos = new FileOutputStream("/Users/annavoronina/Game/temp/temp.txt")) {
            byte[] bytes = str.toString().getBytes();
            fos.write(bytes, 0, bytes.length);
        } catch (IOException exception) {

            System.out.println(exception.getMessage());
        }
        List<String> dirList = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            dir = "/Users/annavoronina/Game/savegames/save" + i + ".dat";
            SaveGame(dir, new GameProgress(30 * i, 45 * i, 6 * i, 56.7 * i));
            dirList.add(dir);
        }
        dir = "/Users/annavoronina/Game/savegames";
        zipFile(dir, dirList);

        for (var d : dirList) {
            new File(d).delete();
        }


    }
}

