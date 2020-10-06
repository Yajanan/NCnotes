import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {

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

    public static void main(String[] args) {
        File f = new File("./a.txt");
        String content = getFileContent(f);
        displayNote(content);
    }
}