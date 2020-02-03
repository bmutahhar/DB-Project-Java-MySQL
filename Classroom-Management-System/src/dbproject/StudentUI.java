package dbproject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

public class StudentUI extends JFrame {

    private JPanel contentPane;
    private JTextField StudentReg;
    private JTextField StudentName;
    private JTextField userName;
    private JTextField InstructorName;
    private JTextField Course;
    private JTextField Year;
    private JTextField Department;
    private JTextField SemesterTextField;
    private JTextField userType;
    private JTextField Section;
    private JLabel StudentRegLabel, StudentNameLabel, InstructorIdLabel, InstructorNameLabel,
            DepartmentLabel, CourseLabel, YearLabel, SemesterLabel, usernameLabel, SectionLabel;
    private static Login log = new Login();
    private static TableUI table = new TableUI();
    private static FromDB instanceOfDB;


    public StudentUI() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 568, 360);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        instanceOfDB = new FromDB();
        instanceOfDB.setConnection();


        usernameLabel = new JLabel("Username :");
        usernameLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        usernameLabel.setBounds(164, 34, 77, 14);
        contentPane.add(usernameLabel);

        JLabel UserTypeLabel = new JLabel("User Type");
        UserTypeLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        UserTypeLabel.setBounds(164, 62, 77, 14);
        contentPane.add(UserTypeLabel);

        userType = new JTextField();
        userType.setText(log.getUserType());
        userType.setColumns(10);
        userType.setBounds(260, 59, 98, 20);
        contentPane.add(userType);
        userType.setEditable(false);

        userName = new JTextField();
        userName.setText("Mutahhar");
        userName.setEditable(false);
        userName.setBounds(260, 31, 98, 20);
        contentPane.add(userName);
        userName.setColumns(10);

        StudentRegLabel = new JLabel("Student Reg No.");
        StudentRegLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        StudentRegLabel.setBounds(30, 125, 98, 20);
        contentPane.add(StudentRegLabel);

        StudentReg = new JTextField();
        StudentReg.setFont(new Font("Tahoma", Font.PLAIN, 11));
        StudentReg.setBounds(126, 126, 98, 20);
        contentPane.add(StudentReg);

        StudentNameLabel = new JLabel("Student Name:");
        StudentReg.setFont(new Font("Tahoma", Font.BOLD, 11));
        StudentNameLabel.setBounds(290, 123, 98, 20);
        contentPane.add(StudentNameLabel);

        StudentName = new JTextField();
        StudentName.setBounds(398, 125, 98, 20);
        contentPane.add(StudentName);

        DepartmentLabel = new JLabel("Department :");
        DepartmentLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        DepartmentLabel.setBounds(290, 164, 86, 14);
        contentPane.add(DepartmentLabel);

        Department = new JTextField();
        Department.setBounds(398, 161, 96, 20);
        contentPane.add(Department);
        Department.setColumns(10);

        CourseLabel = new JLabel("Course ID :");
        CourseLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        CourseLabel.setBounds(30, 164, 77, 14);
        contentPane.add(CourseLabel);

        Course = new JTextField();
        Course.setBounds(126, 161, 98, 20);
        contentPane.add(Course);
        Course.setColumns(10);

        SectionLabel = new JLabel("Section: ");
        SectionLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        SectionLabel.setBounds(30, 195, 98, 20);
        contentPane.add(SectionLabel);

        Section = new JTextField();
        Section.setBounds(126, 198, 98, 20);
        contentPane.add(Section);
        Section.setColumns(10);


        SemesterLabel = new JLabel("Semester:");
        SemesterLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        SemesterLabel.setBounds(290, 203, 86, 14);
        contentPane.add(SemesterLabel);

        SemesterTextField = new JTextField();
        SemesterTextField.setColumns(10);
        SemesterTextField.setBounds(398, 200, 98, 20);
        contentPane.add(SemesterTextField);


        JButton List = new JButton("List");
        List.setBounds(283, 246, 96, 23);
        contentPane.add(List);

        JButton Clear = new JButton("Clear");
        Clear.setBounds(153, 246, 96, 23);
        contentPane.add(Clear);

        List.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                //Select based on Student registration number
                if (!StudentReg.getText().equals("") && StudentName.getText().equals("") &&
                        Course.getText().equals("") && Department.getText().equals("") &&
                        SemesterTextField.getText().equals("") && Section.getText().equals("")) {

                    table.setTypeLabel("Student Name: ");
                    table.getTypeLabel().setBounds(330, 18, 100, 35);
                    table.getTypeTextField().setVisible(true);
                    table.setTypeTextField(instanceOfDB.selectNames("name",
                            "student", "stu_reg", StudentReg.getText()));

                    instanceOfDB.displayTimeSlots("student", "stu_reg", StudentReg.getText(), table.getTable());
                    table.setVisible(true);

                }
                //Select based on student name
                else if (StudentReg.getText().equals("") && !StudentName.getText().equals("") &&
                        Course.getText().equals("") && Department.getText().equals("") &&
                        SemesterTextField.getText().equals("") && Section.getText().equals("")) {

                    table.setTypeLabel("Student Name: ");
                    table.getTypeLabel().setBounds(330, 18, 100, 35);
                    table.setTypeTextField(StudentName.getText());
                    table.getTypeTextField().setVisible(true);
                    instanceOfDB.displayTimeSlots("student", "name", StudentName.getText(), table.getTable());
                    table.setVisible(true);

                }
                //select based on course
                else if (StudentReg.getText().equals("") && StudentName.getText().equals("")
                        && !Course.getText().equals("") && Department.getText().equals("") &&
                        SemesterTextField.getText().equals("") && Section.getText().equals("")) {

                    table.setTypeLabel("Course: ");
                    table.getTypeLabel().setBounds(330, 18, 100, 35);
                    table.setTypeTextField(instanceOfDB.selectNames("title", "course", "course_id", Course.getText()));
                    table.getTypeTextField().setVisible(true);
                    instanceOfDB.displayTimeSlots("takes", "course_course_id", Course.getText(), table.getTable());
                    table.setVisible(true);

                }
                //select based on department
                else if (StudentReg.getText().equals("") && StudentName.getText().equals("")
                        && Course.getText().equals("") && !Department.getText().equals("") &&
                        SemesterTextField.getText().equals("") && Section.getText().equals("")) {
                    table.setTypeLabel("Department: ");
                    table.getTypeLabel().setBounds(330, 18, 100, 35);
                    table.setTypeTextField(Department.getText());
                    table.getTypeTextField().setVisible(true);
                    instanceOfDB.displayTimeSlots("student", "department_dept_name", Department.getText(), table.getTable());
                    table.setVisible(true);
                }
                //select based on semester
                else if (StudentReg.getText().equals("") && StudentName.getText().equals("")
                        && Course.getText().equals("") && Department.getText().equals("") &&
                        !SemesterTextField.getText().equals("") && Section.getText().equals("")) {

                    table.setTypeLabel("Semester: ");
                    table.getTypeLabel().setBounds(330, 18, 100, 35);
                    table.setTypeTextField(SemesterTextField.getText());
                    table.getTypeTextField().setVisible(true);
                    instanceOfDB.displayTimeSlots("student", "section_semester", SemesterTextField.getText(), table.getTable());
                    table.setVisible(true);

                }
                //select based on section
                else if (StudentReg.getText().equals("") && StudentName.getText().equals("")
                        && Course.getText().equals("") && Department.getText().equals("") &&
                        SemesterTextField.getText().equals("") && !Section.getText().equals("")) {

                    table.setTypeLabel("Section: ");
                    table.getTypeLabel().setBounds(330, 18, 100, 35);
                    table.setTypeTextField(Section.getText());
                    table.getTypeTextField().setVisible(true);
                    instanceOfDB.displayTimeSlots("student", "section_sec_id", Section.getText(), table.getTable());
                    table.setVisible(true);

                }
                //select based on semester and section both
                else if (StudentReg.getText().equals("") && StudentName.getText().equals("")
                        && Course.getText().equals("") && Department.getText().equals("") &&
                        !SemesterTextField.getText().equals("") && !Section.getText().equals("")) {

                    table.setTypeLabel("Section & Semester: ");
                    table.getTypeLabel().setBounds(330, 18, 100, 35);
                    table.setTypeTextField((SemesterTextField.getText() + Section.getText()));
                    table.getTypeTextField().setVisible(true);
                    instanceOfDB.displayTimeSlotsBasedOnMultipleValues("student",
                            "section_semester", SemesterTextField.getText(), "student",
                            "section_sec_id", Section.getText(), table.getTable());
                    table.setVisible(true);
                }

                //select all values on relation
                else if (StudentReg.getText().equals("") && StudentName.getText().equals("")
                        && Course.getText().equals("") && Department.getText().equals("") &&
                        SemesterTextField.getText().equals("") && Section.getText().equals("")) {
                    table.getTypeLabel().setBounds(450, 25, 150, 20);
                    table.getTypeLabel().setText("      Entire Relation");
                    table.getTypeTextField().setVisible(false);
                    instanceOfDB.selectAll(table.getTable());
                    table.setVisible(true);
                }

            }
        });


        Clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                StudentReg.setText("");
                StudentName.setText("");
                Course.setText("");
                Department.setText("");
                SemesterTextField.setText("");
                Section.setText("");
            }
        });


    }

    public static void main(String[] args) {
        StudentUI student = new StudentUI();
        student.setVisible(true);
    }

    public String getUserName() {
        return userName.getText();
    }

    public void setUserName(String text) {
        userName.setText(text);
    }
}
