
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


@SuppressWarnings("serial")
public class Gui_nerf extends JFrame {
    private Controller controller;
    Connection_nerf trans = new Connection_nerf();

    public Gui_nerf(){

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel pan1 = new JPanel();
        pan1.setLayout(new BorderLayout());
        JPanel pan2 = new JPanel();
        pan2.setLayout(new GridLayout(4,3));
        pan1.add(pan2, BorderLayout.CENTER);

        JButton c1 = new JButton("FIRE!");
        c1.addActionListener(e -> {
            //test.turn_l_s();
            trans.fire();
        });

        JCheckBox c2 = new JCheckBox("KeyListener");
        c2.setEnabled(true);
        c2.addActionListener(e -> {
            pan1.setFocusable(true);
            pan1.requestFocusInWindow();
        });

        JButton b2 = new JButton("Forward");
        b2.addActionListener(e -> {
            //test.drive_f();
            trans.drive_f();
        });
        /*
        JCheckBox c3 = new JCheckBox("ERROR");
        if (error) {
            c3.setEnabled(true); //ACTIVATION BY BOT AND DEACTIVATION BY USER
        } else {
            c3.setEnabled(false);
        }

        //IDEA: DEACTIVATES ROAM UPON DEACTIVATION
        */
        JButton b4 = new JButton("Left 10°");
        b4.addActionListener(e -> {
            //test.turn_l_s();
            trans.turn_l_s();
        });
        JButton b5 = new JButton("Back");
        b5.addActionListener(e -> {
            //test.drive_b();
            trans.drive_b();
        });
        JButton b6 = new JButton("Right 10°");
        b6.addActionListener(e -> {
            //test.turn_r_s();
            trans.turn_r_s();
        });
        JButton b7 = new JButton("R: 90°");
        b7.addActionListener(e -> {
            //test.turn_r_90();
            trans.turn_r_90();
        });
        JButton b8 = new JButton("L: 90°");
        b8.addActionListener(e -> {
            //test.turn_l_90();
            trans.turn_l_90();
        });
        JTextField tf1 = new JTextField();
        tf1.setEnabled(false);
        pan1.add(tf1, BorderLayout.NORTH);

        JButton b11 = new JButton("Down");
        b8.addActionListener(e -> {
            trans.go_down();
        });
        JButton b12 = new JButton("Up");
        b8.addActionListener(e -> {
            trans.go_up();
        });

        if(c2.getActionCommand() != null) {
            wasd(pan1); //KEY LISTENER ON THE WHOLE PANEL
        }

        pan2.add(c1);      //FIRE
        pan2.add(b11);     //BUTTON Down
        pan2.add(b8);      //BUTTON LEFT 90°
        pan2.add(b2);      //BUTTON FORWARD
        pan2.add(b7);      //BUTTON RIGHT 90°
        pan2.add(b4);      //BUTTON TURN LEFT
        pan2.add(b5);      //BUTTON BACKWARDS
        pan2.add(b6); 	   //BUTTON TURN RIGHT
        pan2.add(b12); 	   //BUTTON Up
        pan2.add(c2);      //KEY LISTENER
        this.add(pan1);

        pack();
        setVisible(true);

        try {
            Controllers.create();
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }
        Controllers.poll();

        Thread xbox = new Thread(() -> {

            controller = Controllers.getController(3);
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                controller.poll();
                int exit1_Y = (int) ((controller.getAxisValue(0)) * (-1) *30);
                int exit2_X = (int) ((controller.getAxisValue(1) ) * (1)* 30);
                int exit3_Y = (int) ((controller.getAxisValue(2) ) * (-1) * 41);
                if(controller.isButtonPressed(1)){
                    trans.drive_controller((exit1_Y /3),(exit2_X/3), (exit3_Y/3));
                }else if(controller.isButtonPressed(2)){
                    trans.drive_controller((10000),(0),(0));                        // A sollte noch frei sein
                }
                else if(controller.isButtonPressed(3)){
                    trans.drive_controller((10005),(0),(0));
                }
                else if(controller.isButtonPressed(0)){
                    trans.drive_controller((-10005),(0),(0));
                }
                else if(controller.isButtonPressed(4)){
                    trans.drive_controller((-10010),(0),(0));
                }
                else if(controller.isButtonPressed(5)){
                    trans.drive_controller((10010),(0),(0));
                }
                else if(controller.isButtonPressed(8)){
                    trans.drive_controller((exit1_Y),(0),(0));
                }
                else {
                    trans.drive_controller(exit1_Y, exit2_X/2, (exit3_Y*3));
                }
            }
        });
        xbox.start();
    }


    private void wasd(JComponent a)  {  //KEY LISTENER
        a.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
                    System.out.println("UP");
                    trans.drive_f();
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
                    System.out.println("DOWN");
                    trans.drive_b();
                }
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                    System.out.println("RIGHT");
                    trans.turn_r_s();
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
                    System.out.println("LEFT");
                    trans.turn_l_s();
                }
                else if (e.getKeyCode() == KeyEvent.VK_Q){ //|| e.getKeyCode() == KeyEvent.VK_E) {
                    System.out.println("Q");
                    trans.turn_l_90();
                }
                else if (e.getKeyCode() == KeyEvent.VK_E) {//|| e.getKeyCode() == KeyEvent.VK_E) {
                    System.out.println("E");
                    trans.turn_r_90();
                }
                else if (e.getKeyCode() == KeyEvent.VK_SPACE) {//|| e.getKeyCode() == KeyEvent.VK_E) {
                    System.out.println("Fire");
                    trans.fire();
                }
                else if (e.getKeyCode() == KeyEvent.VK_R) {//|| e.getKeyCode() == KeyEvent.VK_E) {
                    System.out.println("go_up");
                    trans.go_up();
                }  else if (e.getKeyCode() == KeyEvent.VK_F) {//|| e.getKeyCode() == KeyEvent.VK_E) {
                    System.out.println("go_down");
                    trans.go_down();
                }
                //DRIVE WITH W;A;S;D OR DIRECTION ARROWS
                //TURN LEFT 90° WITH Q
                //TURN RIGHT 90° WITH E
                //CHECKBOXES ONLY WITH PANEL
            }

        });
    }

}