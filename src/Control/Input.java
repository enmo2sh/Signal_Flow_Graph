package Control;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.util.List;
import java.util.Map;
import javax.swing.text.*;

public class Input{

    private JFrame frame;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private static Map<String, List<relation>> mymap;
    LinkedList<String> Loops=new LinkedList<>();
    LinkedList<String>Forward=new LinkedList<>();
    LinkedList<String>nonTouched=new LinkedList<>();
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Input window = new Input();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public Input() {
        initialize();
    }
    private void initialize() {
        LinkedList<String> lines=new LinkedList<>();
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(128, 0, 128));
        frame.setForeground(new Color(51, 51, 153));
        frame.setBackground(new Color(51, 51, 153));
        frame.setBounds(100, 100, 662, 520);
        ImageIcon icon = new ImageIcon(System.getProperty("user.dir")+ File.separator+"background.png");
        frame.getContentPane().setLayout(null);
        JLabel label = new JLabel(icon);
        label.setBounds(0, 0, 281, 519);
        label.setForeground(Color.PINK);
        frame.getContentPane().add(label);
        JButton btnNewButton = new JButton("SOLVE");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(lines.size()>0&&!textField_3.getText().equals("")) {
                    int n=Integer.parseInt(textField_1.getText());
                    methods call=new methods(n);
                    String LastVertex=textField_3.getText();
                    if(!LastVertex.contains("V")){
                        JOptionPane.showMessageDialog(null, "the vertex should be V+no.of vertex");
                        return;
                    }
                    try {
                        int no=Integer.parseInt(LastVertex.replaceAll("V",""));
                        if(no<=0||no>n){
                            JOptionPane.showMessageDialog(null, "the vertex should be V+[1...n]");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "the vertex should be V+no.of vertex");
                        return;
                    }
                    for(int i=0;i<lines.size();i++) {
                      mymap=call.getinputs(lines.get(i));
                    }
                    LinkedList<String> Forward=call.getForwadPaths("V1","",LastVertex,new LinkedList<>());
                    LinkedList<String> Loops=call.getLoops("V1","",new LinkedList<>());
                    LinkedList<String>nonTouchedLoops=call.getNonTouchingLoops();
                    call.getNonTouchingForward();
                    System.out.println("Forwrd Paths: ");
                    for (int j=0;j<Forward.size();j++) {
                        System.out.println(Forward.get(j));
                    }
                    System.out.println("***************************************");
                    System.out.println("Loops: ");
                    for (int j=0;j<Loops.size();j++) {
                        System.out.println(Loops.get(j));
                    }
                    System.out.println("*****************************************");
                    System.out.println("Non Touching Loops: ");
                    for (int j=0;j<nonTouchedLoops.size();j++) {
                        System.out.println(nonTouchedLoops.get(j));
                    }
                     GUI x=new GUI();
                    x.setProps(n,mymap,call.result1(),call.result2());
                }
            }
        });
        btnNewButton.setForeground(new Color(0, 191, 255));
        btnNewButton.setBackground(new Color(255, 228, 181));
        btnNewButton.setBounds(486, 428, 112, 24);
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        frame.getContentPane().add(btnNewButton);
        frame.getContentPane().setLayout(null);
        DefaultStyledDocument document = new DefaultStyledDocument();
        JTextPane textPane = new JTextPane(document);
        StyleContext context = new StyleContext();
        Style style = context.addStyle("test", null);
        textPane.setEditable(false);

        JScrollPane scrollableTextPane = new JScrollPane(textPane);
        scrollableTextPane.setBounds(291, 214, 329, 194);

        scrollableTextPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollableTextPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        textPane.setBackground(new Color(128, 0, 128));

        frame.getContentPane().add(scrollableTextPane);

        btnNewButton.setToolTipText("YOU SHOULD INSERT THE RELATIONS");

        textField = new JTextField();
        textField.setBounds(325, 138, 239, 24);
        frame.getContentPane().add(textField);
        textField.setColumns(10);
        textField.setBackground(new Color(255, 228, 181));
        textField.setForeground(new Color(128, 0, 128));

        textField_1 = new JTextField();
        textField_1.setBackground(new Color(255, 228, 181));
        textField_1.setBounds(485, 21, 96, 19);
        frame.getContentPane().add(textField_1);
        textField_1.setColumns(10);


        textField_3 = new JTextField();
        textField_3.setBackground(new Color(255, 228, 181));
        textField_3.setBounds(485, 62, 96, 19);
        frame.getContentPane().add(textField_3);
        textField_3.setColumns(10);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JButton btnAdd = new JButton("ADD");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if((!textField.getText().equals(""))&&(!textField_1.getText().equals(""))) {
                    String line=textField.getText();
                    int n;
                    try {
                        n=Integer.parseInt(textField_1.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "n should be digit");
                        return;
                    }
                    methods c=new methods(n);
                    boolean valid=c.isValid(line);
                    if(valid==true) {
                        lines.add(line);
                        StyleConstants.setForeground(style, Color.YELLOW);
                        try {
                            document.insertString(document.getLength(),line + "\n", style);
                        } catch (BadLocationException ex) {
                            ex.printStackTrace();
                        }
                        textField.setText("");
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "wrong relation");
                        StyleConstants.setForeground(style, Color.BLACK);
                        try {
                            document.insertString(document.getLength(),line + "\n", style);
                            textField.setText("");
                        } catch (BadLocationException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "you should enter n and relation before ADD");
                    return;
                }
            }
        });
        btnAdd.setBackground(new Color(255, 255, 255));
        btnAdd.setForeground(new Color(0, 191, 255));
        btnAdd.setBounds(361, 182, 70, 21);
        frame.getContentPane().add(btnAdd);
        btnAdd.setToolTipText("YOU SHOULD INSERT THE RELATION AND NUMBER OF VERTICES");

        JButton btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lines.clear();
                textPane.setText("");
            }
        });
        btnClear.setForeground(new Color(0, 191, 255));
        btnClear.setBackground(new Color(255, 255, 255));
        btnClear.setBounds(452, 182, 70, 21);
        frame.getContentPane().add(btnClear);

        JLabel lblNoofvertices = new JLabel("no.Of.vertices:");
        lblNoofvertices.setForeground(new Color(0, 0, 0));
        lblNoofvertices.setBounds(331, 19, 100, 19);
        lblNoofvertices.setFont(new Font("Tahoma", Font.PLAIN, 15));
        frame.getContentPane().add(lblNoofvertices);

        JLabel lblEnterRelation = new JLabel("enter relations : ex   \"V2=A12*V1+-A32*V3\"");
        lblEnterRelation.setForeground(new Color(0, 0, 0));
        lblEnterRelation.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblEnterRelation.setBounds(303, 103, 295, 36);
        frame.getContentPane().add(lblEnterRelation);

        JLabel lblTheLastVertex = new JLabel("the last vertex");
        lblTheLastVertex.setForeground(new Color(0, 0, 0));
        lblTheLastVertex.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblTheLastVertex.setBounds(331, 55, 118, 28);
        frame.getContentPane().add(lblTheLastVertex);

    }
}
