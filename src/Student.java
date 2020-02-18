import java.io.Serializable;

public class Student implements Serializable{

    private static final long serialVersionUID = -4607861880283547016L;

    String name;
    int grade;
    int age;

    public Student(String n, int g, int a) {
        this.name = n;
        this.grade = g;
        this.age = a;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toString() {
        return "Student: " + name +
                " Grade: " + grade +
                " Age: " + age + "\n\n";
    }
}