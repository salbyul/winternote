module org.winternote.winternote {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;

    exports org.winternote.winternote;
    opens org.winternote.winternote.controller to javafx.fxml;
    opens org.winternote.winternote to javafx.fxml;
}