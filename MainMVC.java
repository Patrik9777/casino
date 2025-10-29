import view.LoginView;
import javax.swing.SwingUtilities;

/**
 * MVC Architektúrájú Casino Alkalmazás
 * 
 * Struktúra:
 * - model/: Üzleti logika és adatok (User, UserManager, GameModel)
 * - view/: UI komponensek (LoginView, MainMenuView, GameViews)
 * - controller/: Logika és View közötti kapcsolat (LoginController, GameControllers)
 */
public class MainMVC {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginView();
        });
    }
}
