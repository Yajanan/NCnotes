import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    
    static Scanner s = new Scanner(System.in);

    static void displayNote(String content){
        System.out.println(content);
    }

    static String getFileContent(File f){
        try (FileReader fr = new FileReader(f)){
            char[] content = new char[(int)f.length()];
            int readLength = fr.read(content);
            if(readLength >= 0){
                return new String(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static void newFile(){
        System.out.println("Please type the filename:");
        String filename = s.next();
        
        System.out.println("Please type the content:");
        String content = s.next();

        File f = new File("./notes/" + filename);

        writeFile(f, content);
    }

    static void writeFile(File f, String content){
        try(FileWriter fw = new FileWriter(f)){
            char[] cs = content.toCharArray();
            fw.write(cs);
            fw.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    static void showPath(){
        File f = new File("./notes/");
        String[] notesList = f.list();
        if(notesList != null){
            for(String note : notesList){
                System.out.println(note);
            }
        }
    }

    static void chooseNote(){
        System.out.println("Please type the filename which you want to read:");
        String filename = s.next();
        File f = new File("./notes/" + filename);
        displayNote(getFileContent(f));
    }

    public static void main(String[] args) {
        System.out.println("Welcome to NCnotes!");
        System.out.println("Please type what you want to do:");
        System.out.println("N/n:new S/s:show path");

        String commend = s.next();

        switch(commend){
            case "N":
            case "n":
                newFile();
                break;
            case "S":
            case "s":
                showPath();
                chooseNote();
                break;
            default:
                break;
        }
        
        s.close();
    }
}