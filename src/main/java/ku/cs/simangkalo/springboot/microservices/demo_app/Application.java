package ku.cs.simangkalo.springboot.microservices.demo_app;

import java.net.MalformedURLException;
import java.net.URL;

public class Application {
    public static void main(String[] args) throws MalformedURLException {
        MainController mctrl = new MainController();
        mctrl.start();
    }
}
