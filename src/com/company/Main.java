package com.company;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) throws IOException {
        GameProgress progress = new GameProgress(100, 1, 1, 0);
        GameProgress progress1 = new GameProgress(90, 3, 5, 100);
        GameProgress progress2 = new GameProgress(50, 10, 30, 1049);
        saveGame("D:/Games/savegames/save.dat", progress);
        saveGame("D:/Games/savegames/save1.dat", progress1);
        saveGame("D:/Games/savegames/save2.dat", progress2);

        List<String> file = new ArrayList();
        File dir = new File("D:/Games/savegames/");
        if (dir.isDirectory()) {
            for (File item : dir.listFiles()) {
                if (item.isDirectory()) {
                    System.out.println(item.getName() + " - каталог");
                } else {
                    file.add(item.getPath());
                }
            }
        }
        zipFiles("D:/Games/savegames/archive.zip", file);
        delete();
    }
    static void delete(){
        File dir = new File("D:/Games/savegames/");
        if (dir.isDirectory()) {
            for (File item : dir.listFiles()) {
                if (item.isDirectory()) {
                    System.out.println(item.getName() + " - каталог");
                } else {
                    if (item.getName().contains(".dat")){
                        item.delete();
                    }
                }
            }
        }
    }
    static void saveGame(String path, GameProgress gameProgress) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    static void zipFiles(String filename, List<String> files) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(filename))) {
            for (String file : files) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry entry = new ZipEntry(file.substring(file.lastIndexOf('\\')));
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


