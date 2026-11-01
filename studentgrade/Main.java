
package studentgrade;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Main extends JFrame {

    JTextField collegeField, nameField, rollField, countField;
    JPanel subjectPanel;
    ArrayList<JTextField> subjectNames = new ArrayList<>();
    ArrayList<JTextField> subjectMarks = new ArrayList<>();

    public Main() {
        setTitle("Student Grade Management System");
        setSize(650, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        topPanel.add(new JLabel("College Name:"));
        collegeField = new JTextField();
        topPanel.add(collegeField);

        topPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        topPanel.add(nameField);

        topPanel.add(new JLabel("Roll Number:"));
        rollField = new JTextField();
        topPanel.add(rollField);

        topPanel.add(new JLabel("Number of Subjects:"));
        countField = new JTextField();
        topPanel.add(countField);

        JButton generateButton = new JButton("Generate Subject Fields");
        topPanel.add(generateButton);

        JButton calculateButton = new JButton("Calculate Grade");
        topPanel.add(calculateButton);

        add(topPanel, BorderLayout.NORTH);

        subjectPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        subjectPanel.setBorder(
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        );

        JScrollPane scrollPane = new JScrollPane(subjectPanel);
        add(scrollPane, BorderLayout.CENTER);

        generateButton.addActionListener(e -> generateSubjects());
        calculateButton.addActionListener(e -> calculateGrade());

        setVisible(true);
    }

    void generateSubjects() {
        try {
            int count = Integer.parseInt(countField.getText());

            if (count < 1 || count > 50) {
                JOptionPane.showMessageDialog(
                    this, "Enter a number between 1 and 50."
                );
                return;
            }

            subjectPanel.removeAll();
            subjectNames.clear();
            subjectMarks.clear();

            for (int i = 1; i <= count; i++) {
                subjectPanel.add(new JLabel("Subject " + i + " Name:"));

                JTextField subjectName = new JTextField();
                subjectNames.add(subjectName);
                subjectPanel.add(subjectName);

                subjectPanel.add(new JLabel("Subject " + i + " Marks:"));

                JTextField marks = new JTextField();
                subjectMarks.add(marks);
                subjectPanel.add(marks);
            }

            subjectPanel.revalidate();
            subjectPanel.repaint();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this, "Please enter a valid number of subjects."
            );
        }
    }

    void calculateGrade() {
        try {
            if (subjectNames.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this, "First generate the subject fields."
                );
                return;
            }

            String college = collegeField.getText().trim();
            String student = nameField.getText().trim();
            String roll = rollField.getText().trim();

            if (college.isEmpty() || student.isEmpty() || roll.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this, "Please fill all student details."
                );
                return;
            }

            int total = 0;
            int count = subjectNames.size();

            StringBuilder result = new StringBuilder();

            result.append("====================================\n");
            result.append("         STUDENT RESULT CARD        \n");
            result.append("====================================\n\n");

            result.append("COLLEGE: ").append(college).append("\n\n");

            result.append("STUDENT DETAILS\n");
            result.append("Name    : ").append(student).append("\n");
            result.append("Roll No : ").append(roll).append("\n\n");

            result.append("SUBJECT PERFORMANCE\n");
            result.append("------------------------------------\n");

            for (int i = 0; i < count; i++) {
                String subject = subjectNames.get(i).getText().trim();

                if (subject.isEmpty()) {
                    JOptionPane.showMessageDialog(
                        this, "Enter all subject names."
                    );
                    return;
                }

                int marks = Integer.parseInt(
                    subjectMarks.get(i).getText().trim()
                );

                if (marks < 0 || marks > 100) {
                    JOptionPane.showMessageDialog(
                        this, "Marks must be between 0 and 100."
                    );
                    return;
                }

                total += marks;

                result.append(String.format(
                    "%-20s %3d / 100\n", subject, marks
                ));
            }

            double average = (double) total / count;

            String grade;
            String message;

            if (average >= 90) {
                grade = "A+";
                message = "EXCELLENT PERFORMANCE";
            } else if (average >= 75) {
                grade = "A";
                message = "VERY GOOD PERFORMANCE";
            } else if (average >= 60) {
                grade = "B";
                message = "GOOD PERFORMANCE";
            } else if (average >= 40) {
                grade = "C";
                message = "KEEP IMPROVING";
            } else {
                grade = "F";
                message = "BETTER LUCK NEXT TIME";
            }

            result.append("------------------------------------\n\n");

            result.append("TOTAL MARKS : ")
                  .append(total).append(" / ")
                  .append(count * 100).append("\n");

            result.append(String.format(
                "AVERAGE     : %.2f%%\n\n", average
            ));

            result.append("+----------------------------------+\n");
            result.append("          GRADE: ").append(grade).append("\n");
            result.append("     ").append(message).append("\n");
            result.append("+----------------------------------+\n\n");

            result.append("     KEEP UP THE GREAT WORK!");

            JTextArea resultArea = new JTextArea(result.toString());
            resultArea.setFont(new Font("Monospaced", Font.BOLD, 14));
            resultArea.setEditable(false);

            JScrollPane resultScroll = new JScrollPane(resultArea);
            resultScroll.setPreferredSize(new Dimension(500, 500));

            JOptionPane.showMessageDialog(
                this,
                resultScroll,
                "Student Result",
                JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this, "Please enter valid marks using numbers only."
            );
        }
    }

    public static void main(String[] args) {
    Main app = new Main();
    }
}
