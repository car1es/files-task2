package files.task2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        List<String> listFiles = new ArrayList<>();
        listFiles.add("Games/savegames/save1.dat");
        listFiles.add("Games/savegames/save2.dat");
        listFiles.add("Games/savegames/save3.dat");
        GameProgress gp1 = new GameProgress(100, 40, 3, 50.3);
        GameProgress gp2 = new GameProgress(80, 90, 9, 99.3);
        GameProgress gp3 = new GameProgress(65, 85, 6, 70);
        saveGame("Games/savegames/save1.dat", gp1);
        saveGame("Games/savegames/save2.dat", gp2);
        saveGame("Games/savegames/save3.dat", gp3);
        zipFiles("Games/savegames/zip.zip", listFiles);

    }

    public static void saveGame(String path, GameProgress gp) {
        try (ObjectOutputStream outs = new ObjectOutputStream(new FileOutputStream(path))) {
            outs.writeObject(gp);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void zipFiles(String path, List<String> files) {
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))){
            for (int i = 0; i < files.size(); i++) {
                try(FileInputStream fis = new FileInputStream(files.get(i))){
                    ZipEntry entry = new ZipEntry(files.get(i));
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File dir = new File("Games/savegames/");
        for (File file : dir.listFiles()){
            if (!file.getName().endsWith(".zip")){
                file.delete();
            }
        }
    }
}