package Tasks;

import Tasks.controller.Controller;
import Tasks.model.Model;
import Tasks.view.GUI;

public class WordCounter {

	public static void main(String[] args) {
	    final Model model = new Model();
        final GUI gui = new GUI();
        final Controller controller = new Controller(gui);
        model.addObserver(gui);
        gui.setController(controller);
    }

}
