package Controller;


import db.SQLite;
import entities.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ClientesManutencaoController implements Initializable {

    @FXML
    private TableView<Cliente> twcliente;
    @FXML
    private TableColumn<Cliente,String> nomecolumn;
    @FXML
    private TableColumn<Cliente,String> cpfcolumn;
    @FXML
    private TableColumn<Cliente,String> telcolumn;
    @FXML
    private TableColumn<Cliente,String> nascimentocolumn;
    @FXML
    private TextField tfnome;
    @FXML
    private TextField tfcpf;
    @FXML
    private TextField tftel;
    @FXML
    private TextField tfnascimento;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        nomecolumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cpfcolumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        telcolumn.setCellValueFactory(new PropertyValueFactory<>("tel"));
        nascimentocolumn.setCellValueFactory(new PropertyValueFactory<>("nascimento"));

        try {
            twcliente.setItems(listaTodosCliente());
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }


    }

    private ObservableList<Cliente> listaTodosCliente() throws SQLException, ClassNotFoundException {

        SQLite dbclientes = new SQLite();

        return FXCollections.observableArrayList(dbclientes.getAllClientes());
    }

    public void atualizarTextFieldCliente(Event e){

        ObservableList clienteselecionado = twcliente.getSelectionModel().getSelectedItems();

        Cliente clienteAtualizar = new Cliente();
        clienteAtualizar.setNome(((Cliente) clienteselecionado.get(0)).getNome());
        clienteAtualizar.setCpf(((Cliente) clienteselecionado.get(0)).getCpf());
        clienteAtualizar.setTel(((Cliente) clienteselecionado.get(0)).getTel());
        clienteAtualizar.setNascimento(((Cliente) clienteselecionado.get(0)).getNascimento());

        tfnome.setText(clienteAtualizar.getNome());
        tfcpf.setText(clienteAtualizar.getCpf());
        tftel.setText(clienteAtualizar.getTel());
        tfnascimento.setText(clienteAtualizar.getNascimento());


    }

    public void excluirCliente(Event e){

        ObservableList clienteselecionado = twcliente.getSelectionModel().getSelectedItems();

        Cliente clienteEliminar = new Cliente();
        clienteEliminar.setNome(((Cliente) clienteselecionado.get(0)).getNome());
        clienteEliminar.setCpf(((Cliente) clienteselecionado.get(0)).getCpf());
        clienteEliminar.setTel(((Cliente) clienteselecionado.get(0)).getTel());
        clienteEliminar.setNascimento(((Cliente) clienteselecionado.get(0)).getNascimento());

        try {
            SQLite dbExcluirCliente = new SQLite();
            dbExcluirCliente.eliminarCliente(clienteEliminar);

            twcliente.getItems().clear();
            twcliente.setItems(listaTodosCliente());

            Alert alertEliminacao = new Alert(Alert.AlertType.INFORMATION,"Cliente excluido com sucesso!");
            alertEliminacao.show();
            limparCampo();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void atualizarCliente(Event e){

        ObservableList<Cliente> clienteListAntigo = twcliente.getSelectionModel().getSelectedItems();

        String nome = tfnome.getText();
        String cpf = tfcpf.getText();
        String tel = tftel.getText();
        String nascimento = tfnascimento.getText();

        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNome(nome);
        clienteAtualizado.setCpf(cpf);
        clienteAtualizado.setTel(tel);
        clienteAtualizado.setNascimento(nascimento);


        try {
            SQLite dbAtualizarCliente = new SQLite();
            dbAtualizarCliente.atualizarCliente(clienteAtualizado);

            twcliente.getItems().clear();
            twcliente.setItems(listaTodosCliente());

            Alert alertAtualizar = new Alert(Alert.AlertType.INFORMATION,"Cliente atualizado com sucesso");
            alertAtualizar.show();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public void limparCampo(){

        tfnome.clear();
        tfcpf.clear();
        tftel.clear();
        tfnascimento.clear();
    }
}