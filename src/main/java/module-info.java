module org.winternote.winternote {
    requires javafx.controls;
    requires javafx.fxml;

    exports org.winternote.winternote.controller;
    opens org.winternote.winternote.controller to javafx.fxml;
    exports org.winternote.winternote;
    opens org.winternote.winternote to javafx.fxml;
}