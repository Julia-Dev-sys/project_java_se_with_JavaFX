package Scene;

import Controller.VendasManutencaoController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VendasManutencao extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loaderVendasManutencao = new FXMLLoader(this.getClass().getResource("layoutVendasManutencao.fxml"));

        VendasManutencaoController vendasManutencaoController = new VendasManutencaoController();
        loaderVendasManutencao.setController(vendasManutencaoController);

        Parent root = loaderVendasManutencao.load();
        stage.setTitle("Manutenção das Vendas");
        stage.setScene(new Scene(root));
        stage.show();

    }
}
