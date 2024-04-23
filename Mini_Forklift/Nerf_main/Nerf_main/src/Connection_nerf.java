
import java.io.*;
import java.net.Socket;

public class Connection_nerf {
    String receive;

    String exit = "";
    //String exit;

    public Connection_nerf() {

        //String host = "127.0.0.1"; //NETWORK DELAYER
        String host = "192.168.4.1"; //BOT CONNECTION

        try {
            Socket s = new Socket(host, 23);
            BufferedReader r = new BufferedReader((new InputStreamReader(s.getInputStream())));
            BufferedWriter w = new BufferedWriter((new OutputStreamWriter(s.getOutputStream())));
            Thread rx = new Thread(() -> {
                while (true) {
                    try {
                        receive = r.readLine(); //RECOVER STRING
                        System.out.println(receive);
                        // CAN RECOVER SOME VALUES FROM THE BOT
                        Thread.sleep(100);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            });
            Thread tx = new Thread(() -> {
                while (true) {
                    try {
                        if (exit != "0") {
                            System.out.println(exit);
                            w.write(exit +  "\0");//SEND STRING
                            w.flush();
                            reset();//RESET SEND STRING
                        }
                        Thread.sleep(50);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            tx.start();
            rx.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drive_f() {
        exit = "1/1/11";
    }

    public void drive_b() {
        exit = "1/1/1";
    }

    public void turn_l_90() {
        exit = "1/1/1";
    }

    public void turn_r_90() {
        exit = "1/1/1";
    }

//	    public void roam() {
//	        exit = '5';
//	    }

    public void turn_l_s()  {
        exit = "1/1/1";
    }

    public void turn_r_s() {
        exit = "1/1/1";
    }

    //    public void keep_right() { exit = '8'; }

    private void reset() {
        exit = "0";
    }

    public void go_down() {
    }

    public void go_up() {
    }

    public void fire() {
    }
    public void drive_controller(int exit1_y, int exit2_x, int exit3_y) {
        exit = String.valueOf(exit1_y )+ "/" + String.valueOf(exit2_x)+ "/" + String.valueOf(exit3_y);
    }
}
