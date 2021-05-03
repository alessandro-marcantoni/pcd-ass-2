package Reactive;

import io.reactivex.rxjava3.subjects.PublishSubject;

public class WordCounter {

    public static void main(String[] args) {
        final PublishSubject<Parameter> stream = PublishSubject.create();
        final GUI gui = new GUI(stream);
        final Controller controller = new Controller(stream, gui);
        gui.setController(controller);
    }

}
