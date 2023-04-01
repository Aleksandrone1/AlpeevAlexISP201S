package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;


public  class Tovar extends Application {
    int i=0;

    GridPane gridPane1 = new GridPane();
    Button button=new Button("Получить артикул");
    ObservableList<Label> labels = FXCollections.observableArrayList();
    Pane resultsPane = new Pane();
@Override
public void start(Stage stage) throws SQLException,IOException {

    Button vozvr = new Button("Отредактировать данные");
    Button ubyv = new Button("Вставить данные");


    TextField textField = new TextField();
    GridPane gridPane1 = new GridPane();
    gridPane1.setAlignment(Pos.TOP_LEFT);
    gridPane1.setHgap(10);
    gridPane1.setVgap(10);
    gridPane1.setPadding(new Insets(25, 25, 25, 25));
    Button button1 = new Button("Получить артикул");
    textField.textProperty().addListener((observable, oldValue, newValue) -> {
        // Метод для поиска в базе данных и обновления результатов на экране
        // newValue - новое значение текстового поля
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (
                ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=tempdb1;user=sa;password=1111", "sa", "1111")) {
            int row = 0;
            // Создаем запрос к базе данных
            String query = "SELECT*FROM Product WHERE Title LIKE ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + newValue + "%");
            // Выполняем запрос и получаем результаты
            ResultSet rs1 = pstmt.executeQuery();
            gridPane1.getChildren().clear();
            // Очищаем старые результаты поиска
            // Обрабатываем результаты поиска и отображаем их на экране
            while (rs1.next()) {
                ImageView imageView1 = new ImageView();

                String s=rs1.getString("Image");
                File file = new File(rs1.getString("Image"));
                if ( file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    imageView1.setFitWidth(100);
                    imageView1.setFitHeight(130);
                    imageView1.setImage(image);

                } else {
                    Image defaultImage = new Image(getClass().getResourceAsStream("/fxml/picture.png"));
                    imageView1.setFitWidth(100);
                    imageView1.setFitHeight(130);
                    imageView1.setImage(defaultImage);
                }
                String s3 = rs1.getString("Title");
                int s4 = rs1.getInt("MinCostForAgent");
                String s5 = "Артикул: "+rs1.getString("ArticleNumber");
                String w5 = "Стоимость:" + " " + s4;
                StringBuilder stringBuilder = new StringBuilder(s3);
                Label nameValueLabel = new Label();
                nameValueLabel.setText(s3);
                Label articul = new Label();
                articul.setText(s5);
                Label money = new Label();
                money.setText(w5);

                VBox vBox = new VBox(20, money);

                Label label1 = new Label();
                label1.setText(s3);
                VBox hbox1 = new VBox(20, nameValueLabel, articul);
                FlowPane hbox = new FlowPane(30,50, imageView1,hbox1, vBox);
                hbox.setStyle("-fx-border-color: black;");
                gridPane1.add(hbox, 1, row);

                row++;
                // добавьте элемент в ячейку таблицы
            }
            pstmt.close();
            rs1.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    });ComboBox<String> save1=new ComboBox<String>();
    save1.getItems().add("По убыванию названия");
    save1.getItems().add("По возрастанию названия");
    save1.setValue("Сортировка");
    ComboBox<String> filtr=new ComboBox<String>();
    filtr.getItems().add("По цене");
    filtr.getItems().add("По количеству");
    filtr.setValue("Фильтрация");
    save1.setOnAction(e -> {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (
                ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        if (save1.getValue().equals("По убыванию названия")) {
            try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=tempdb1;user=sa;password=1111", "sa", "1111");
                 PreparedStatement stmt1 = connection1.prepareStatement("SELECT *FROM Product ORDER BY Title DESC")) {
                ResultSet rs1 = stmt1.executeQuery();
                int row = 0;
                ImageView imageView = new ImageView();
                int count=0;
                gridPane1.getChildren().clear();
                while (rs1.next()) {
                    ImageView imageView1 = new ImageView();
                    String s=rs1.getString("Image");
                    File file = new File(rs1.getString("Image"));
                    if ( file.exists()) {
                        Image image = new Image(file.toURI().toString());
                        imageView1.setFitWidth(100);
                        imageView1.setFitHeight(130);
                        imageView1.setImage(image);
                        count++;

                    } else {
                        Image defaultImage = new Image(getClass().getResourceAsStream("/fxml/picture.png"));
                        imageView1.setFitWidth(100);
                        imageView1.setFitHeight(130);
                        imageView1.setImage(defaultImage);
                    }
                    // Добавление элемента ImageView на сцену
                    String s3 = rs1.getString("Title");
                    int s4 = rs1.getInt("MinCostForAgent");
                    String s5 = "Артикул: "+rs1.getString("ArticleNumber");
                    String w5 = "Стоимость:" + " " + s4;
                    StringBuilder stringBuilder = new StringBuilder(s3);
                    Label nameValueLabel = new Label();
                    nameValueLabel.setText(s3);
                    Label articul = new Label();
                    articul.setText(s5);
                    Label money = new Label();
                    money.setText(w5);

                    VBox vBox = new VBox(20, money);

                    Label label1 = new Label();
                    label1.setText(s3);
                    VBox hbox1 = new VBox(20, nameValueLabel, articul);
                    FlowPane hbox = new FlowPane(30,50, imageView1,hbox1, vBox);
                    hbox.setStyle("-fx-border-color: black;");
                    gridPane1.add(hbox, 1, row);
                    row++;
                    // добавьте элемент в ячейку таблицы
                }
                stmt1.close();
                rs1.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } if (save1.getValue().equals("По возрастанию названия")) {
            try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=tempdb1;user=sa;password=1111", "sa", "1111");
                 PreparedStatement stmt1 = connection1.prepareStatement("SELECT *FROM Product ORDER BY Title ASC")) {
                ResultSet rs1 = stmt1.executeQuery();
                int row = 0;
                ImageView imageView = new ImageView();
                int count=0;
                gridPane1.getChildren().clear();
                while (rs1.next()) {
                    ImageView imageView1 = new ImageView();
                    String s=rs1.getString("Image");
                    File file = new File(rs1.getString("Image"));
                    if ( file.exists()) {
                        Image image = new Image(file.toURI().toString());
                        imageView1.setFitWidth(100);
                        imageView1.setFitHeight(130);
                        imageView1.setImage(image);
                        count++;

                    } else {
                        Image defaultImage = new Image(getClass().getResourceAsStream("/fxml/picture.png"));
                        imageView1.setFitWidth(100);
                        imageView1.setFitHeight(130);
                        imageView1.setImage(defaultImage);
                    }
                    // Добавление элемента ImageView на сцену
                    String s3 = rs1.getString("Title");
                    int s4 = rs1.getInt("MinCostForAgent");
                    String s5 = "Артикул: "+rs1.getString("ArticleNumber");
                    String w5 = "Стоимость:" + " " + s4;
                    StringBuilder stringBuilder = new StringBuilder(s3);
                    Label nameValueLabel = new Label();
                    nameValueLabel.setText(s3);
                    Label articul = new Label();
                    articul.setText(s5);
                    Label money = new Label();
                    money.setText(w5);

                    VBox vBox = new VBox(20, money);

                    Label label1 = new Label();
                    label1.setText(s3);
                    VBox hbox1 = new VBox(20, nameValueLabel, articul);
                    FlowPane hbox = new FlowPane(30,50, imageView1,hbox1, vBox);
                    hbox.setStyle("-fx-border-color: black;");
                    gridPane1.add(hbox, 1, row);
                    row++;
                    // добавьте элемент в ячейку таблицы
                }
                stmt1.close();
                rs1.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    });
    try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    } catch (
            ClassNotFoundException e) {
        e.printStackTrace();
    }

    try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=tempdb1;user=sa;password=1111", "sa", "1111");
         PreparedStatement stmt1 = connection1.prepareStatement("SELECT *FROM Product")) {
        ResultSet rs1 = stmt1.executeQuery();
        int row = 0;
        gridPane1.getChildren().clear();
        ImageView imageView = new ImageView();
        int count=0;

        while (rs1.next()) {
            ImageView imageView1 = new ImageView();

            String s=rs1.getString("Image");
            File file = new File(rs1.getString("Image"));
            if ( file.exists()) {
                Image image = new Image(file.toURI().toString());
                imageView1.setFitWidth(100);
                imageView1.setFitHeight(130);
                imageView1.setImage(image);
                count++;

            } else {
                Image defaultImage = new Image(getClass().getResourceAsStream("/fxml/picture.png"));
                imageView1.setFitWidth(100);
                imageView1.setFitHeight(130);
                imageView1.setImage(defaultImage);
        }
            // Добавление элемента ImageView на сцену
            String s3 = rs1.getString("Title");
            int s4 = rs1.getInt("MinCostForAgent");
            String s5 = "Артикул: "+rs1.getString("ArticleNumber");
            String w5 = "Стоимость:" + " " + s4;
            StringBuilder stringBuilder = new StringBuilder(s3);
            Label nameValueLabel = new Label();
            nameValueLabel.setText(s3);
            Label articul = new Label();
            articul.setText(s5);
            Label money = new Label();
            money.setText(w5);

            VBox vBox = new VBox(20, money);

            Label label1 = new Label();
            label1.setText(s3);
            VBox hbox1 = new VBox(20, nameValueLabel, articul);
            FlowPane hbox = new FlowPane(30,50, imageView1,hbox1, vBox);
            hbox.setStyle("-fx-border-color: black;");
            gridPane1.add(hbox, 1, row);

            row++;
            // добавьте элемент в ячейку таблицы
        }
        stmt1.close();
        rs1.close();

    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    button.setOnAction(event -> {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/sample.fxml"));
            fxmlLoader.load();
            Parent parent = fxmlLoader.getRoot();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(parent));
            stage1.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    });

    ubyv.setOnAction(event -> {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/gridpane.fxml"));
            fxmlLoader.load();
            Parent parent = fxmlLoader.getRoot();
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(parent));
            stage1.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage2 = (Stage) ubyv.getScene().getWindow();
        stage2.close();
    });

    TextField name=new TextField();
    Label polzname=new Label();
    name.setPromptText("имя товара");
    TextField price=new TextField();
    price.setPromptText("тип продукта");
    TextField articul=new TextField();
    articul.setPromptText("артикул товара");
    TextField put=new TextField();
    put.setPromptText("путь к фото");
    TextField description=new TextField();
    description.setPromptText("описание");
    TextField ProductionPersonCount=new TextField();
    ProductionPersonCount.setPromptText("количество человек");
    TextField ProductionWorkshopNumber=new TextField();
    ProductionWorkshopNumber.setPromptText(" номер цеха");
    TextField MinCostForAgent=new TextField();
    MinCostForAgent.setPromptText("  минимальная стоимость агента");
    Button dobav=new Button("добавить данные");
    Button delete=new Button("удалить данные");
    Button updateprice=new Button("обновить цену");
    Button updatetovar=new Button("обновить имя товара");
    Button updatear=new Button("обновить артикул");
    Button admin=new Button("Администратирование");
    TextField kolichestvo=new TextField();
    kolichestvo.setPromptText("количество товара");
admin.setOnAction(event -> {
    try {
        String fxmlFile = "/fxml/admin.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        Stage stage1=new Stage();
        stage1.setTitle("Отправка почты");
        stage1.setScene(new Scene(root));
        stage1.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
});
    try {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    } catch (
            ClassNotFoundException e) {
        e.printStackTrace();
    }
    dobav.setOnAction(event -> {
        String namet = name.getText();
        String ar = articul.getText();
        String photo = put.getText();
        File file=new File(photo);
        int quantity = Integer.parseInt(kolichestvo.getText());String s=
               " INSERT INTO [dbo].[Product] ([Title] ,[ProductTypeID] ,[ArticleNumber] ,[Description] ,[Image],[ProductionPersonCount] ,[ProductionWorkshopNumber]   ,[MinCostForAgent]) VALUES ( ?, ?, ?,  ?,  ?, ?,   ?,  ?)";
        try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=tempdb1", "sa", "1111");
             InputStream is = new FileInputStream(file);
             PreparedStatement preparedStatement = connection1.prepareStatement(s)){
            preparedStatement.setString(1, namet);
            preparedStatement.setString(2, price.getText());
            preparedStatement.setString(3, ar);
            preparedStatement.setString(4, description.getText());
            preparedStatement.setString(5, put.getText());
            preparedStatement.setString(6, ProductionPersonCount.getText());
            preparedStatement.setString(7, ProductionWorkshopNumber.getText());
            preparedStatement.setString(8, MinCostForAgent.getText());
            preparedStatement.execute();
            is.close();
            connection1.close();
            preparedStatement.close();
        }
        catch (SQLException  | IOException e) {
            System.out.println(e);
        }
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (
                ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=tempdb1;user=sa;password=1111", "sa", "1111");
             PreparedStatement stmt1 = connection1.prepareStatement("SELECT *FROM Product")) {
            ResultSet rs1 = stmt1.executeQuery();
            int row = 0;
            gridPane1.getChildren().clear();
            ImageView imageView = new ImageView();
            int count=0;

            while (rs1.next()) {
                ImageView imageView1 = new ImageView();

                String s1=rs1.getString("Image");
                File file1 = new File(rs1.getString("Image"));
                if ( file1.exists()) {
                    Image image = new Image(file1.toURI().toString());
                    imageView1.setFitWidth(100);
                    imageView1.setFitHeight(130);
                    imageView1.setImage(image);
                    count++;

                } else {
                    Image defaultImage = new Image(getClass().getResourceAsStream("/fxml/picture.png"));
                    imageView1.setFitWidth(100);
                    imageView1.setFitHeight(130);
                    imageView1.setImage(defaultImage);
                }
                // Добавление элемента ImageView на сцену
                String s3 = rs1.getString("Title");
                int s4 = rs1.getInt("MinCostForAgent");
                String s5 = "Артикул: "+rs1.getString("ArticleNumber");
                String w5 = "Стоимость:" + " " + s4;
                StringBuilder stringBuilder = new StringBuilder(s3);
                Label nameValueLabel = new Label();
                nameValueLabel.setText(s3);
                Label articul1 = new Label();
                articul1.setText(s5);
                Label money = new Label();
                money.setText(w5);

                VBox vBox = new VBox(20, money);

                Label label1 = new Label();
                label1.setText(s3);
                VBox hbox1 = new VBox(20, nameValueLabel, articul1);
                FlowPane hbox = new FlowPane(30,50, imageView1,hbox1, vBox);
                hbox.setStyle("-fx-border-color: black;");
                gridPane1.add(hbox, 1, row);

                row++;
                // добавьте элемент в ячейку таблицы
            }
            stmt1.close();
            rs1.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    });
    delete.setOnAction(event -> {
        String namet = name.getText();
        String s= "Delete FROM Product where Title=?";
            try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=tempdb1", "sa", "1111");
             PreparedStatement preparedStatement = connection1.prepareStatement(s)){
            preparedStatement.setString(1, namet);
            preparedStatement.executeUpdate();
            connection1.close();
            preparedStatement.close();
        }
        catch (SQLException e ) {
            System.out.println(e);
        }
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (
                ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=tempdb1;user=sa;password=1111", "sa", "1111")) {
            int row = 0;
            // Создаем запрос к базе данных
            String query = "SELECT*FROM Product";
            PreparedStatement pstmt = conn.prepareStatement(query);
            // Выполняем запрос и получаем результаты
            ResultSet rs1 = pstmt.executeQuery();
            gridPane1.getChildren().clear();
            // Очищаем старые результаты поиска
            // Обрабатываем результаты поиска и отображаем их на экране
            while (rs1.next()) {
                ImageView imageView1 = new ImageView();
                File file = new File(rs1.getString("Image"));
                if ( file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    imageView1.setFitWidth(100);
                    imageView1.setFitHeight(130);
                    imageView1.setImage(image);

                } else {
                    Image defaultImage = new Image(getClass().getResourceAsStream("/fxml/picture.png"));
                    imageView1.setFitWidth(100);
                    imageView1.setFitHeight(130);
                    imageView1.setImage(defaultImage);
                }
                String s3 = rs1.getString("Title");
                int s4 = rs1.getInt("MinCostForAgent");
                String s5 = "Артикул: "+rs1.getString("ArticleNumber");
                String w5 = "Стоимость:" + " " + s4;
                StringBuilder stringBuilder = new StringBuilder(s3);
                Label nameValueLabel = new Label();
                nameValueLabel.setText(s3);
                Label articul1 = new Label();
                articul1.setText(s5);
                Label money = new Label();
                money.setText(w5);

                VBox vBox = new VBox(20, money);

                Label label1 = new Label();
                label1.setText(s3);
                VBox hbox1 = new VBox(20, nameValueLabel, articul1);
                FlowPane hbox = new FlowPane(30,50, imageView1,hbox1, vBox);
                hbox.setStyle("-fx-border-color: black;");
                gridPane1.add(hbox, 1, row);

                row++;
                // добавьте элемент в ячейку таблицы
            }
            pstmt.close();
            rs1.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    });


    updateprice.setOnAction(event -> {
        String namet = name.getText();
        double pr = Double.parseDouble(price.getText());
        String s= "UPDATE  Product SET MinCostForAgent=? where Title=?";
        try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=tempdb1", "sa", "1111");
             PreparedStatement preparedStatement = connection1.prepareStatement(s)){
            preparedStatement.setDouble(1, pr);
            preparedStatement.setString(2, namet);
            preparedStatement.executeUpdate();
            connection1.close();
            preparedStatement.close();
        }
        catch (SQLException e ) {
            System.out.println(e);
        }
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (
                ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=tempdb1;user=sa;password=1111", "sa", "1111")) {
            int row = 0;
            // Создаем запрос к базе данных
            String query = "SELECT*FROM Product";
            PreparedStatement pstmt = conn.prepareStatement(query);
            // Выполняем запрос и получаем результаты
            ResultSet rs1 = pstmt.executeQuery();
            gridPane1.getChildren().clear();
            // Очищаем старые результаты поиска
            // Обрабатываем результаты поиска и отображаем их на экране
            while (rs1.next()) {
                ImageView imageView1 = new ImageView();

                File file = new File(rs1.getString("Image"));
                if ( file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    imageView1.setFitWidth(100);
                    imageView1.setFitHeight(130);
                    imageView1.setImage(image);

                } else {
                    Image defaultImage = new Image(getClass().getResourceAsStream("/fxml/picture.png"));
                    imageView1.setFitWidth(100);
                    imageView1.setFitHeight(130);
                    imageView1.setImage(defaultImage);
                }
                String s3 = rs1.getString("Title");
                int s4 = rs1.getInt("MinCostForAgent");
                String s5 = "Артикул: "+rs1.getString("ArticleNumber");
                String w5 = "Стоимость:" + " " + s4;
                StringBuilder stringBuilder = new StringBuilder(s3);
                Label nameValueLabel = new Label();
                nameValueLabel.setText(s3);
                Label articul1 = new Label();
                articul1.setText(s5);
                Label money = new Label();
                money.setText(w5);

                VBox vBox = new VBox(20, money);

                Label label1 = new Label();
                label1.setText(s3);
                VBox hbox1 = new VBox(20, nameValueLabel, articul1);
                FlowPane hbox = new FlowPane(30,50, imageView1,hbox1, vBox);
                hbox.setStyle("-fx-border-color: black;");
                gridPane1.add(hbox, 1, row);

                row++;
                // добавьте элемент в ячейку таблицы
            }
            pstmt.close();
            rs1.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    });
    updatear.setOnAction(event -> {
        String namet = name.getText();
        String ar = articul.getText();
        String s= "UPDATE  Product SET ArticleNumber=? where Title=?";
        try (Connection connection1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=tempdb1", "sa", "1111");
             PreparedStatement preparedStatement = connection1.prepareStatement(s)){
            preparedStatement.setString(1, ar);
            preparedStatement.setString(2, namet);
            preparedStatement.executeUpdate();
            connection1.close();
            preparedStatement.close();
        }
        catch (SQLException e ) {
            System.out.println(e);
        }
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (
                ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=tempdb1;user=sa;password=1111", "sa", "1111")) {
            int row = 0;
            // Создаем запрос к базе данных
            String query = "SELECT*FROM Product";
            PreparedStatement pstmt = conn.prepareStatement(query);
            // Выполняем запрос и получаем результаты
            ResultSet rs1 = pstmt.executeQuery();
            gridPane1.getChildren().clear();
            // Очищаем старые результаты поиска
            // Обрабатываем результаты поиска и отображаем их на экране
            while (rs1.next()) {
                ImageView imageView1 = new ImageView();

                File file = new File(rs1.getString("Image"));
                if ( file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    imageView1.setFitWidth(100);
                    imageView1.setFitHeight(130);
                    imageView1.setImage(image);

                } else {
                    Image defaultImage = new Image(getClass().getResourceAsStream("/fxml/picture.png"));
                    imageView1.setFitWidth(100);
                    imageView1.setFitHeight(130);
                    imageView1.setImage(defaultImage);
                }
                String s3 = rs1.getString("Title");
                int s4 = rs1.getInt("MinCostForAgent");
                String s5 = "Артикул: "+rs1.getString("ArticleNumber");
                String w5 = "Стоимость:" + " " + s4;
                StringBuilder stringBuilder = new StringBuilder(s3);
                Label nameValueLabel = new Label();
                nameValueLabel.setText(s3);
                Label articul1 = new Label();
                articul1.setText(s5);
                Label money = new Label();
                money.setText(w5);

                VBox vBox = new VBox(20, money);

                Label label1 = new Label();
                label1.setText(s3);
                VBox hbox1 = new VBox(20, nameValueLabel, articul1);
                FlowPane hbox = new FlowPane(30,50, imageView1,hbox1, vBox);
                hbox.setStyle("-fx-border-color: black;");
                gridPane1.add(hbox, 1, row);

                row++;
                // добавьте элемент в ячейку таблицы
            }
            pstmt.close();
            rs1.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    });


    Label label=new Label("Имя пользователя: ");
    VBox vBox =new VBox(30,name,price,articul,put,kolichestvo,description,ProductionPersonCount,ProductionWorkshopNumber,MinCostForAgent,dobav,delete,updateprice,updatear);
    StackPane.setAlignment(vBox, Pos.TOP_RIGHT);
    StackPane.setMargin(vBox, new Insets(150, 50, 600, 500));
    HBox hBox = new HBox(50, textField,save1,filtr);
    StackPane.setAlignment(hBox, Pos.TOP_RIGHT);
    StackPane.setMargin(hBox, new Insets(80, 300, 600, 40));
    StackPane.setAlignment(gridPane1, Pos.TOP_RIGHT);
    StackPane.setMargin(gridPane1, new Insets(110, 300, 500, 10));
    StackPane.setAlignment(polzname, Pos.TOP_LEFT);
    StackPane.setMargin(polzname, new Insets(20, 10, 500, 650));
    StackPane.setMargin(label, new Insets(20, 10, 500, 530));
    StackPane.setAlignment(label, Pos.TOP_LEFT);
    StackPane root = new StackPane(gridPane1, hBox,vBox,polzname,label);
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setContent(root);
    scrollPane.setPrefSize(300, 300); // установка начальных размеров
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // установка политики прокрутки вертикальной полосы
    scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // установка политики прокрутки горизонтальной полосы
    scrollPane.setVvalue(0.5); // установка начального значения полосы прокрутки по вертикали
    scrollPane.setHvalue(0.5); // установка начального значения полосы прокрутки по горизонтали
    scrollPane.setPannable(true); // установка возможности прокрутки мышью
    scrollPane.setFitToWidth(true);


    Scene scene = new Scene(scrollPane, 800, 600);
    stage.setScene(scene);
    stage.setTitle("TableView in JavaFX");
    stage.show();
}
    public static void main(String[] args) {
        launch(args);
    }

}