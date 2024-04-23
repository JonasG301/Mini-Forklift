import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

public class Start{

    static Controller controller;
    public static boolean start;

    public static <string> void main(String[] args) throws InterruptedException, LWJGLException {

        new Xboxcontroller();

        Controllers.create();
        Controllers.poll();
        Controllers.getController(5);


        try {
            System.out.println("1");
            Controllers.create();
        } catch (LWJGLException e) {
            System.out.println(e.getMessage() + "shit!1!!1");
            e.printStackTrace();
        }


        for(int i= 0; i<Controllers.getControllerCount();i++){
            controller = Controllers.getController(i);
            System.out.println(controller.getName());
        }
        for(int j = 0; j < 5;j++) {
           controller = Controllers.getController(j);

           // controller = Controllers.getController(3);
            for (int i = 0; i < controller.getAxisCount(); i++) {
                System.out.println(i + ": " + controller.getAxisName(i));
            }
            for (int i = 0; i < controller.getButtonCount(); i++) {
                System.out.println(i + ": " + controller.getButtonName(i));
            }
            System.out.println("\n");
        }

        controller = Controllers.getController(3);
        while(true){
            controller.poll();
            Thread.sleep(2000);
            int axis = controller.getAxisCount();
            //System.out.println("Axis " + axis);
           // System.out.println(controller.getAxisName(0));
            //System.out.println(controller.getAxisName(1));
            //System.out.println(controller.getAxisName(2));
            //System.out.println(controller.getAxisName(3));
            double Axis1_Y = (controller.getAxisValue(0));
            double Axis1_X = (controller.getAxisValue(1));
            double Axis2_Y = (controller.getAxisValue(2)*-1);
            double Axis2_X = (controller.getAxisValue(3)*-1);
            //start = controller.isButtonPressed(1);
            System.out.println("0 button" + controller.isButtonPressed(0)); // = A
            System.out.println("1 button" +controller.isButtonPressed(1)); // = B
            System.out.println("2 button"+controller.isButtonPressed(2)); // =X
            System.out.println("3 button"+controller.isButtonPressed(3));// =Y
            System.out.println("4 button"+controller.isButtonPressed(4));//=links oben
            System.out.println("5 button"+controller.isButtonPressed(5));//= rechts oben
            System.out.println("6 button"+controller.isButtonPressed(6));//=linker home(funktioniert nicht richitg)
            System.out.println("7 button"+controller.isButtonPressed(7));//=rechter home
            System.out.println("8 button"+controller.isButtonPressed(8));//=linker joystick druck
            System.out.println("9 button"+controller.isButtonPressed(9));//=rechter joystick druck




           /*
            System.out.println(start);
            //System.out.print((Axis1_Y) + "   ");
            System.out.print((Axis1_X)+ "   ");
            System.out.print((Axis2_Y)+ "   ");
            System.out.print((Axis2_X)+ "   ");
            System.out.print((controller.getAxisValue(4))+ "   ");
            System.out.println();

            */

        }
    }
}