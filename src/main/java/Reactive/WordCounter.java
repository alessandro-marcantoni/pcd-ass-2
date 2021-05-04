package Reactive;

public class WordCounter {

    public static void main(String[] args) {
        final GUI gui = new GUI();
        final Controller controller = new Controller(gui);
        gui.setController(controller);
    }

}
