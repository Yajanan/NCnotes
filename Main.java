import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;

public class Main {
    
    private static JFrame jf;
    private static JList<String> dirList;
    
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
            dirList.setListData(notesList);
        }
    }

    static void chooseNote(){
        System.out.println("Please type the filename which you want to read:");
        String filename = s.next();
        File f = new File("./notes/" + filename);
        displayNote(getFileContent(f));
    }

    static void drawWindow(){
        jf = new JFrame("NoteController");
        jf.setResizable(false);
        jf.setLocationRelativeTo(null);
        jf.setSize(600,800);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JSplitPane jsp = new JSplitPane();
        jsp.setDividerLocation(155);

        JPanel leftPanel = new JPanel(new BorderLayout());

        dirList = new JList<>();

        dirList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        leftPanel.add(dirList,BorderLayout.CENTER);

        JPanel ctrlPanel = new JPanel();

        Box hBox1 = Box.createHorizontalBox();

        Box hBox2 = Box.createHorizontalBox();

        Box vBox = Box.createVerticalBox();

        JButton jbNew = new JButton("New");


        JButton jbDelete = new JButton("Delete");

        hBox1.add(jbNew);
        hBox1.add(jbDelete);

        JButton jbSetting = new JButton("Setting");

        hBox2.add(jbSetting);

        vBox.add(hBox1);
        vBox.add(hBox2);

        ctrlPanel.add(vBox);

        leftPanel.add(ctrlPanel,BorderLayout.SOUTH);

        jsp.setLeftComponent(leftPanel);

        JPanel rightPanel = new JPanel(new BorderLayout());

        JPanel editPanel = new JPanel();
        JButton jbEditAndSave = new JButton("Edit");

        editPanel.add(jbEditAndSave);

        JTextArea contentArea = new JTextArea();
        contentArea.setLineWrap(true);
        contentArea.setEditable(false);
        contentArea.setFont(new Font("宋体",Font.PLAIN,18));

        JScrollPane contentScroll = new JScrollPane(contentArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        rightPanel.add(editPanel, BorderLayout.NORTH);
        rightPanel.add(contentScroll, BorderLayout.CENTER);

        jsp.setRightComponent(rightPanel);

        jf.add(jsp);
        jf.setVisible(true);
    }

    public static void main(String[] args) {
        drawWindow();
        showPath();
        s.close();
    }
}