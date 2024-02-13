module org.winternote.winternote {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive java.logging;
    requires spring.boot;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    requires spring.jcl;
    requires spring.expression;
    requires spring.aop;
    requires spring.boot.autoconfigure;

    exports org.winternote.winternote to spring.beans, spring.context, javafx.graphics;
    exports org.winternote.winternote.note.persistence to spring.beans;
    exports org.winternote.winternote.note.service to spring.beans;
    exports org.winternote.winternote.model.logging to spring.beans;
    exports org.winternote.winternote.project.persistence to spring.beans;
    exports org.winternote.winternote.project.service to spring.beans;
    exports org.winternote.winternote.model.property to spring.beans;
    exports org.winternote.winternote.model.application.initializer to spring.beans;
    exports org.winternote.winternote.model.application to spring.beans;
    exports org.winternote.winternote.metadata.persistence to spring.beans;
    exports org.winternote.winternote.metadata.service to spring.beans;
    exports org.winternote.winternote.controller to spring.beans;
    exports org.winternote.winternote.common.utils to spring.beans;


    opens org.winternote.winternote.controller to javafx.fxml;
    opens org.winternote.winternote to javafx.fxml, spring.core;
}