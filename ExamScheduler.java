import java.util.Scanner;
import structure5.*;

public class ExamScheduler {
  // graph containing courses as nodes and students as edges
  protected GraphListUndirected<String, String> scheduler;
  // list of students
  protected OrderedVector<Student> students;
  // list of exam time slots
  protected Vector<SinglyLinkedList<String>> schedule;

  /**
  * construct ExamScheduler object
  * @para file is name of file of students and their courses
  */
  public ExamScheduler(String file) {
    students = new OrderedVector<Student>();
    // populate Vector students
    readFile(file);
    scheduler = new GraphListUndirected<String, String>();
    // populate graph scheduler
    createScheduler();
    schedule = new Vector<SinglyLinkedList<String>>();
  }

  /**
  * read file of students
  * @param filename is file of students
  * @pre none
  * @post create Student objects and add to vector students
  */
  public void readFile(String fileName) {
    Scanner in = new Scanner(new FileStream(fileName));
    while (in.hasNextLine()) {
      String name = in.nextLine();
      String course1 = in.nextLine();
      String course2 = in.nextLine();
      String course3 = in.nextLine();
      String course4 = in.nextLine();
      Student s = new Student(name, course1, course2, course3, course4);
      students.add(s);
    }
  }

  /**
  * populate graph scheduler with courses and students
  * @pre none
  * @post populate scheduler, courses as vertices, students as edges
  */
  public void createScheduler() {
    // iterate over all students
    for (Student s: students) {
      String name = s.getName();
      String[] courses = s.getCourses();
      // iterate over all courses that student takes
      for (String c: courses) {
        // if course not present in scheduler, add vertex to scheduler
        if (!scheduler.contains(c)) {
          scheduler.add(c);
        }
      }
      // add student name as edge between each pair of courses
      scheduler.addEdge(courses[0], courses[1], name);
      scheduler.addEdge(courses[0], courses[2], name);
      scheduler.addEdge(courses[0], courses[3], name);
      scheduler.addEdge(courses[1], courses[2], name);
      scheduler.addEdge(courses[1], courses[3], name);
      scheduler.addEdge(courses[2], courses[3], name);
    }
  }
  /**
   * method to print an exam schedule for each student in alphabetical order
   * @pre none
   * @post prints out a schedule for each student in alphabetical order
   */
  public void printScheduleforStudent() {
    for (Student s : students) {
      System.out.println(s.getName() + " Exam Schedule:");
      String[] curCourses = s.getCourses();
      for (int i = 0; i < 4; i++) {
        String target = curCourses[i];
        for (int timeSlot = 0; timeSlot < schedule.size(); timeSlot++) {
          if (schedule.get(timeSlot).contains(target)) {
            System.out.println("Slot " + (timeSlot + 1) + ": " + target);
            break; // this just saves some time
          }
        }
      }
    }
  }
  /**
  * creates a course schedule and stores in schedule instance var
  * @post schedule is populated with linkedLists that reprisent timeslots.
  * @post the graph remains unchanged, and all visited flags are reset.
  */
  public void makeSchedule() {
    scheduler.reset();
    for (String s : scheduler) { // for all vertices
      if (!scheduler.isVisited(s)) {
        // if not visited then should start new timeslot
        SinglyLinkedList<String> timeSlot = new SinglyLinkedList<String>();
        timeSlot.add(s);
        scheduler.visit(s);
        for (String c : scheduler) { // for all vertices
          if (!scheduler.isVisited(c)) {
            // if not visited yet, see if we can add to the timeslot
            boolean noEdge = true; // assume there is no edge
            for (String inSlot : timeSlot) { // for all verticies in timeslot
              if (scheduler.containsEdge(c, inSlot)) {
                // if they share an edge then assumption is wrong
                noEdge = false;
              }
            }
            if (noEdge && !timeSlot.contains(c)) {
              timeSlot.add(c);
              scheduler.visit(c);
            }
          }
        }
        schedule.add(timeSlot);
      }
    }
    scheduler.reset(); // resets to maintain pre-condition
  }


  /**
  * provide string representation of exam schedule
  */
  public String toString() {
    String result = "";
    for (int i = 0; i < schedule.size(); i++) {
      result += "Slot " + (i + 1);
      for (String c: schedule.get(i)) {
        result += " " + c;
      }
      result += "\n";
    }
    return result;
  }

  /**
  * execute program
  */
  public static void main(String[] args) {
    ExamScheduler myScheduler = new ExamScheduler(args[0]);
    myScheduler.makeSchedule();
    System.out.println(myScheduler);
    System.out.println();
    myScheduler.printScheduleforStudent();
  }
}
