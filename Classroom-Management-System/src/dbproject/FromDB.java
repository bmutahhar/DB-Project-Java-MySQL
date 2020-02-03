package dbproject;

import java.awt.*;
import java.awt.print.PrinterException;
import java.sql.*;

import com.mysql.cj.jdbc.Driver;
import jdk.swing.interop.SwingInterOpUtils;
import net.proteanit.sql.DbUtils;

import javax.swing.*;

public class FromDB {
    // JDBC driver name and database URL
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3307/classroom";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    PreparedStatement preparedStatement;


    public void setConnection() {
        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            System.out.println("Connection Successful!");
            statement = connection.createStatement();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());

        }

    }

    public void insertStudent(String stuReg, String name, int tot_cred, String dept_name, String secID,
                              int semester, String program, String courseCode) {

        try {

            preparedStatement = connection.prepareStatement("INSERT INTO student VALUES (?,?,?,?,?,?,?)");
            preparedStatement.setString(1, stuReg);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, tot_cred);
            preparedStatement.setString(4, dept_name);
            preparedStatement.setString(5, secID);
            preparedStatement.setInt(6, semester);
            preparedStatement.setString(7, program);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("INSERT INTO takes VALUES (?,?)");
            preparedStatement.setString(1, stuReg);
            preparedStatement.setString(2, courseCode);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Tuple Inserted Successfully!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void updateStudent(String stuReg, int totalCredit, String dept_name, String sectionID, int semester,
                              String program, String courseCode) {
        try {

            if (stuReg.equals("")) {
                JOptionPane.showMessageDialog(null, "Registration Field Must Not be Left Empty" +
                        "", "Error", JOptionPane.ERROR_MESSAGE);
            }
            //Only Update total credits
            else if (!stuReg.equals("") && totalCredit != 0 && dept_name.equals("") && sectionID.equals("") &&
                    semester == 0 && program.equals("") && courseCode.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE student set tot_cred = ? where stu_reg = ?");
                preparedStatement.setInt(1, totalCredit);
                preparedStatement.setString(2, stuReg);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Total Credits Updated!");
            }

            //Only Update department name
            else if (!stuReg.equals("") && totalCredit == 0 && !dept_name.equals("") && sectionID.equals("") &&
                    semester == 0 && program.equals("") && courseCode.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE student set department_dept_name = ? where stu_reg = ?");
                preparedStatement.setString(1, dept_name);
                preparedStatement.setString(2, stuReg);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Department Name Updated!");
            }
            //Only Update Section ID
            else if (!stuReg.equals("") && totalCredit == 0 && dept_name.equals("") && !sectionID.equals("") &&
                    semester == 0 && program.equals("") && courseCode.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE student set section_sec_id = ? where stu_reg = ?");
                preparedStatement.setString(1, sectionID);
                preparedStatement.setString(2, stuReg);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Section Updated!");
            }

            //Only Update Semester
            else if (!stuReg.equals("") && totalCredit == 0 && dept_name.equals("") && sectionID.equals("") &&
                    semester != 0 && program.equals("") && courseCode.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE student set section_semester = ? where stu_reg = ?");
                preparedStatement.setInt(1, semester);
                preparedStatement.setString(2, stuReg);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Semester Updated!");
            }
            //Only Update Program
            else if (!stuReg.equals("") && totalCredit == 0 && dept_name.equals("") && sectionID.equals("") &&
                    semester == 0 && !program.equals("") && courseCode.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE student set section_program = ? where stu_reg = ?");
                preparedStatement.setString(1, program);
                preparedStatement.setString(2, stuReg);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Program Updated!");
            }
            //Only Update Course
            else if (!stuReg.equals("") && totalCredit == 0 && dept_name.equals("") && sectionID.equals("") &&
                    semester == 0 && program.equals("") && !courseCode.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE takes set course_course_id = ? where stu_reg = ?");
                preparedStatement.setString(1, courseCode);
                preparedStatement.setString(2, stuReg);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Course Updated!");
            }

            //Only Update Section And Semester
            else if (!stuReg.equals("") && totalCredit == 0 && dept_name.equals("") && !sectionID.equals("") &&
                    semester != 0 && program.equals("") && courseCode.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE student set section_sec_id= ?,section_semester=? where stu_reg = ?");
                preparedStatement.setString(1, sectionID);
                preparedStatement.setInt(2, semester);
                preparedStatement.setString(3, stuReg);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Section and Semester Updated!");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void deleteStudent(String stuReg) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM student where stu_reg = ?");
            preparedStatement.setString(1, stuReg);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tuple Deleted Successfully!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public void selectAllStudents(JTable table) {
        try {
            preparedStatement = connection.prepareStatement("SELECT student.stu_reg,name,tot_cred,department_dept_name,\n" +
                    "takes.course_course_id, student.section_sec_id,\n" +
                    "student.section_semester,student.section_program\n" +
                    "FROM student left outer join takes on student.stu_reg = takes.stu_reg;\n");
            resultSet = preparedStatement.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(resultSet));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }


    public void insertInstructor(int ID, String name, int salary, String dept_name, String sectionID, int semester,
                                 String program, String courseCode) {

        try {

            preparedStatement = connection.prepareStatement("INSERT INTO instructor VALUES (?,?,?,?)");
            preparedStatement.setInt(1, ID);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, salary);
            preparedStatement.setString(4, dept_name);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("INSERT INTO instructor_teaches_section VALUES(?,?,?,?)");
            preparedStatement.setInt(1, ID);
            preparedStatement.setString(2, sectionID);
            preparedStatement.setInt(3, semester);
            preparedStatement.setString(4, program);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("INSERT INTO instructor_teaches_course VALUES(?,?)");
            preparedStatement.setInt(1, ID);
            preparedStatement.setString(2, courseCode);
            preparedStatement.executeUpdate();


            JOptionPane.showMessageDialog(null, "Tuple Insert Successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public void insertInstructorTeachesSection(int ID, String sectionID, int semester, String program) {
        try {



            preparedStatement = connection.prepareStatement("INSERT INTO instructor_teaches_section VALUES(?,?,?,?)");
            preparedStatement.setInt(1, ID);
            preparedStatement.setString(2, sectionID);
            preparedStatement.setInt(3, semester);
            preparedStatement.setString(4, program);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Tuple Insert Successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }
    public void insertInstructorTeachesCourse(int ID, String courseCode) {
        try {

            preparedStatement = connection.prepareStatement("INSERT INTO instructor_teaches_course VALUES(?,?)");
            preparedStatement.setInt(1, ID);
            preparedStatement.setString(2, courseCode);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Tuple Insert Successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public void updateInstructor(int ID, String name, int salary, String dept_name, String sectionID, int semester,
                                 String program, String courseCode) {
        try {

            //Update name of instructor
            if (ID != 0 && !name.equals("") && salary == 0 && dept_name.equals("") && sectionID.equals("") &&
                    semester == 0 && program.equals("") && courseCode.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE instructor SET name = ? where " +
                        "instructor_id = ?");
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, ID);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Name Updated Successfully!");
            }

            //Update salary
            else if (ID != 0 && name.equals("") && salary != 0 && dept_name.equals("") && sectionID.equals("") &&
                    semester == 0 && program.equals("") && courseCode.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE instructor SET salary = ? where " +
                        "instructor_id = ?");
                preparedStatement.setInt(1, salary);
                preparedStatement.setInt(2, ID);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Salary Updated Successfully!");
            }

            //Update department
            else if (ID != 0 && name.equals("") && salary == 0 && !dept_name.equals("") && sectionID.equals("") &&
                    semester == 0 && program.equals("") && courseCode.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE instructor SET department_dept_name = ? where " +
                        "instructor_id = ?");
                preparedStatement.setString(1, dept_name);
                preparedStatement.setInt(2, ID);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Department Updated Successfully!");
            }

            //Update Section
            else if (ID != 0 && name.equals("") && salary == 0 && dept_name.equals("") && !sectionID.equals("") &&
                    semester != 0 && !program.equals("") && courseCode.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE instructor_teaches_section" +
                        " SET section_sec_id = ? where instructor_instructor_id = ? and section_semester = ?" +
                        "and section_program = ?");
                preparedStatement.setString(1, sectionID);
                preparedStatement.setInt(2, ID);
                preparedStatement.setInt(3, semester);
                preparedStatement.setString(4, program);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Section Updated Successfully!");
            }
            //Update course code
            else if (ID != 0 && name.equals("") && salary == 0 && dept_name.equals("") && sectionID.equals("") &&
                    semester == 0 && program.equals("") && !courseCode.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE instructor_teaches_course" +
                        " SET course_course_id = ? where instructor_instructor_id = ? ");
                preparedStatement.setString(1, courseCode);
                preparedStatement.setInt(2, ID);
                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Course Code Updated Successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Please ensure Instructor ID or Section ID " +
                                "or Semester or Program are not empty!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }


    }


    public void deleteInstructor(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM instructor WHERE instructor_id=?;");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tuple Deleted Successfully!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    public void deleteInstructorTeachesCourse(int id, String courseCode) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM instructor_teaches_course WHERE instructor_instructor_id=? and course_course_id = ?;");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2,courseCode );
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tuple Deleted Successfully!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    public void deleteInstructorTeachesSection(int id, String  section, int semester, String program) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM instructor_teaches_section WHERE " +
                    "instructor_instructor_id=? and section_sec_id = ? and section_semester = ? and section_program = ?;");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2,section );
            preparedStatement.setInt(3,semester );
            preparedStatement.setString(4,program );
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tuple Deleted Successfully!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void selectAllInstructor(JTable table) {
        try {
            preparedStatement = connection.prepareStatement("SELECT instructor_id,name,salary,department_dept_name," +
                    "instructor_teaches_course.course_course_id,instructor_teaches_section.section_sec_id," +
                    "instructor_teaches_section.section_semester,instructor_teaches_section.section_program FROM " +
                    "instructor left outer join instructor_teaches_course on instructor.instructor_id = " +
                    "instructor_teaches_course.instructor_instructor_id left outer JOIN instructor_teaches_section on " +
                    "instructor.instructor_id = instructor_teaches_section.instructor_instructor_id;");
            resultSet = preparedStatement.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(resultSet));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }


    public void insertClasses(int roomNumber, int capacity, String dept_name) {
        try {

            preparedStatement = connection.prepareStatement("INSERT INTO classes VALUES (?,?,?)");
            preparedStatement.setInt(1, roomNumber);
            preparedStatement.setInt(2, capacity);
            preparedStatement.setString(3, dept_name);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tuple Inserted Successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public void updateClasses(int roomNumber, int capacity, String dept_name) {
        try {
            if (roomNumber != 0 && capacity != 0 && !dept_name.equals("")) {
                preparedStatement = connection.prepareStatement("Update classes set capacity = ?" +
                        " where room_number = ? and department_dept_name = ?");
                preparedStatement.setInt(1, capacity);
                preparedStatement.setInt(2, roomNumber);
                preparedStatement.setString(3, dept_name);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Capacity Updated Successfully!");
            } else if (roomNumber == 0 || dept_name.equals("")) {
                JOptionPane.showMessageDialog(null, "Room Number and Department Name Must " +
                        "not Be left Empty!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public void deleteClasses(int roomNumber, String dept_name) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM classes WHERE room_number=? and department_dept_name=?;");
            preparedStatement.setInt(1, roomNumber);
            preparedStatement.setString(2, dept_name);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tuple Deleted Successfully!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void selectAllClasses(JTable table) {
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM classes;");
            resultSet = preparedStatement.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(resultSet));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void insertSection(String secID, int semester, String program, String dept_name, int timeSlotID, String day, String courseCode) {
        try {
            if (dept_name.equals("")) {
                preparedStatement = connection.prepareStatement("INSERT INTO section_has_timeslot VALUES(?,?,?,?,?,?)");
                preparedStatement.setString(1, secID);
                preparedStatement.setInt(2, semester);
                preparedStatement.setString(3, program);
                preparedStatement.setInt(4, timeSlotID);
                preparedStatement.setString(5, day);
                preparedStatement.setString(6, courseCode);
                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Time Slot Added Successfully!");
            } else {

                preparedStatement = connection.prepareStatement("INSERT INTO classroom.section VALUES(?,?,?,?); ");
                preparedStatement.setString(1, secID);
                preparedStatement.setInt(2, semester);
                preparedStatement.setString(3, program);
                preparedStatement.setString(4, dept_name);
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement("INSERT INTO section_has_timeslot VALUES(?,?,?,?,?,?)");
                preparedStatement.setString(1, secID);
                preparedStatement.setInt(2, semester);
                preparedStatement.setString(3, program);
                preparedStatement.setInt(4, timeSlotID);
                preparedStatement.setString(5, day);
                preparedStatement.setString(6, courseCode);
                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Tuple Inserted Successfully!");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public void updateSection(String secID, int semester, String program, String dept_name, int timeSlotID, String day, String courseCode) {
        try {

            //Update Semester Only
            if (!secID.equals("") && semester != 0 && !program.equals("") && dept_name.equals("") && timeSlotID == 0
                    && day.equals("") && courseCode.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE classroom.section set semester = ?" +
                        " where section.sec_id = ? and section.program = ? ");
                preparedStatement.setInt(1, semester);
                preparedStatement.setString(2, secID);
                preparedStatement.setString(3, program);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Semester Updated Successfully!");
            }
            //Update program only
            else if (!secID.equals("") && semester != 0 && !program.equals("") && !dept_name.equals("") && timeSlotID == 0
                    && day.equals("") && courseCode.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE classroom.section set program = ?" +
                        " where section.sec_id = ? and section.semester = ? and department_dept_name = ? ");
                preparedStatement.setString(1, program);
                preparedStatement.setString(2, secID);
                preparedStatement.setInt(3, semester);
                preparedStatement.setString(4, dept_name);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Program Updated Successfully!");
            }

            //Update time slot id only
            else if (!secID.equals("") && semester != 0 && !program.equals("") && dept_name.equals("") && timeSlotID != 0
                    && day.equals("") && courseCode.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE section_has_timeslot set time_slot_time_slot_id = ?" +
                        " where section_sec_id = ? and section_semester = ? and section_program = ? ");
                preparedStatement.setInt(1, timeSlotID);
                preparedStatement.setString(2, secID);
                preparedStatement.setInt(3, semester);
                preparedStatement.setString(4, program);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "TimeSlot ID Updated Successfully!");
            }

//            //Update course code
//            else if (!secID.equals("") && semester != 0 && !program.equals("") && !dept_name.equals("") && timeSlotID != 0
//                    && !day.equals("") && !courseCode.equals("")) {
//                preparedStatement = connection.prepareStatement("UPDATE section_has_timeslot set course_course_id = ?" +
//                        " where section_sec_id = ? and section_semester = ? and section_program = ? and time_slot_time_slot_id = ? and time_slot_day = ? ");
//                preparedStatement.setString(1, courseCode);
//                preparedStatement.setString(2, secID);
//                preparedStatement.setInt(3, semester);
//                preparedStatement.setString(4, program);
//                preparedStatement.setInt(5, timeSlotID);
//                preparedStatement.setString(6, day);
//                preparedStatement.executeUpdate();
//                JOptionPane.showMessageDialog(null, "Course Code Updated Successfully!");
//            }

            //Update time slot day only
            else if (!secID.equals("") && semester != 0 && !program.equals("") && dept_name.equals("") && timeSlotID == 0
                    && !day.equals("") && courseCode.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE section_has_timeslot set time_slot_day = ?" +
                        " where section_sec_id = ? and section_semester = ? and section_program = ? ");
                preparedStatement.setString(1, day);
                preparedStatement.setString(2, secID);
                preparedStatement.setInt(3, semester);
                preparedStatement.setString(4, program);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "TimeSlot Day Updated Successfully!");
            }
            //Update TimeSlot ID and DAY
            else if (!secID.equals("") && semester != 0 && !program.equals("") && dept_name.equals("") && timeSlotID != 0
                    && !day.equals("") && courseCode.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE section_has_timeslot set " +
                        "time_slot_time_slot_id = ?,time_slot_day=?" +
                        " where section_sec_id = ? and section_semester = ? and section_program = ? ");
                preparedStatement.setInt(1, timeSlotID);
                preparedStatement.setString(2, day);
                preparedStatement.setString(3, secID);
                preparedStatement.setInt(4, semester);
                preparedStatement.setString(5, program);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "TimeSlot ID and Day Updated Successfully!");
            }
            //Update TimeSlot ID, DAY and Course Code
            else if (!secID.equals("") && semester != 0 && !program.equals("") && dept_name.equals("") && timeSlotID != 0
                    && !day.equals("") && !courseCode.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE section_has_timeslot set " +
                        "time_slot_time_slot_id = ?,time_slot_day=?,course_course_id = ?" +
                        " where section_sec_id = ? and section_semester = ? and section_program = ? ");
                preparedStatement.setInt(1, timeSlotID);
                preparedStatement.setString(2, day);
                preparedStatement.setString(3, courseCode);
                preparedStatement.setString(4, secID);
                preparedStatement.setInt(5, semester);
                preparedStatement.setString(6, program);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "TimeSlot ID, Day and Course Code Updated Successfully!");
            } else if (secID.equals("") || semester == 0 || program.equals("")) {
                if (secID.equals("") && semester == 0 && program.equals("")) {
                    JOptionPane.showMessageDialog(null, "Section ID, Semester and Program" +
                            "can not be Empty!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (secID.equals("")) {
                    JOptionPane.showMessageDialog(null, "Section ID can " +
                            "not Be Empty!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (semester == 0) {
                    JOptionPane.showMessageDialog(null, "Semester can " +
                            "not Be Empty!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (program.equals("")) {
                    JOptionPane.showMessageDialog(null, "Program can " +
                            "not Be Empty!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void deleteSection(String secID, int semester, String program) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM classroom.section WHERE sec_id=? and " +
                    "semester = ? and program = ?;");
            preparedStatement.setString(1, secID);
            preparedStatement.setInt(2, semester);
            preparedStatement.setString(3, program);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tuple Deleted Successfully!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void deleteSectionTimeSlot(String secID, int semester, String program, String courseCode) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM classroom.section_has_timeslot WHERE section_sec_id=? and " +
                    "section_semester = ? and section_program = ? and course_course_id = ? ;");
            preparedStatement.setString(1, secID);
            preparedStatement.setInt(2, semester);
            preparedStatement.setString(3, program);
            preparedStatement.setString(4, courseCode);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Time slot Deleted Successfully!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void selectAllSection(JTable table) {
        try {
            preparedStatement = connection.prepareStatement("SELECT section.sec_id,section.semester,section.program,\n" +
                    "section_has_timeslot.course_course_id,time_slot.day,time_slot.start, time_slot.end,section.department_dept_name\n" +
                    "FROM section LEFT OUTER JOIN section_has_timeslot on section.sec_id = section_has_timeslot.section_sec_id and" +
                    " section.semester = section_has_timeslot.section_semester and section.program = " +
                    "section_has_timeslot.section_program LEFT OUTER JOIN time_slot on section_has_timeslot.time_slot_time_slot_id" +
                    " = time_slot.time_slot_id and section_has_timeslot.time_slot_day = time_slot.day;");
            resultSet = preparedStatement.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(resultSet));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }


    public void insertDepartment(String dept_name, String building, int budget) {
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO department VALUES (?,?,?)");
            preparedStatement.setString(1, dept_name);
            preparedStatement.setString(2, building);
            preparedStatement.setInt(3, budget);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tuple Inserted Successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public void updateDepartment(String dept_name, String building, int budget) {
        try {
            //Update Building
            if (!dept_name.equals("") && !building.equals("") && budget == 0) {
                preparedStatement = connection.prepareStatement("Update department set building = ? where dept_name = ?");
                preparedStatement.setString(1, building);
                preparedStatement.setString(2, dept_name);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Building Updated Successfully!");
            }
            //Update Budget
            else if (!dept_name.equals("") && building.equals("") && budget != 0) {
                preparedStatement = connection.prepareStatement("Update department set budget = ? where dept_name = ?");
                preparedStatement.setInt(1, budget);
                preparedStatement.setString(1, dept_name);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Budget Updated Successfully!");

            }
            //Update Building and Budget
            else if (!dept_name.equals("") && !building.equals("") && budget != 0) {
                preparedStatement = connection.prepareStatement("Update department set building = ?,budget=? where dept_name = ?");
                preparedStatement.setString(1, building);
                preparedStatement.setInt(2, budget);
                preparedStatement.setString(3, dept_name);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Budget and Building Updated Successfully!");

            } else {
                JOptionPane.showMessageDialog(null, "Room Number and Department Name Must " +
                        "not Be left Empty!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public void deleteDepartment(String dept_name) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM department WHERE dept_name=?;");
            preparedStatement.setString(1, dept_name);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tuple Deleted Successfully!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void selectAllDepartment(JTable table) {
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM department;");
            resultSet = preparedStatement.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(resultSet));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }


    public void insertTimeSlot(int id, String day, String sTime, String eTime) {
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO time_slot VALUES (?,?,?,?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, day);
            preparedStatement.setString(3, sTime);
            preparedStatement.setString(4, eTime);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tuple Inserted Successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public void updateTimeSlot(int id, String day, String val1, String val2) {
        try {

            //Update start time
            if (!val1.equals("") && val2.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE time_slot SET start = ? where time_slot_id=? and time_slot.day = ?");
                preparedStatement.setString(1, val1);
                preparedStatement.setInt(2, id);
                preparedStatement.setString(3, day);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Value Updated!");
            }
            //Update end time
            else if (val1.equals("") && !val2.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE time_slot SET time_slot.end = ? where time_slot_id=? and time_slot.day = ?");
                preparedStatement.setString(1, val2);
                preparedStatement.setInt(2, id);
                preparedStatement.setString(3, day);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Value Updated!");

            }
            //Update start and end time
            else if (!val1.equals("") && !val2.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE time_slot SET start = ?,time_slot.end = ? where time_slot_id=? and time_slot.day = ?");
                preparedStatement.setString(1, val1);
                preparedStatement.setString(2, val2);
                preparedStatement.setInt(3, id);
                preparedStatement.setString(4, day);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Values Updated!");
            } else {
                JOptionPane.showMessageDialog(null, "Start Time, End Time, or Both," +
                        " Shall Not Be Left Empty!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void deleteTimeSlot(int id, String day) {
        try {

            preparedStatement = connection.prepareStatement("DELETE FROM time_slot WHERE time_slot_id=? and time_slot.day=?;");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, day);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Value Deleted Successfully!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void selectAllTimeSlots(JTable table) {
        try {

            preparedStatement = connection.prepareStatement("SELECT * FROM time_slot;");
            resultSet = preparedStatement.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(resultSet));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }


    public void insertCourses(String courseCode, String title, int creditHour, String dept_name) {
        try {
            preparedStatement = connection.prepareStatement("INSERT course VALUES (?,?,?,?)");
            preparedStatement.setString(1, courseCode);
            preparedStatement.setString(2, title);
            preparedStatement.setInt(3, creditHour);
            preparedStatement.setString(4, dept_name);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tuple Inserted Successfully!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void updateCourses(String courseCode, String title, int creditHour, String dept_name) {
        try {
            //Update course title
            if (!courseCode.equals("") && !title.equals("") && creditHour == 0 && dept_name.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE course set title = ? where course_id=?");
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, courseCode);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Title Updated Successfully!");
            }
            //Update credits
            else if (!courseCode.equals("") && title.equals("") && creditHour != 0 && dept_name.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE course set credits = ? where course_id=?");
                preparedStatement.setInt(1, creditHour);
                preparedStatement.setString(2, courseCode);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Credit Hour Updated Successfully!");
            }
            //Update Department name
            else if (!courseCode.equals("") && title.equals("") && creditHour == 0 && !dept_name.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE course set department_dept_name = ? where course_id=?");
                preparedStatement.setString(1, dept_name);
                preparedStatement.setString(2, courseCode);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Department Name Updated Successfully!");
            }
            //Update Course Code
            else if (!courseCode.equals("") && !title.equals("") && creditHour == 0 && !dept_name.equals("")) {
                preparedStatement = connection.prepareStatement("UPDATE course set course_id = ? where title = ? and department_dept_name = ?");
                preparedStatement.setString(1, courseCode);
                preparedStatement.setString(2, title);
                preparedStatement.setString(3, dept_name);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Course Code Updated Successfully!");
            }
            //Display Error Messages
            else if (!courseCode.equals("") && title.equals("") && creditHour == 0 && dept_name.equals("")) {
                JOptionPane.showMessageDialog(null, "Please Insert A Value to be Updated",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Course Code Can Not Be Empty!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }


        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }


    }


    public void deleteCourses(String courseCode) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM course WHERE course_id=?;");
            preparedStatement.setString(1, courseCode);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Tuple Deleted Successfully!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void selectAllCourses(JTable table) {
        try {

            preparedStatement = connection.prepareStatement("SELECT * FROM course;");
            resultSet = preparedStatement.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(resultSet));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }


//    public void displayTimeSlots(String tableName, String whereColumn, String value, JTable table) {
//        try {
//
//            preparedStatement = connection.prepareStatement("SELECT student.stu_reg,student.name," +
//                    "student.section_semester,student.section_sec_id, course.course_id,instructor.name,\n" +
//                    "time_slot.day,time_slot.start,time_slot.end FROM student JOIN takes ON " +
//                    "student.stu_reg = takes.stu_reg JOIN course on takes.course_course_id =" +
//                    " course.course_id JOIN instructor_teaches_course ON course.course_id = " +
//                    "instructor_teaches_course.course_course_id  JOIN instructor ON " +
//                    "instructor_teaches_course.instructor_instructor_id = instructor.instructor_id\n" +
//                    "JOIN section_has_timeslot ON student.section_sec_id = section_has_timeslot.section_sec_id " +
//                    "and student.section_semester = section_has_timeslot.section_semester and student.section_program" +
//                    " = section_has_timeslot.section_program JOIN time_slot ON " +
//                    "section_has_timeslot.time_slot_time_slot_id = time_slot.time_slot_id and " +
//                    "section_has_timeslot.time_slot_day = time_slot.day\n" +
//                    "and " + tableName + "." + whereColumn + " = ?");
//            if (isInteger(value)) {
//                preparedStatement.setInt(1, Integer.parseInt(value));
//            } else {
//                preparedStatement.setString(1, value);
//            }
//            resultSet = preparedStatement.executeQuery();
//            table.setModel(DbUtils.resultSetToTableModel(resultSet));
//
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage());
//        }
//
//    }

    public void displayTimeSlots(String tableName, String whereColumn, String value, JTable table) {
        try {

            preparedStatement = connection.prepareStatement("SELECT student.stu_reg,student.name," +
                    "student.section_sec_id,student.section_semester,takes.course_course_id,instructor.name," +
                    "time_slot.day,time_slot.start,time_slot.end,student.department_dept_name" +
                    " FROM student JOIN takes ON student.stu_reg = " +
                    "takes.stu_reg JOIN section_has_timeslot ON\n" +
                    "student.section_sec_id = section_has_timeslot.section_sec_id and student.section_semester " +
                    "=section_has_timeslot.section_semester and student.section_program = " +
                    "section_has_timeslot.section_program and takes.course_course_id = " +
                    "section_has_timeslot.course_course_id JOIN time_slot ON section_has_timeslot.time_slot_time_slot_id" +
                    " = time_slot.time_slot_id and section_has_timeslot.time_slot_day = time_slot.day JOIN" +
                    " instructor_teaches_course ON takes.course_course_id = instructor_teaches_course.course_course_id " +
                    "JOIN instructor ON instructor_teaches_course.instructor_instructor_id = instructor.instructor_id" +
                    " and " + tableName + "." + whereColumn + " = ?");
            if (isInteger(value)) {
                preparedStatement.setInt(1, Integer.parseInt(value));
            } else {
                preparedStatement.setString(1, value);
            }
            resultSet = preparedStatement.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(resultSet));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }


    public void displayTimeSlotsBasedOnMultipleValues(String tableName1, String whereColumn1, String value1,
                                                      String tableName2, String whereColumn2, String value2, JTable table) {
        try {

            preparedStatement = connection.prepareStatement("SELECT student.stu_reg,student.name," +
                    "student.section_sec_id,student.section_semester,takes.course_course_id,instructor.name," +
                    "time_slot.day,time_slot.start,time_slot.end,student.department_dept_name" +
                    " FROM student JOIN takes ON student.stu_reg = " +
                    "takes.stu_reg JOIN section_has_timeslot ON\n" +
                    "student.section_sec_id = section_has_timeslot.section_sec_id and student.section_semester " +
                    "=section_has_timeslot.section_semester and student.section_program = " +
                    "section_has_timeslot.section_program and takes.course_course_id = " +
                    "section_has_timeslot.course_course_id JOIN time_slot ON section_has_timeslot.time_slot_time_slot_id" +
                    " = time_slot.time_slot_id and section_has_timeslot.time_slot_day = time_slot.day JOIN" +
                    " instructor_teaches_course ON takes.course_course_id = instructor_teaches_course.course_course_id " +
                    "JOIN instructor ON instructor_teaches_course.instructor_instructor_id = instructor.instructor_id" +
                    " and " + tableName1 + "." + whereColumn1 + " = ? and " + tableName2 + "." + whereColumn2 + " = ?");
            if (isInteger(value1)) {
                preparedStatement.setInt(1, Integer.parseInt(value1));
                preparedStatement.setString(2, value2);
            } else {
                preparedStatement.setString(1, value1);
                preparedStatement.setString(2, value2);
            }
            resultSet = preparedStatement.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(resultSet));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public void displayTimeSlotsForInstructor(String tableName, String whereColumn, String value, JTable table) {
        try {

            preparedStatement = connection.prepareStatement("SELECT instructor.instructor_id,instructor.name,instructor_teaches_course.course_course_id,course.title,instructor_teaches_section.section_sec_id,\n" +
                    "instructor_teaches_section.section_semester,instructor_teaches_section.section_program,\n" +
                    "time_slot.day,time_slot.start,time_slot.end FROM instructor JOIN instructor_teaches_course ON instructor.instructor_id = instructor_teaches_course.instructor_instructor_id JOIN course ON instructor_teaches_course.course_course_id = course.course_id JOIN instructor_teaches_section ON instructor.instructor_id = instructor_teaches_section.instructor_instructor_id JOIN section_has_timeslot ON instructor_teaches_section.section_sec_id = section_has_timeslot.section_sec_id and instructor_teaches_section.section_semester = section_has_timeslot.section_semester and instructor_teaches_section.section_program = section_has_timeslot.section_program and instructor_teaches_course.course_course_id = section_has_timeslot.course_course_id JOIN time_slot ON section_has_timeslot.time_slot_time_slot_id = time_slot.time_slot_id and section_has_timeslot.time_slot_day = time_slot.day" +
                    " and " + tableName + "." + whereColumn + " = ?" + " ORDER BY instructor.instructor_id; ");
            if (isInteger(value)) {
                preparedStatement.setInt(1, Integer.parseInt(value));
            } else {
                preparedStatement.setString(1, value);
            }
            resultSet = preparedStatement.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(resultSet));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    public void displayTimeSlotsForInstructorUsingMultipleValues(String sectionID, int semester, String program, JTable table) {
        try {
            //Display Entire Relation
            if (sectionID.equals("") && semester == 0 && program.equals("")) {

                preparedStatement = connection.prepareStatement("SELECT instructor.instructor_id,instructor.name," +
                        "instructor_teaches_course.course_course_id,course.title,instructor_teaches_section.section_sec_id,\n" +
                        "instructor_teaches_section.section_semester,instructor_teaches_section.section_program," +
                        "total_students,\n" +
                        "time_slot.day,time_slot.start,time_slot.end FROM instructor JOIN instructor_teaches_course " +
                        "ON instructor.instructor_id = instructor_teaches_course.instructor_instructor_id JOIN course " +
                        "ON instructor_teaches_course.course_course_id = course.course_id JOIN" +
                        " instructor_teaches_section ON instructor.instructor_id = " +
                        "instructor_teaches_section.instructor_instructor_id JOIN section_has_timeslot" +
                        " ON instructor_teaches_section.section_sec_id = section_has_timeslot.section_sec_id " +
                        "and instructor_teaches_section.section_semester = section_has_timeslot.section_semester " +
                        "and instructor_teaches_section.section_program = section_has_timeslot.section_program and " +
                        "instructor_teaches_course.course_course_id = section_has_timeslot.course_course_id JOIN" +
                        " time_slot ON section_has_timeslot.time_slot_time_slot_id = time_slot.time_slot_id and " +
                        "section_has_timeslot.time_slot_day = time_slot.day JOIN (select count(stu_reg) as" +
                        " total_students,section_sec_id,section_semester,section_program FROM student " +
                        "GROUP BY (concat(section_sec_id,section_semester,section_program))) C ON " +
                        "instructor_teaches_section.section_sec_id = C.section_sec_id and " +
                        "instructor_teaches_section.section_semester = C.section_semester and " +
                        "instructor_teaches_section.section_program = C.section_program\n" +
                        " ORDER BY instructor.instructor_id");
                resultSet = preparedStatement.executeQuery();
                table.setModel(DbUtils.resultSetToTableModel(resultSet));
            }

            //Display According to Section ID
            else if (!sectionID.equals("") && semester == 0 && program.equals("")) {

                preparedStatement = connection.prepareStatement("SELECT instructor.instructor_id,instructor.name,instructor_teaches_course.course_course_id,course.title,instructor_teaches_section.section_sec_id,\n" +
                        "instructor_teaches_section.section_semester,instructor_teaches_section.section_program,\n" +
                        "time_slot.day,time_slot.start,time_slot.end FROM instructor JOIN instructor_teaches_course ON instructor.instructor_id = instructor_teaches_course.instructor_instructor_id JOIN course ON instructor_teaches_course.course_course_id = course.course_id JOIN instructor_teaches_section ON instructor.instructor_id = instructor_teaches_section.instructor_instructor_id JOIN section_has_timeslot ON instructor_teaches_section.section_sec_id = section_has_timeslot.section_sec_id and instructor_teaches_section.section_semester = section_has_timeslot.section_semester and instructor_teaches_section.section_program = section_has_timeslot.section_program and instructor_teaches_course.course_course_id = section_has_timeslot.course_course_id JOIN time_slot ON section_has_timeslot.time_slot_time_slot_id = time_slot.time_slot_id and section_has_timeslot.time_slot_day = time_slot.day" +
                        " and instructor_teaches_section.section_sec_id = ?" + " ORDER BY instructor.instructor_id");
                preparedStatement.setString(1, sectionID);
                resultSet = preparedStatement.executeQuery();
                table.setModel(DbUtils.resultSetToTableModel(resultSet));
            }

            //Display According to Semester
            else if (sectionID.equals("") && semester != 0 && program.equals("")) {

                preparedStatement = connection.prepareStatement("SELECT instructor.instructor_id,instructor.name,instructor_teaches_course.course_course_id,course.title,instructor_teaches_section.section_sec_id,\n" +
                        "instructor_teaches_section.section_semester,instructor_teaches_section.section_program,\n" +
                        "time_slot.day,time_slot.start,time_slot.end FROM instructor JOIN instructor_teaches_course ON instructor.instructor_id = instructor_teaches_course.instructor_instructor_id JOIN course ON instructor_teaches_course.course_course_id = course.course_id JOIN instructor_teaches_section ON instructor.instructor_id = instructor_teaches_section.instructor_instructor_id JOIN section_has_timeslot ON instructor_teaches_section.section_sec_id = section_has_timeslot.section_sec_id and instructor_teaches_section.section_semester = section_has_timeslot.section_semester and instructor_teaches_section.section_program = section_has_timeslot.section_program and instructor_teaches_course.course_course_id = section_has_timeslot.course_course_id JOIN time_slot ON section_has_timeslot.time_slot_time_slot_id = time_slot.time_slot_id and section_has_timeslot.time_slot_day = time_slot.day" +
                        " and instructor_teaches_section.section_semester = ?  ORDER BY instructor.instructor_id");
                preparedStatement.setInt(1, semester);
                resultSet = preparedStatement.executeQuery();
                table.setModel(DbUtils.resultSetToTableModel(resultSet));
            }

            //Display According to Program
            else if (sectionID.equals("") && semester == 0 && !program.equals("")) {

                preparedStatement = connection.prepareStatement("SELECT instructor.instructor_id,instructor.name,instructor_teaches_course.course_course_id,course.title,instructor_teaches_section.section_sec_id,\n" +
                        "instructor_teaches_section.section_semester,instructor_teaches_section.section_program,\n" +
                        "time_slot.day,time_slot.start,time_slot.end FROM instructor JOIN instructor_teaches_course ON instructor.instructor_id = instructor_teaches_course.instructor_instructor_id JOIN course ON instructor_teaches_course.course_course_id = course.course_id JOIN instructor_teaches_section ON instructor.instructor_id = instructor_teaches_section.instructor_instructor_id JOIN section_has_timeslot ON instructor_teaches_section.section_sec_id = section_has_timeslot.section_sec_id and instructor_teaches_section.section_semester = section_has_timeslot.section_semester and instructor_teaches_section.section_program = section_has_timeslot.section_program and instructor_teaches_course.course_course_id = section_has_timeslot.course_course_id JOIN time_slot ON section_has_timeslot.time_slot_time_slot_id = time_slot.time_slot_id and section_has_timeslot.time_slot_day = time_slot.day" +
                        " and instructor_teaches_section.section_program = ?  ORDER BY instructor.instructor_id");
                preparedStatement.setString(1, program);
                resultSet = preparedStatement.executeQuery();
                table.setModel(DbUtils.resultSetToTableModel(resultSet));
            }

            //Display According to Section and Semester
            else if (!sectionID.equals("") && semester != 0 && program.equals("")) {

                preparedStatement = connection.prepareStatement("SELECT instructor.instructor_id,instructor.name,instructor_teaches_course.course_course_id,course.title,instructor_teaches_section.section_sec_id,\n" +
                        "instructor_teaches_section.section_semester,instructor_teaches_section.section_program,\n" +
                        "time_slot.day,time_slot.start,time_slot.end FROM instructor JOIN instructor_teaches_course ON instructor.instructor_id = instructor_teaches_course.instructor_instructor_id JOIN course ON instructor_teaches_course.course_course_id = course.course_id JOIN instructor_teaches_section ON instructor.instructor_id = instructor_teaches_section.instructor_instructor_id JOIN section_has_timeslot ON instructor_teaches_section.section_sec_id = section_has_timeslot.section_sec_id and instructor_teaches_section.section_semester = section_has_timeslot.section_semester and instructor_teaches_section.section_program = section_has_timeslot.section_program and instructor_teaches_course.course_course_id = section_has_timeslot.course_course_id JOIN time_slot ON section_has_timeslot.time_slot_time_slot_id = time_slot.time_slot_id and section_has_timeslot.time_slot_day = time_slot.day" +
                        " and instructor_teaches_section.section_sec_id = ? and instructor_teaches_section.section_semester = ? ORDER BY instructor.instructor_id");
                preparedStatement.setString(1, sectionID);
                preparedStatement.setInt(2, semester);
                resultSet = preparedStatement.executeQuery();
                table.setModel(DbUtils.resultSetToTableModel(resultSet));
            }

            //Display Semester and Program
            else if (sectionID.equals("") && semester != 0 && !program.equals("")) {

                preparedStatement = connection.prepareStatement("SELECT instructor.instructor_id,instructor.name,instructor_teaches_course.course_course_id,course.title,instructor_teaches_section.section_sec_id,\n" +
                        "instructor_teaches_section.section_semester,instructor_teaches_section.section_program,\n" +
                        "time_slot.day,time_slot.start,time_slot.end FROM instructor JOIN instructor_teaches_course ON instructor.instructor_id = instructor_teaches_course.instructor_instructor_id JOIN course ON instructor_teaches_course.course_course_id = course.course_id JOIN instructor_teaches_section ON instructor.instructor_id = instructor_teaches_section.instructor_instructor_id JOIN section_has_timeslot ON instructor_teaches_section.section_sec_id = section_has_timeslot.section_sec_id and instructor_teaches_section.section_semester = section_has_timeslot.section_semester and instructor_teaches_section.section_program = section_has_timeslot.section_program and instructor_teaches_course.course_course_id = section_has_timeslot.course_course_id JOIN time_slot ON section_has_timeslot.time_slot_time_slot_id = time_slot.time_slot_id and section_has_timeslot.time_slot_day = time_slot.day" +
                        " and instructor_teaches_section.section_semester = ? and instructor_teaches_section.section_program = ?" +
                        "  ORDER BY instructor.instructor_id");
                preparedStatement.setInt(1, semester);
                preparedStatement.setString(2, program);
                resultSet = preparedStatement.executeQuery();
                table.setModel(DbUtils.resultSetToTableModel(resultSet));
            }


            //Display Semester, Section and Program
            else if (!sectionID.equals("") && semester != 0 && !program.equals("")) {

                preparedStatement = connection.prepareStatement("SELECT instructor.instructor_id,instructor.name,instructor_teaches_course.course_course_id,course.title,instructor_teaches_section.section_sec_id,\n" +
                        "instructor_teaches_section.section_semester,instructor_teaches_section.section_program,\n" +
                        "time_slot.day,time_slot.start,time_slot.end FROM instructor JOIN instructor_teaches_course ON instructor.instructor_id = instructor_teaches_course.instructor_instructor_id JOIN course ON instructor_teaches_course.course_course_id = course.course_id JOIN instructor_teaches_section ON instructor.instructor_id = instructor_teaches_section.instructor_instructor_id JOIN section_has_timeslot ON instructor_teaches_section.section_sec_id = section_has_timeslot.section_sec_id and instructor_teaches_section.section_semester = section_has_timeslot.section_semester and instructor_teaches_section.section_program = section_has_timeslot.section_program and instructor_teaches_course.course_course_id = section_has_timeslot.course_course_id JOIN time_slot ON section_has_timeslot.time_slot_time_slot_id = time_slot.time_slot_id and section_has_timeslot.time_slot_day = time_slot.day" +
                        " and instructor_teaches_section.section_sec_id = ? and instructor_teaches_section.section_semester = ? and instructor_teaches_section.section_program = ?  ORDER BY instructor.instructor_id");
                preparedStatement.setString(1, sectionID);
                preparedStatement.setInt(2, semester);
                preparedStatement.setString(3, program);
                resultSet = preparedStatement.executeQuery();
                table.setModel(DbUtils.resultSetToTableModel(resultSet));
            }


        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

//    public void selectAll(JTable table) {
//        try {
//
//            preparedStatement = connection.prepareStatement("SELECT student.stu_reg,student.name," +
//                    "student.section_semester,student.section_sec_id, course.course_id,instructor.name,\n" +
//                    "time_slot.day,time_slot.start,time_slot.end FROM student JOIN takes ON " +
//                    "student.stu_reg = takes.stu_reg JOIN course on takes.course_course_id =" +
//                    " course.course_id JOIN instructor_teaches_course ON course.course_id = " +
//                    "instructor_teaches_course.course_course_id  JOIN instructor ON " +
//                    "instructor_teaches_course.instructor_instructor_id = instructor.instructor_id\n" +
//                    "JOIN section_has_timeslot ON student.section_sec_id = section_has_timeslot.section_sec_id " +
//                    "and student.section_semester = section_has_timeslot.section_semester and student.section_program" +
//                    " = section_has_timeslot.section_program JOIN time_slot ON " +
//                    "section_has_timeslot.time_slot_time_slot_id = time_slot.time_slot_id and " +
//                    "section_has_timeslot.time_slot_day = time_slot.day");
//            resultSet = preparedStatement.executeQuery();
////            while(resultSet.next()){
////                System.out.print(resultSet.getInt(1) + " ");
////                System.out.print(resultSet.getString(2) + " ");
////                System.out.print(resultSet.getInt(3) + " " );
////                System.out.println(resultSet.getString(4));
////            }
//            table.setModel(DbUtils.resultSetToTableModel(resultSet));
//
//
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage());
//        }
//    }
//

    public void selectAll(JTable table) {
        try {

            preparedStatement = connection.prepareStatement("SELECT student.stu_reg,student.name," +
                    "student.section_sec_id,student.section_semester,takes.course_course_id,instructor.name," +
                    "time_slot.day,time_slot.start,time_slot.end,student.department_dept_name" +
                    " FROM student JOIN takes ON student.stu_reg = " +
                    "takes.stu_reg JOIN section_has_timeslot ON\n" +
                    "student.section_sec_id = section_has_timeslot.section_sec_id and student.section_semester " +
                    "=section_has_timeslot.section_semester and student.section_program = " +
                    "section_has_timeslot.section_program and takes.course_course_id = " +
                    "section_has_timeslot.course_course_id JOIN time_slot ON section_has_timeslot.time_slot_time_slot_id" +
                    " = time_slot.time_slot_id and section_has_timeslot.time_slot_day = time_slot.day JOIN" +
                    " instructor_teaches_course ON takes.course_course_id = instructor_teaches_course.course_course_id " +
                    "JOIN instructor ON instructor_teaches_course.instructor_instructor_id = instructor.instructor_id");
            resultSet = preparedStatement.executeQuery();
//            while(resultSet.next()){
//                System.out.print(resultSet.getInt(1) + " ");
//                System.out.print(resultSet.getString(2) + " ");
//                System.out.print(resultSet.getInt(3) + " " );
//                System.out.println(resultSet.getString(4));
//            }
            table.setModel(DbUtils.resultSetToTableModel(resultSet));


        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }


    public String selectNames(String columnName, String tableName, String whereValue, String value) {
        String out = "";
        try {

            preparedStatement = connection.prepareStatement("SELECT " + columnName + " FROM " + tableName +
                    " WHERE " + whereValue + " = ?");
            preparedStatement.setString(1, value);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            out = resultSet.getString(1);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return out;
    }


    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
        // only got here if it didn't return false
        return true;
    }

}
