import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;

public class Main {

    static String configPath;
    static String configFileName;
    
    private static JFrame jf;
    private static JList<String> dirList;
    private static JTextArea contentArea;
    private static JButton jbEditAndSave;

    static void importConfig() {
        File f = new File("./config.txt");
        if(f.exists()){
            configPath = getFileContent(f);
        }else{
            setConfig();
        }
    }

    static void setConfig() {
        File f = new File("./config.txt");
        if(!f.exists()){
            configPath = "./notes/";
            writeFile(f, configPath);
        }else {
            if(f.delete()){
                openFileChooser();
            }
        }
    }

    static void openFileChooser() {
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setMultiSelectionEnabled(false);

        int result = jfc.showOpenDialog(jfc);
        if(result == JFileChooser.APPROVE_OPTION) {
            configPath = jfc.getSelectedFile().getPath() + "\\";
        }

        File f = new File("./config.txt");
        writeFile(f, configPath);
    }
    

    static void displayNote(String content){
        contentArea.setText(content);
        contentArea.setCaretPosition(0);
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
        configFileName = JOptionPane.showInputDialog(jf, "请输入文件名：");
        
        if(configFileName != null){
            File f = new File(configPath + configFileName);
            writeFile(f, "");
            showPath();
            displayNote(getFileContent(f));
            
            contentArea.setEditable(true);
            contentArea.requestFocus();
            jbEditAndSave.setText("Save");
        }
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
        File f = new File(configPath);
        String[] notesList = f.list();
        if(notesList != null){
            dirList.setListData(notesList);
        }
    }

    static void chooseNote(){
        int[] indices = dirList.getSelectedIndices();
        ListModel<String> listModel = dirList.getModel();
        if(dirList.getValueIsAdjusting()){
            for(int index : indices){
                configFileName = listModel.getElementAt(index);
                File f = new File(configPath + configFileName);
                displayNote(getFileContent(f));
            }
        }
    }

    static void deleteFile(File f){
        try{
            if(f.delete()){
                JOptionPane.showMessageDialog(jf, "删除成功！");
            }else{
                JOptionPane.showMessageDialog(jf, "删除失败！");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static void deleteNote(){
        int confirm = JOptionPane.showConfirmDialog(jf, "确认删除？", "删除提醒", JOptionPane.YES_NO_CANCEL_OPTION);
        if(confirm == JOptionPane.YES_OPTION){
            configFileName = dirList.getSelectedValue();
            if(configFileName != null){
                File f = new File(configPath + configFileName);
                deleteFile(f);
                showPath();
                displayNote("");
            }

        }
    }

    static void editNote(){
        if(jbEditAndSave.getText().equals("Edit")){
            jbEditAndSave.setText("Save");
            contentArea.setEditable(true);
            contentArea.requestFocus();
        }else{
            jbEditAndSave.setText("Edit");
            configFileName = dirList.getSelectedValue();
            File f = new File(configPath + configFileName);
            String content = contentArea.getText();
            writeFile(f, content);
            contentArea.setEditable(false);
        }
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

        dirList.addListSelectionListener(e -> chooseNote());

        leftPanel.add(dirList,BorderLayout.CENTER);

        JPanel ctrlPanel = new JPanel();

        Box hBox1 = Box.createHorizontalBox();

        Box hBox2 = Box.createHorizontalBox();

        Box vBox = Box.createVerticalBox();

        JButton jbNew = new JButton("New");

        jbNew.addActionListener(e -> newFile());


        JButton jbDelete = new JButton("Delete");

        jbDelete.addActionListener(e -> deleteNote());

        hBox1.add(jbNew);
        hBox1.add(jbDelete);

        JButton jbSetting = new JButton("Setting");

        jbSetting.addActionListener(e -> {
            setConfig();
            showPath();
        });

        hBox2.add(jbSetting);

        vBox.add(hBox1);
        vBox.add(hBox2);

        ctrlPanel.add(vBox);

        leftPanel.add(ctrlPanel,BorderLayout.SOUTH);

        jsp.setLeftComponent(leftPanel);

        JPanel rightPanel = new JPanel(new BorderLayout());

        JPanel editPanel = new JPanel();
        jbEditAndSave = new JButton("Edit");

        jbEditAndSave.addActionListener(e -> editNote());

        editPanel.add(jbEditAndSave);

        contentArea = new JTextArea();
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
        importConfig();
        drawWindow();
        showPath();
    }
}