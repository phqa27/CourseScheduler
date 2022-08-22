// Students, fill this in.
public class Student implements Comparable<Student> {
  // student name
  protected String name;
  // courses that student take
  protected String[] courses = new String[4];

  /**
  * construct Student object based on name and the courses they take
  */
  public Student(String theName,
         String course1, String course2, String course3, String course4) {
    name = theName;
    courses[0] = course1;
    courses[1] = course2;
    courses[2] = course3;
    courses[3] = course4;
  }

  /**
  * access name of Student
  * @return name of Student
  * @pre none
  * @post return name of Student
  */
  public String getName() {
    return name;
  }

  /**
  * access courses that Student takes
  * @return courses that student takes
  * @pre none
  * @post return courses that student takes
  */
  public String[] getCourses() {
    return courses;
  }

  /**
  * method to compare Students
  * @param o other student to compare to
  * @return positive if current student is first alphabetically, 0 if the same,
  * and negative if other comes first.
  * @post returns integer reprisenting relationship
  */
  public int compareTo(Student o) {
    return name.compareTo(o.getName());
  }

  /**
  * Returns string reprisentation of function
  * @return string reprisentation of function
  */
  public String toString() {
    return name + ": " + courses[0] + ", " + courses[1] + ", " + courses[2] + ", " + courses[3];
  }



}
