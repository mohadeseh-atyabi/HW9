package com.company;

import java.io.*;

/**
 * Class to handle files.
 */
public class FileUtils {

    private static final String NOTES_PATH = ".\\notes\\";

    //It's a static initializer. It's executed when the class is loaded.
    //It's similar to constructor
    static {
        boolean isSuccessful = new File(NOTES_PATH).mkdirs();
        System.out.println("Creating " + NOTES_PATH + " directory is successful: " + isSuccessful);
    }

    /**
     * Returns the list of existing files
     * @return List of existing files
     */
    public static File[] getFilesInDirectory() {
        return new File(NOTES_PATH).listFiles();
    }

    /**
     * Reads a file using BufferReader
     * @param file File to read from
     * @return File's text as a string
     */
    public static String fileReader(File file) {
        //TODO: Phase1: read content from file
        BufferedReader reader = null;
        StringBuilder toReturn = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            while (reader.ready()){
                toReturn.append(reader.readLine()).append("\n");
            }

        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return toReturn.toString();
    }

    /**
     * Writes on an existing file or creates new file using BufferWriter.
     * @param content Text to write on the file
     */
    public static void fileWriter(String content) {
        //TODO: write content on file
        String fileName = getProperFileName(content);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(NOTES_PATH+fileName));
            writer.write(content);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * Reads a file using FileInputStream
     * @param file File to read from
     * @return File's text as a string
     */
    //TODO: Phase1: define method here for reading file with InputStream
    public static String fileStreamReader(File file){
        FileInputStream reader = null;
        StringBuilder toReturn = new StringBuilder();
        int temp;
        try {
            reader = new FileInputStream(file);
            while ((temp = reader.read()) != -1){
                toReturn.append((char) temp);
            }

        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        return toReturn.toString();
    }

    /**
     * Writes on an existing file or creates new file using FileOutputStream.
     * @param content Text to write on the file
     */
    //TODO: Phase1: define method here for writing file with OutputStream
    public static void fileStreamWriter(String content){
        String fileName = getProperFileName(content);
        FileOutputStream writer = null;
        byte[] toWrite = content.getBytes();
        try {
            writer = new FileOutputStream(NOTES_PATH+fileName);
            writer.write(toWrite);

        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * Returns proper parts to creat a new note to write on a file.
     * @param content Information of the text of the file
     * @return Different parts of a note.
     */
    //TODO: Phase2: proper methods for handling serialization
    public static String[] separateParts(String content){
        String[] parts = new String[3];
        if (content.startsWith("Note{title='")){
            content = content.replace("Note{title='" ,"");
            String[] temp = content.split("', content='" , 2);
            parts[0] = temp[0];

            content = temp[1].replace("', content='" , "");
            temp = content.split("', date='" , 2);
            parts[1] = temp[0];

            content = temp[1].replace("', date='" , "");
            parts[2] = content.replace("}" , "");
        }
        else {
            parts[0] = getProperFileName(content);
            parts[1] = content.replace(parts[0] , "");
            parts[2] = java.time.LocalDate.now().toString();
        }
        return parts;
    }

    /**
     * Writes on an existing file or creates new file using ObjectOutputStream.
     * @param content Text to write on the file
     */
    public static void fileSerializeWriter(String content){
        String[] noteParts = separateParts(content);
        Note note = new Note(noteParts[0] , noteParts[1] ,
                noteParts[2]);

        try (FileOutputStream f = new FileOutputStream(NOTES_PATH+noteParts[0])){
            ObjectOutputStream writer = new ObjectOutputStream(f);
            writer.writeObject(note);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Reads a file using ObjectInputStream
     * @param file File to read from
     * @return File's text as a string
     */
    public static String fileSerializeReader(File file){
        Note note = null;
        try (FileInputStream f = new FileInputStream(file)){
            ObjectInputStream reader = new ObjectInputStream(f);
            note = (Note) reader.readObject();

        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
        }

        if (note != null) {
            return note.getTitle() + note.getContent() +"\n"+ note.getDate();
//            return note.toString();
        }else
            return null;
    }

    /**
     * Returns a note to be written in the JList
     * @param file File to read the object from
     * @return Note
     */
    public static Note listText(File file){
        Note note = null;
        try (FileInputStream f = new FileInputStream(file)){
            ObjectInputStream reader = new ObjectInputStream(f);
            note = (Note) reader.readObject();

        } catch (IOException | ClassNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return note;
    }

    /**
     * Returns a proper name according to the input.
     * @param content Input
     * @return Proper noun
     */
    private static String getProperFileName(String content) {
        int loc = content.indexOf("\n");
        if (loc != -1) {
            return content.substring(0, loc);
        }
        if (!content.isEmpty()) {
            return content;
        }
        return System.currentTimeMillis() + "_new file.txt";
    }
}
