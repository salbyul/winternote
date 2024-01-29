module org.winternote.winternote {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive java.logging;

    exports org.winternote.winternote;
    opens org.winternote.winternote.controller to javafx.fxml;
    opens org.winternote.winternote to javafx.fxml;
}