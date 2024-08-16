module com.example.bubblebobble {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;


    opens com.example.bubblebobble to javafx.fxml;
    exports com.example.bubblebobble;
    exports com.example.bubblebobble.Controllers;
    opens com.example.bubblebobble.Controllers to javafx.fxml;
    exports com.example.bubblebobble.GameObjects.NonstaticGameObjects;
    opens com.example.bubblebobble.GameObjects.NonstaticGameObjects to javafx.fxml;
    exports com.example.bubblebobble.GameObjects.Weapons;
    opens com.example.bubblebobble.GameObjects.Weapons to javafx.fxml;
    exports com.example.bubblebobble.GameObjects.StaticGameObjects;
    opens com.example.bubblebobble.GameObjects.StaticGameObjects to javafx.fxml;
    exports com.example.bubblebobble.Handlers;
    opens com.example.bubblebobble.Handlers to javafx.fxml;
    exports com.example.bubblebobble.SoundEffect;
    opens com.example.bubblebobble.SoundEffect to javafx.fxml;
    exports com.example.bubblebobble.GameObjects.NonstaticGameObjects.Enemy;
    opens com.example.bubblebobble.GameObjects.NonstaticGameObjects.Enemy to javafx.fxml;
    exports com.example.bubblebobble.GameObjects.NonstaticGameObjects.Boss;
    opens com.example.bubblebobble.GameObjects.NonstaticGameObjects.Boss to javafx.fxml;
    exports com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero;
    opens com.example.bubblebobble.GameObjects.NonstaticGameObjects.Hero to javafx.fxml;
    exports com.example.bubblebobble.InteractableWorld;
    opens com.example.bubblebobble.InteractableWorld to javafx.fxml;
    exports com.example.bubblebobble.InteractableWorld.Factory;
    opens com.example.bubblebobble.InteractableWorld.Factory to javafx.fxml;
    exports com.example.bubblebobble.InteractableWorld.ScoreBoard;
    opens com.example.bubblebobble.InteractableWorld.ScoreBoard to javafx.fxml;
    exports com.example.bubblebobble.PopUpWindows;
    opens com.example.bubblebobble.PopUpWindows to javafx.fxml;
}