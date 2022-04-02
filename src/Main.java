import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {

    public static GameProgress openProgress(String dir){
        GameProgress gameProgress = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dir))){

           gameProgress = (GameProgress) ois.readObject();
        }
catch (Exception e){
            e.getMessage();
} return gameProgress;
    }

    public static void SaveGame(String dir, GameProgress gmp) {
        try (FileOutputStream fos = new FileOutputStream(dir);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gmp);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void openZipFile(String dir) {

        try (ZipInputStream zinput = new ZipInputStream(new FileInputStream(dir + "/SaveGame.zip"))) {
            ZipEntry entry;
            String name;
            while ((entry = zinput.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(dir + "/" + name);
                for (int c = zinput.read(); c != -1; c = zinput.read()) {
                    fout.write(c);
                }
                fout.flush();
                zinput.closeEntry();
                fout.close();
            }
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
        String[] dirName = {"", "src", "res", "savegames", "temp", "res/drawables",
                "res/vectors", "res/icons", "src/main", "src/test"};
        String[] fileName = {"src/main/Main.java", "src/main/Utils.java", "/temp/temp.txt"};
        try {


            for (int i = 0; i < dirName.length; i++) {
                if (new File(dir + "/" + dirName[i]).mkdir()) {
                    str.append("Directory: " + dir + "/" + dirName[i] + " create\n");
                }
            }
            for (int i = 0; i < fileName.length; i++) {
                if (new File(dir + "/" + fileName[i]).createNewFile()) {
                    str.append("Directory: " + dir + "/" + fileName[i] + " create\n");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        openZipFile(dir);

        System.out.println(openProgress(dirList.get(1)));
    }
}

