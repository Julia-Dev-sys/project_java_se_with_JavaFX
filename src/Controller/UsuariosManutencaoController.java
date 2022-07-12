package Controller;

import db.SQLite;
import entities.Usuario;
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

public class UsuariosManutencaoController implements Initializable {

    @FXML
    private TableView<Usuario> twusuario;

    @FXML
    private TableColumn<Usuario,String> nomecolumn;

    @FXML
    private TableColumn<Usuario,String> emailcolumn;

    @FXML
    private TextField tfnome;

    @FXML
    private TextField tfemail;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        nomecolumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        emailcolumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        try {
            twusuario.setItems(listaTodosUsuarios());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private ObservableList<Usuario> listaTodosUsuarios() throws SQLException, ClassNotFoundException {

        SQLite dbusuarios = new SQLite();

        return FXCollections.observableArrayList(dbusuarios.getAllUsuarios());
    }

    public void atualizarTextField(Event e){


        ObservableList usuarioselecionado = twusuario.getSelectionModel().getSelectedItems();

        Usuario usuarioAtualizar = new Usuario();
        usuarioAtualizar.setNome(((Usuario) usuarioselecionado.get(0)).getNome());
        usuarioAtualizar.setEmail(((Usuario) usuarioselecionado.get(0)).getEmail());

        tfnome.setText(usuarioAtualizar.getNome());
        tfemail.setText(usuarioAtualizar.getEmail());

    }

    public void excluirUsuario(Event e ){

        ObservableList usuarioselecionado = twusuario.getSelectionModel().getSelectedItems();

        Usuario usuarioEliminar = new Usuario();
        usuarioEliminar.setNome(((Usuario) usuarioselecionado.get(0)).getNome());
        usuarioEliminar.setEmail(((Usuario) usuarioselecionado.get(0)).getEmail());

        try {
            SQLite dExcluirUsuario = new SQLite();
            dExcluirUsuario.eliminarUsuario(usuarioEliminar);

            twusuario.getItems().clear();
            twusuario.setItems(listaTodosUsuarios());

            Alert alertEliminacao = new Alert(Alert.AlertType.INFORMATION,"Usuario excluido com sucesso!");
            alertEliminacao.show();
            limparCampo();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

    }

    public void atualizarUsuario(Event e){

        ObservableList usuarioListAntigo = twusuario.getSelectionModel().getSelectedItems();

        Usuario usuarioAntigo = new Usuario();
        usuarioAntigo.setEmail(((Usuario) usuarioListAntigo.get(0)).getEmail());

        String nome = tfnome.getText();
        String email = tfemail.getText();

        Usuario usuarioAtualizar = new Usuario();
        usuarioAtualizar.setNome(nome);
        usuarioAtualizar.setEmail(email);

        try {
            SQLite dbAtualizarUsuario = new SQLite();
            dbAtualizarUsuario.atualizarUsuario(usuarioAtualizar,usuarioAntigo.getEmail());

            twusuario.getItems().clear();
            twusuario.setItems(listaTodosUsuarios());

            Alert alertAtualizar = new Alert(Alert.AlertType.INFORMATION,"Usuario atualizado com sucesso!");
            alertAtualizar.show();


        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public void limparCampo(){

        tfnome.clear();
        tfemail.clear();

    }
}
