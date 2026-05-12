import javax.swing.*;

public class MW {
    private JFrame window;

    public MW() throws InterruptedException {
        System.out.println("Stealing your ram....");
        Thread.sleep(1000);

        System.out.println("Mining bitcoin....");
        Thread.sleep(1000);

        System.out.println("stealing your bandwidth....");
        Thread.sleep(100);

        System.out.println("Theft");
        Thread.sleep(100);

        System.out.println("its gunna start reaallll soon trust");
        Thread.sleep(1000);

        System.out.println("ok fine");
        Thread.sleep(100);

        run();
    }
    public void run() {
        window = new JFrame();
        window.setTitle("Fobal simulator");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.setSize(700,700);
        window.setLocationRelativeTo(null);
    }
    public void show() {
        window.setVisible(true);
    }
}
