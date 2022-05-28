package com.example.labs;

import domain.Agent;
import domain.Product;
import domain.validator.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import observer.Observer;
import repository.RepoProduct;
import repository.interfaces.RepositoryAdmin;
import repository.interfaces.RepositoryAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class EmplController implements Observer {

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
    private ComboBox<Integer> quantityTextField;
    @FXML
    private TextField priceTextField1;
    @FXML
    private TextField priceTextField2;

    Agent agent;
    ObservableList<Product> modelProducts = FXCollections.observableArrayList();
    public void set(Agent agentT, RepositoryAgent repo1, RepositoryAdmin repo2, RepoProduct repo3, Stage stage)
    {
        repositoryAdmin=repo2;
        repositoryAgent=repo1;
        repositoryProduct=repo3;
        this.dialogStage = stage;
        agent=agentT;
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
        priceTextField1.setText("0");
        priceTextField2.setText("0");
        nameTextField.textProperty().addListener(o -> handleFilter());
        providerTextField.textProperty().addListener(o -> handleFilter());
        priceTextField1.textProperty().addListener(o -> handleFilter());
        priceTextField2.textProperty().addListener(o -> handleFilter());

    }
    public void handleFilter() {
        Predicate<Product> p1 = n -> n.getName().startsWith(nameTextField.getText());
        Predicate<Product> p2 = n -> n.getProvider().startsWith(providerTextField.getText());
        if(Objects.equals(priceTextField1.getText(), ""))
            priceTextField1.setText("0");
        if(Objects.equals(priceTextField2.getText(), ""))
            priceTextField2.setText("10000");
        Predicate<Product> p3 = n ->n.getPrice()>=Integer.parseInt(priceTextField1.getText());
        Predicate<Product> p4 = n ->n.getPrice()<=Integer.parseInt(priceTextField2.getText());
        modelProducts.setAll(getProducts().stream().filter(p1.and(p2).and(p3).and(p4)).collect(Collectors.toList()));
        //System.out.println();

    }
    protected void initModelProducts() {


        modelProducts.clear();

        Iterable<Product> usersOnPage = repositoryProduct.findAll();
        List<Product> EvList = StreamSupport.stream(usersOnPage.spliterator(), false).collect(Collectors.toList());


        for (Product ev : EvList) {

            modelProducts.add(ev);

        }


    }
    @Override
    public void update() {
        initModel();
    }

    private void initModel() {
        modelProducts.setAll(getProducts());
    }
    private List<Product> getProducts()
    {
        Iterable<Product> usersOnPage = repositoryProduct.findAll();
        List<Product> EvList = StreamSupport.stream(usersOnPage.spliterator(), false).collect(Collectors.toList());
        return EvList;
    }



    public void handleSelect() {
        Product product = tableView.getSelectionModel().getSelectedItem();
        if(product != null) {
            nameTextField.setText(product.getName());
            providerTextField.setText(product.getProvider());
            quantityTextField.setValue(product.getQuantity());
            quantityTextField.setItems(getQuantityElems(product.getQuantity()));
            priceTextField1.setText(String.valueOf(product.getPrice()));
            priceTextField2.setText(String.valueOf(product.getPrice()));
            selectedProduct=product;
        }

    }

    private ObservableList<Integer> getQuantityElems(int quantity) {
        int i=0;
        ArrayList<Integer> items=new ArrayList<Integer>();
        ObservableList<Integer> observableList = FXCollections.observableList(items);
        while(i<=quantity)
        {items.add(i);
            i=i+5;

        }
        return observableList;

    }
    private Product selectedProduct=null;
    public void handleOrderProduct()
    {
        Product product = selectedProduct;

        Integer id = 0;
        if(product != null ) {
            id = product.getID();
            System.out.println(id);

            String name = nameTextField.getText();
            String provider = providerTextField.getText();
            Integer quantity = quantityTextField.getValue();
            String price = priceTextField1.getText();
            try {
                Product product1 = new Product(name, provider, quantity, Integer.parseInt(price));
                repositoryProduct.order(product1, quantity, id);
                Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                errorAlert.setHeaderText("Product added to basket!");
                errorAlert.showAndWait();
                nameTextField.clear();
                providerTextField.clear();
                quantityTextField.getSelectionModel().clearSelection();
                priceTextField1.clear();
                priceTextField2.clear();

            } catch (ValidationException exception) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText(exception.getMessage());
                errorAlert.showAndWait();
            }
        }

    }
    public void handleFinishOrder()
    {
        Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setHeaderText("Order finished!");
        errorAlert.showAndWait();
    }
}
