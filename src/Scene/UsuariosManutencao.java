package Scene;

import Controller.LoginController;
import Controller.UsuariosManutencaoController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UsuariosManutencao extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loaderUsuariosManutencao = new FXMLLoader(this.getClass().getResource("layoutUsuarioManutencao.fxml"));

        UsuariosManutencaoController usuariosManutencaoController = new UsuariosManutencaoController();
        loaderUsuariosManutencao.setController(usuariosManutencaoController);

        Parent root = loaderUsuariosManutencao.load();
        stage.setTitle("Manutenção de Usuarios");
        stage.setScene(new Scene(root));
        stage.show();


    }
}
