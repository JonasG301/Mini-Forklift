import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controllers;

public class Xboxcontroller {
    public Xboxcontroller() {
        try {
            Controllers.create();
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }
    }
}
