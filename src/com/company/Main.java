package com.company;

import java.io.*;
import java.util.ArrayDeque;
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

        ArrayDeque<String> filelist = new ArrayDeque<String>();
        File dir = new File("D:/Games/savegames/");
        if (dir.isDirectory()) {
            for (File item : dir.listFiles()) {
                if (item.isDirectory()) {
                    System.out.println(item.getName() + " - каталог");
                } else {
                    filelist.add(item.getName());
                }
            }
        }

        zipFiles("D:/Games/savegames/archive.zip", filelist);
    }


    static void saveGame(String path, GameProgress gameProgress) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
    static void zipFiles(String path, ArrayDeque<String> filelist) throws IOException {
        ZipOutputStream zout = new ZipOutputStream(new FileOutputStream("D:/Games/savegames/archive.zip"));
        while(filelist.peek()!=null){
            FileInputStream fis = new FileInputStream(filelist.peek());
            ZipEntry entry = new ZipEntry(filelist.peek());
            zout.putNextEntry(entry);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            zout.write(buffer);
            zout.closeEntry();
            filelist.pop();
        }
    }
}


