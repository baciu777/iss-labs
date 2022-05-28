package com.example.labs;

import domain.Admin;
import domain.Agent;
import domain.Product;
import domain.validator.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import observer.Observer;
import repository.RepoProduct;
import repository.interfaces.Repository;
import repository.interfaces.RepositoryAdmin;
import repository.interfaces.RepositoryAgent;
import repository.interfaces.RepositoryProduct;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainController implements Observer {

    Stage dialogStage;
    RepositoryAgent repositoryAgent;
    RepositoryAdmin repositoryAdmin;
    RepoProduct repositoryProduct;
    Parent mainChatParent;
    @FXML
    TableView<Product> tableView;
    @FXML
    TableColumn<Product, String> tableColumnName;
    @FXML
    TableColumn<Product, String> tableColumnProvider;
    @FXML
    TableColumn<Product, String> tableColumnQuantity;
    @FXML
    TableColumn<Product, String> tableColumnPrice;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField providerTextField;
    @FXML
    private TextField quantityTextField;
    @FXML
    private TextField priceTextField;

    ObservableList<Product> modelProducts = FXCollections.observableArrayList();
    Admin admin;
    public void set(Admin admintT,RepositoryAgent repo1, RepositoryAdmin repo2,RepoProduct repo3,Stage stage)
    {
        repositoryAdmin=repo2;
        repositoryAgent=repo1;
        repositoryProduct=repo3;
        admin=admintT;
        this.dialogStage = stage;
        initModelProducts();
        repositoryProduct.addObserver(this);

    }

    @FXML
    public void initialize() {



        tableColumnName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        tableColumnProvider.setCellValueFactory(new PropertyValueFactory<Product, String>("provider"));
        tableColumnQuantity.setCellValueFactory(new PropertyValueFactory<Product, String>("quantity"));
        tableColumnPrice.setCellValueFactory(new PropertyValueFactory<Product, String>("price"));

        tableView.setItems(modelProducts);
        nameTextField.textProperty().addListener(o -> handleFilter());
        providerTextField.textProperty().addListener(o -> handleFilter());
        quantityTextField.textProperty().addListener(o -> handleFilter());
        priceTextField.textProperty().addListener(o -> handleFilter());


    }
    protected List<Product> getProducts()
    {
        Iterable<Product> usersOnPage = repositoryProduct.findAll();
        List<Product> EvList = StreamSupport.stream(usersOnPage.spliterator(), false).collect(Collectors.toList());
        return EvList;
    }
    protected void initModelProducts() {


        modelProducts.clear();



        for (Product ev : getProducts()) {

            modelProducts.add(ev);

        }

    }
    public void handleFilter() {
        Predicate<Product> p1 = n -> n.getName().startsWith(nameTextField.getText());
        Predicate<Product> p2 = n -> n.getProvider().startsWith(providerTextField.getText());

        modelProducts.setAll(getProducts().stream().filter(p1.and(p2)).collect(Collectors.toList()));
        //System.out.println();

    }
    private Product selectedProduct=null;
    public void handleSelect() {
        Product product = tableView.getSelectionModel().getSelectedItem();
        if(product != null) {
            nameTextField.setText(product.getName());
            providerTextField.setText(product.getProvider());
            quantityTextField.setText(String.valueOf(product.getQuantity()));
            priceTextField.setText(String.valueOf(product.getPrice()));
            selectedProduct=product;
        }



    }


    public void handleAddProduct()
    {
        String name = nameTextField.getText();
        String provider = providerTextField.getText();
        String quantity=quantityTextField.getText();
        String price=priceTextField.getText();
            try
            {   Product product=new Product(name,provider,Integer.parseInt(quantity),Integer.parseInt(price));
                repositoryProduct.add(product);
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setHeaderText("Product added!");
                errorAlert.showAndWait();
                nameTextField.clear();
                providerTextField.clear();
                quantityTextField.clear();
                priceTextField.clear();

            } catch (ValidationException exception) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText(exception.getMessage());
                errorAlert.showAndWait();
            }

    }
    public void handleUpdateButton(){
        Product product = selectedProduct;

        Integer id = 0;
        if(product != null )
        {
            id = product.getID();
            System.out.println(id);
            try {
                String name = nameTextField.getText();
                String provider = providerTextField.getText();
                String quantity=quantityTextField.getText();
                String price=priceTextField.getText();
                Product productNew=new Product(name,provider,Integer.parseInt(quantity),Integer.parseInt(price));

                repositoryProduct.update(productNew,id);
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setHeaderText("Product updated!");
                errorAlert.showAndWait();
                nameTextField.clear();
                providerTextField.clear();
                quantityTextField.clear();
                priceTextField.clear();

            } catch (ValidationException exception) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText(exception.getMessage());
                errorAlert.showAndWait();
            }

        }

    }
    public void handleDeleteProduct()  {
        Product product = selectedProduct;

        Integer id = 0;
        if(product != null )
        {
            id = product.getID();
            System.out.println(id);
            try {
                String name = nameTextField.getText();
                String provider = providerTextField.getText();
                String quantity=quantityTextField.getText();
                String price=priceTextField.getText();
                Product productNew=new Product(name,provider,Integer.parseInt(quantity),Integer.parseInt(price));

                repositoryProduct.delete(product);
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setHeaderText("Product deleted!");
                errorAlert.showAndWait();
                nameTextField.clear();
                providerTextField.clear();
                quantityTextField.clear();
                priceTextField.clear();

            } catch (ValidationException exception) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText(exception.getMessage());
                errorAlert.showAndWait();
            }

        }
    }

    @Override
    public void update() {
        initModel();
    }

    private void initModel() {
        modelProducts.setAll(getProducts());
    }
}
