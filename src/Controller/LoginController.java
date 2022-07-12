package Controller;

import Scene.*;
import db.SQLite;
import entities.Usuario;
import entities.VendaProduto;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class LoginController {

    @FXML
    Button btLogar;
    @FXML
    Button btCancelar;
    @FXML
    TextField tfUsuario = null;
    @FXML
    TextField tfSenha = null;
    @FXML
    MenuBar menuPrincipal;
    @FXML
    Label lbUsuario;
    @FXML
    Label lbSenha;
    @FXML
    Label lbUsuarioLogado;
    @FXML
    Label lbdata;
    @FXML
    Label lbhora;


    ///// MÉTODO PARA LOGAR NO SISTEMA /////
    @FXML
    public void clickLogar(Event e) throws Exception {
        Boolean checkLogin = true;
        String usuario = tfUsuario.getText();
        String senha = tfSenha.getText();
        Alert alertLogar = new Alert(Alert.AlertType.INFORMATION, "Usuário Logado");


        if (usuario.isEmpty()) {
            alertLogar.setContentText("Primeiro Informar o usuário");
            checkLogin = false;
        }else {
            if (senha.isEmpty()) {
                alertLogar.setContentText("Informe a sua senha");
                checkLogin = false;
            }
        }

        if (!checkLogin) {
            alertLogar.show();
        }else{
            Usuario usuarioLogin = new Usuario();
            usuarioLogin.setEmail(usuario);
            usuarioLogin.setSenha(senha);

            SQLite dbsqlite = new SQLite();
            if (dbsqlite.checkLogin(usuarioLogin)){
                // Faz Login
                lbUsuarioLogado.setText("Logado como (" + usuarioLogin.getEmail() + ")");
                tfSenha.setText("");
                dataSistema();
                horaSistema();
                clickMostrar();
            }else{
                alertLogar.setContentText("Usuário não cadastrado");
                alertLogar.show();
            }
        }
    }

    ///// METÓDO PARA HABILITAR/DESABILITAR LABELS E BUTTONS NO MENU LOGIN /////

    private void clickMostrar() {

        menuPrincipal.setDisable(false);
        tfUsuario.setVisible(false);
        tfSenha.setVisible(false);
        btLogar.setVisible(false);
        btCancelar.setVisible(false);
        lbUsuario.setVisible(false);
        lbSenha.setVisible(false);
    }
    @FXML
    public void clickRelogar(Event e){
        lbUsuarioLogado.setText("");
        menuPrincipal.setDisable(true);
        tfUsuario.setVisible(true);
        tfSenha.setVisible(true);
        btLogar.setVisible(true);
        btCancelar.setVisible(true);
        lbUsuario.setVisible(true);
        lbSenha.setVisible(true);
    }

    /////  METÓDOS PARA ENTRAR NOS MENUS DE PRODUTOS/CLIENTES/USUÁRIOS/VENDAS /////

    @FXML
    public void clickCancelar(Event e){
        tfUsuario.setText("");
        tfSenha.setText("");
    }

    @FXML
    public void clickCadastrarUsuario(Event e) throws Exception {
        Usuarios usuarios = new Usuarios();
        usuarios.start(new Stage());
    }

    @FXML
    public void clickCadastrarCliente(Event e) throws Exception {
        Clientes clientes = new Clientes();
        clientes.start(new Stage());
    }

    @FXML
    public void clickCadastrarProduto(Event e ) throws Exception {
        Produtos produtos = new Produtos();
        produtos.start(new Stage());

    }

    @FXML
    public void clickCadastrarVenda(Event e ) throws  Exception{
        Vendas vendas = new Vendas();
        vendas.start(new Stage());
    }



    ///// MÉTODOS PARA ENTRAR NA MANUTENÇÃO DE PRODUTOS/CLIENTES/USUÁRIOS/VENDAS /////

    @FXML
    public void clickManterUsuario(Event e ) throws Exception {
        UsuariosManutencao usuariosManutencao = new UsuariosManutencao();
        usuariosManutencao.start(new Stage());
    }

    @FXML
    public void clickManterProduto(Event e) throws Exception {
        ProdutosManutencao produtosManutencao = new ProdutosManutencao();
        produtosManutencao.start(new Stage());
    }
    @FXML
    public void clickManterCliente(Event e) throws Exception {
        ClientesManutencao clienteManutencao = new ClientesManutencao();
        clienteManutencao.start(new Stage());

    }
    @FXML
    public void clickManterVendas(Event e) throws Exception {
        VendasManutencao vendasManutencao = new VendasManutencao();
        vendasManutencao.start(new Stage());


    }


    ///// METÓDOS PARA INSERIR DATA E HORA NO MENU LOGIN /////

    @FXML
    public void dataSistema(){
        Calendar c = Calendar.getInstance();
        Date data = c.getTime();
        DateFormat formatoData = DateFormat.getDateInstance(DateFormat.FULL);
        lbdata.setText(formatoData.format(data));
    }

    private SimpleDateFormat formatador = new SimpleDateFormat("hh:mm:ss a");

    public void horaSistema(){
        KeyFrame frame = new KeyFrame(Duration.millis(1000), event -> AtualizaHora());
        Timeline timeline = new Timeline(frame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private  void AtualizaHora(){
        Date agora = new Date();
        lbhora.setText(formatador.format(agora));
    }
}