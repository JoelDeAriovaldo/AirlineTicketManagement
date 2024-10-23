package main;

/**
 *
 * @author JoelDeAriovaldo
 */

import view.LoginView;

public class Main {
    public static void main(String[] args) {
        // Launch the login view
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
}
