package Scene;

import Controller.ClientesManutencaoController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientesManutencao extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loaderClientesManutencao = new FXMLLoader(this.getClass().getResource("layoutClienteManutencao.fxml"));

        ClientesManutencaoController clientesManutencaoController = new ClientesManutencaoController();
        loaderClientesManutencao.setController(clientesManutencaoController);

        Parent root = loaderClientesManutencao.load();
        stage.setTitle("Manutenção de Clientes");
        stage.setScene(new Scene(root));
        stage.show();

    }
}
