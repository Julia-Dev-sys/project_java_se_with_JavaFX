package Controller;

import db.SQLite;
import entities.VendaProduto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VendasManutencaoController implements Initializable {

    @FXML
    private TextField tfcliente;
    @FXML
    private Label lbclienteselecionado;
    @FXML
    private TextField tfproduto;
    @FXML
    private Label lbprodutoselecionado;
    @FXML
    private TableView<VendaProduto> twmanutencaovenda;
    @FXML
    private TableColumn<VendaProduto,Integer> tcCodigoProduto;
    @FXML
    private TableColumn<VendaProduto,String>tcCodigoCliente;
    @FXML
    private TableColumn<VendaProduto,Integer>tcQuantidade;
    @FXML
    private TableColumn<VendaProduto,Double>tcUnitario;
    @FXML
    private TableColumn<VendaProduto,Double>tcTotal;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcCodigoProduto.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        tcCodigoCliente.setCellValueFactory(new PropertyValueFactory<>("codigo_cliente"));
        tcQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tcUnitario.setCellValueFactory(new PropertyValueFactory<>("unitario"));
        tcTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        try {
            twmanutencaovenda.setItems(listaTodasVendas());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private ObservableList<VendaProduto> listaTodasVendas() throws SQLException, ClassNotFoundException {


        SQLite dbvendaProduto = new SQLite();
        return FXCollections.observableArrayList(dbvendaProduto.getAllVendaProduto());

    }
}
