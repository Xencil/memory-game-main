module uk.ac.gold.memorygame {
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires javafx.media;
    requires transitive java.prefs;
    requires org.apache.logging.log4j;

    exports uk.ac.gold.memorygame;
    exports uk.ac.gold.memorygame.model;
    exports uk.ac.gold.memorygame.observer;
}
