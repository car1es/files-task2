package files.task2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        String path = "Games/savegames/";
        List<String> listFiles = new ArrayList<>();
        listFiles.add(path + "save1.dat");
        listFiles.add(path + "save2.dat");
        listFiles.add(path + "save3.dat");
        GameProgress gp1 = new GameProgress(100, 40, 3, 50.3);
        GameProgress gp2 = new GameProgress(80, 90, 9, 99.3);
        GameProgress gp3 = new GameProgress(65, 85, 6, 70);
        saveGame(path + "save1.dat", gp1);
        saveGame(path + "save2.dat", gp2);
        saveGame(path + "save3.dat", gp3);
        zipFiles(path + "backup.zip", listFiles);
        deleteFiles(path);


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
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (int i = 0; i < files.size(); i++) {
                try (FileInputStream fis = new FileInputStream(files.get(i))) {
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
    }

    public static boolean deleteFiles(String path) {
        boolean result = false;
        File dir = new File(path);
        for (File file : dir.listFiles()) {
            if (!file.getName().endsWith(".zip")) {
                file.delete();
                result = true;
            }
        }
        return result;
    }
}