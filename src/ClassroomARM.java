

public class ClassroomARM implements Runnable {

    public static void main(String[] args) {
        Thread t = new Thread(new ClassroomARM());
        t.start();
    }

    public void run() {
        GUI gui = new GUI(); // Create frame and components
    }
}