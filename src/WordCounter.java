import controller.Controller;
import model.Model;
import view.GUI;

public class WordCounter {

	public static void main(String[] args) {
	    final Model model = new Model();
        final GUI gui = new GUI();
        final Controller controller = new Controller();
        model.addObserver(gui);
        gui.setController(controller);
    }

}
