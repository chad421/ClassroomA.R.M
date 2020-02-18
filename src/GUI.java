import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI {

    JFrame frame;
    JPanel content;
    JPanel north;
    JPanel center;
    JPanel south;
    JTable table;
    JTextField studentName;
    JTextField studentGrade;
    JTextField studentAge;
    DefaultTableModel dtm;
    List<Student> studentArrayList = new ArrayList<Student>();
    Object[] dtmInput;
    private final Font futuraBody = new Font("Futura", Font.PLAIN, 14);
    private final Font futuraHeader = new Font("Futura", Font.BOLD, 36);
    private static final int WIDTH = 680, HEIGHT = WIDTH/12*9;

    public GUI() {
        makeMenu(); // Create JFrame
    }

    public void makeMenu() {
        frame = new JFrame("Classroom A.R.M");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(WIDTH, HEIGHT));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        content = new JPanel();
        north = new JPanel();
        center = new JPanel();
        south = new JPanel();

        northSetup(); // North component setup
        centerSetup(); // Center component setup
        southSetup(); // South component setup

        content.add(BorderLayout.NORTH, north);
        content.add(BorderLayout.CENTER, center);
        content.add(BorderLayout.SOUTH, south);
        frame.getContentPane().add(content);
        load(dtm, studentArrayList); // Retrieve saved data
        frame.repaint();
        frame.setVisible(true);
    }

    public void northSetup() {
        GridBagConstraints gbc = new GridBagConstraints();
        JButton buttonRm = new JButton("Remove");
        buttonRm.addActionListener(new RemoveListener());
        JLabel header = new JLabel("Classroom A.R.M");
        header.setFont(futuraHeader);
        buttonRm.setPreferredSize(new Dimension(100, 40));
        buttonRm.setFont(futuraBody);
        north.add(header);
        north.add(buttonRm);
    }

    public void centerSetup() {
        String[] columns = {"Name", "Grade", "Age"};
        dtm = new DefaultTableModel(columns, 0);
        table = new JTable(dtm);
        JScrollPane jsp = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        jsp.setPreferredSize(new Dimension(500, 300));
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        center.add(jsp);
    }

    public void southSetup() {
        JLabel nameLabel = new JLabel("Name");
        nameLabel.setFont(futuraBody);
        JLabel gradeLabel = new JLabel("Grade");
        gradeLabel.setFont(futuraBody);
        JLabel ageLabel = new JLabel("Age");
        ageLabel.setFont(futuraBody);
        studentName = new JTextField(20);
        studentName.setFont(futuraBody);
        studentGrade = new JTextField(2);
        studentGrade.setFont(futuraBody);
        studentAge = new JTextField(2);
        studentAge.setFont(futuraBody);
        JButton submit = new JButton("Submit");
        submit.setFont(futuraBody);
        submit.setPreferredSize(new Dimension(200, 50));
        submit.addActionListener(new FieldListener());
        south.add(nameLabel);
        south.add(studentName);
        south.add(gradeLabel);
        south.add(studentGrade);
        south.add(ageLabel);
        south.add(studentAge);
        south.add(submit);
    }

    public static void save(List<Student> list) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;

        try {
            fos = new FileOutputStream("StudentData.ser");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.flush();
            oos.close();
        } catch(FileNotFoundException ex) {
            ex.printStackTrace();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Saved!");
    }

    public static void load(DefaultTableModel dTable, List<Student> list) {
        Object[] dTableInput;
        String studentName;
        int studentGrade;
        int studentAge;

        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream("StudentData.ser");
            ois = new ObjectInputStream(fis);
            list = (ArrayList<Student>) ois.readObject();
        } catch(FileNotFoundException ex) {
            ex.printStackTrace();
        } catch(IOException ex) {
            ex.printStackTrace();
        } catch(ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        // Saving the data retrieved
        // to the table model
        for(Student std : list) {
            studentName = std.getName();
            studentGrade = std.getGrade();
            studentAge = std.getAge();
            dTableInput = new Object[]{studentName, studentGrade, studentAge};
            dTable.addRow(dTableInput);
        }
    }

    public void delete() {

    }

    public class FieldListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Student student;
            if(studentName.getText().equals("") || studentGrade.getText().equals("") || studentAge.getText().equals("")) {
                JOptionPane.showMessageDialog(frame,"Please fill out fields fully");
            } else {
                String name = studentName.getText();
                int grade = Integer.parseInt(studentGrade.getText());
                int age = Integer.parseInt(studentAge.getText());
                student = new Student(name, grade, age);
                studentArrayList.add(student);
                dtmInput = new Object[]{name, grade, age};
                dtm.addRow(dtmInput);
                studentName.setText("");
                studentGrade.setText("");
                studentAge.setText("");
                studentArrayList.add(new Student(name, grade, age));
                save(studentArrayList);
            }
        }
    }

    public class RemoveListener implements ActionListener {
        public void actionPerformed(ActionEvent ev){
            int selectedRow = table.getSelectedRow();
            dtm.removeRow(selectedRow);
        }
    }
}