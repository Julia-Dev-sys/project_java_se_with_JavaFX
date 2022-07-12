package Scene;

import Controller.UsuariosController;
import Controller.VendasController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Vendas extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loaderVenda = new FXMLLoader(this.getClass().getResource("layoutVendas.fxml"));


        VendasController vendasController = new VendasController();
        loaderVenda.setController(vendasController);

        Parent root = loaderVenda.load();

        stage.setTitle("Sistema de vendas");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
