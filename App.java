import javafx.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import javafx.application.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.*;

public class App extends Application {
    Stage window;
    Scene scene1, scene2, scene3, scene4;
    String fn, ln, p, h, d, t, id1;

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        // scene1

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        Label l1 = new Label("Welcome");
        Label l = new Label();
        l1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(l1, 0, 0, 2, 1);
        grid.add(l, 1, 5);
        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);
        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button btn = new Button("Sign in");
        grid.add(btn, 1, 4);
        Button btn2 = new Button("Register");
        grid.add(btn2, 0, 4);
        Button btn6 = new Button("Exit");
        grid.add(btn6, 2, 4);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ex) {
                if (userTextField.getText().equals("") || pwBox.getText().equals("")) {
                    l.setText("Invalid User name and Password");
                    l.setTextFill(Color.RED);
                    System.out.println("Invalid User name and Password");
                } else {
                    try {
                        Class c1 = Class.forName("com.mysql.cj.jdbc.Driver");
                        String user = "root";
                        String pass = "";
                        final String db_url = "jdbc:mysql://localhost/patient";
                        Connection con = DriverManager.getConnection(db_url, user, pass);
                        Statement stmt = (Statement) con.createStatement();
                        ResultSet rs = stmt.executeQuery("select * from login;");
                        while (rs.next()) {

                            String eD;
                            String dD;
                            String eP, dP;
                            eD = userTextField.getText();
                            dD = rs.getString(1);
                            eP = pwBox.getText();
                            dP = rs.getString(2);

                            if (eD.equals(dD) && eP.equals(dP)) {
                                btn.setOnAction(e -> window.setScene(scene4));
                            } else {
                                userTextField.clear();
                                pwBox.clear();
                                l.setText("Wrong username or password");
                                l.setTextFill(Color.RED);
                                System.out.println("Wrong username or password");
                            }
                        }
                        con.close();

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }

        });
        btn2.setOnAction(e -> window.setScene(scene2));

        btn6.setOnAction(e -> window.close());
        scene1 = new Scene(grid, 370, 300);
        // scene2
        Label l2 = new Label("Register");
        l2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        GridPane grid1 = new GridPane();
        grid1.setAlignment(Pos.CENTER);
        grid1.setVgap(10);
        grid1.setPadding(new Insets(25, 25, 25, 25));
        grid1.add(l2, 0, 0, 2, 1);
        Label userName1 = new Label("User Name:");
        grid1.add(userName1, 0, 1);

        TextField userTextField1 = new TextField();
        grid1.add(userTextField1, 1, 1);

        Label pw1 = new Label("Password:");
        grid1.add(pw1, 0, 2);

        PasswordField pwBox1 = new PasswordField();
        grid1.add(pwBox1, 1, 2);
        Label pw2 = new Label("Confirm Password:");
        grid1.add(pw2, 0, 3);

        PasswordField pwBox2 = new PasswordField();
        grid1.add(pwBox2, 1, 3);
        Button btn1 = new Button("Submit");
        grid1.add(btn1, 3, 5);
        Label lw = new Label();
        grid1.add(lw, 1, 6);
        btn1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ex) {
                if (userTextField1.getText().equals("") && pwBox1.getText().equals("")) {
                    lw.setText("Invalid User name and Password");
                    lw.setTextFill(Color.RED);
                    System.out.println("Invalid user name and password");
                } else {
                    try {
                        Class c1 = Class.forName("com.mysql.cj.jdbc.Driver");
                        String user = "root";
                        String pass = "";
                        final String db_url = "jdbc:mysql://localhost/patient";
                        Connection con = DriverManager.getConnection(db_url, user, pass);
                        Statement stmt = (Statement) con.createStatement();

                        final String name, p, cp;
                        name = userTextField1.getText();
                        p = pwBox1.getText();
                        cp = pwBox2.getText();
                        String sql = "INSERT INTO login(" + "user," + "pass)" + "VALUES(?,?)";
                        PreparedStatement preparedStatement = con.prepareStatement(sql);
                        if (cp.equals(p)) {

                            preparedStatement.setString(1, name);
                            preparedStatement.setString(2, p);
                            preparedStatement.execute();
                            btn1.setOnAction(e -> window.setScene(scene3));
                        } else {
                        }
                        con.close();

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        TableView<Person> table = new TableView<Person>();
        final ObservableList<Person> data = FXCollections.observableArrayList(new Person(id1, fn, ln, p, h, d, t),
                new Person("1", "Aman", "Raj", "7061144522", "fracture", "2022-12-03", "13:00"));
        Button btn3 = new Button("Back");
        grid1.add(btn3, 0, 5);
        btn3.setOnAction(e -> window.setScene(scene1));
        scene2 = new Scene(grid1, 400, 300);
        // scene3
        GridPane grid2 = new GridPane();
        grid2.setAlignment(Pos.CENTER);
        grid2.setVgap(10);
        grid2.setPadding(new Insets(25, 25, 25, 25));
        Label l3 = new Label("Successfully registered!!");
        l3.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        grid2.add(l3, 0, 0);
        Button btn4 = new Button("Login");
        btn4.setOnAction(e -> window.setScene(scene1));
        grid2.add(btn4, 5, 1);
        scene3 = new Scene(grid2, 300, 70);
        // scene4
        GridPane grid3 = new GridPane();
        grid3.setAlignment(Pos.CENTER);
        // grid3.setVgap(5);
        // grid3.setPadding(new Insets(10, 10, 10, 10));
        Label l4 = new Label("Patient details");
        l4.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        grid3.add(l4, 0, 0);
        table.setEditable(true);
        TableColumn id = new TableColumn("ID");
        id.setMinWidth(100);
        id.setCellValueFactory(
                new PropertyValueFactory<Person, String>("id"));
        id.setCellFactory(TextFieldTableCell.forTableColumn());
        id.setOnEditCommit(
                new EventHandler<CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(CellEditEvent<Person, String> t) {
                        ((Person) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setId(t.getNewValue());
                    }
                });
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("firstName"));
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(CellEditEvent<Person, String> t) {
                        ((Person) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setFirstName(t.getNewValue());
                    }
                });
        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Person, String>("lastName"));
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setOnEditCommit(
                new EventHandler<CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(CellEditEvent<Person, String> t) {
                        ((Person) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setLastName(t.getNewValue());
                    }
                });
        TableColumn phoneno = new TableColumn("Phone No.");
        phoneno.setMinWidth(200);
        phoneno.setCellValueFactory(
                new PropertyValueFactory<Person, String>("phone"));
        phoneno.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneno.setOnEditCommit(
                new EventHandler<CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(CellEditEvent<Person, String> t) {
                        ((Person) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setPhone(t.getNewValue());
                    }
                });
        TableColumn history = new TableColumn("History");
        history.setMinWidth(100);
        history.setCellValueFactory(
                new PropertyValueFactory<Person, String>("History"));
        history.setCellFactory(TextFieldTableCell.forTableColumn());
        history.setOnEditCommit(
                new EventHandler<CellEditEvent<Person, String>>() {
                    @Override
                    public void handle(CellEditEvent<Person, String> t) {
                        ((Person) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())).setHistory(t.getNewValue());
                    }
                });
        TableColumn date = new TableColumn("Date");
        date.setMinWidth(100);
        date.setCellValueFactory(new PropertyValueFactory<Person, String>("date"));
        date.setCellFactory(TextFieldTableCell.forTableColumn());
        date.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
            @Override
            public void handle(CellEditEvent<Person, String> t) {
                ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDate(t.getNewValue());
            }
        });
        TableColumn time = new TableColumn("Time");
        time.setMinWidth(100);
        time.setCellValueFactory(new PropertyValueFactory<Person, String>("time"));
        time.setCellFactory(TextFieldTableCell.forTableColumn());
        time.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
            @Override
            public void handle(CellEditEvent<Person, String> t) {
                ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTime(t.getNewValue());
            }
        });
        table.setItems(data);
        table.getColumns().addAll(id, firstNameCol, lastNameCol, phoneno, history, date, time);
        grid3.add(table, 0, 2);
        final TextField addid = new TextField();
        addid.setMaxWidth(time.getPrefWidth());
        addid.setPromptText("ID");
        grid3.add(addid, 5, 1);
        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("First Name");
        addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
        grid3.add(addFirstName, 5, 2);
        final TextField addLastName = new TextField();
        addLastName.setMaxWidth(lastNameCol.getPrefWidth());
        addLastName.setPromptText("Last Name");
        grid3.add(addLastName, 5, 3);
        final TextField addphone = new TextField();
        addphone.setMaxWidth(phoneno.getPrefWidth());
        addphone.setPromptText("Phone No.");
        grid3.add(addphone, 5, 4);
        final TextField addhistory = new TextField();
        addhistory.setMaxWidth(history.getPrefWidth());
        addhistory.setPromptText("History");
        grid3.add(addhistory, 6, 1);
        final TextField adddate = new TextField();
        adddate.setMaxWidth(date.getPrefWidth());
        adddate.setPromptText("Date");
        grid3.add(adddate, 6, 2);
        final TextField addtime = new TextField();
        addtime.setMaxWidth(time.getPrefWidth());
        addtime.setPromptText("time");
        grid3.add(addtime, 6, 3);

        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                fn = addFirstName.getText();
                ln = addLastName.getText();
                p = addphone.getText();
                h = addhistory.getText();
                d = adddate.getText();
                t = addtime.getText();
                id1 = addid.getText();
                data.add(new Person(id1,fn, ln, p, h, d, t));
                addFirstName.clear();
                addLastName.clear();
                addphone.clear();
                addhistory.clear();
                adddate.clear();
                addtime.clear();
                addid.clear();
                userTextField.clear();
                pwBox.clear();
                try {
                    Class c1 = Class.forName("com.mysql.cj.jdbc.Driver");
                    String user = "root";
                    String pass = "";
                    final String db_url = "jdbc:mysql://localhost/patient";
                    Connection con = DriverManager.getConnection(db_url, user, pass);
                    Statement stmt = (Statement) con.createStatement();
                    String sql = "INSERT INTO details(" + "id," + "fname," + "lname," + "phone," + "history," + "date,"
                            + "time)" + "VALUES(?,?,?,?,?,?,?)";
                    PreparedStatement preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1, id1);
                    preparedStatement.setString(2, fn);
                    preparedStatement.setString(3, ln);
                    preparedStatement.setString(4, p);
                    preparedStatement.setString(5, h);
                    preparedStatement.setString(6, d);
                    preparedStatement.setString(7, t);
                    preparedStatement.execute();
                    con.close();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        grid3.add(addButton, 0, 8);
        Button btn5 = new Button("Logout");
        btn5.setOnAction(e -> window.close());
        grid3.add(btn5, 2, 8);
        Button del = new Button("Bill");
        grid3.add(del,1,10);
        del.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    HashMap hm = new HashMap<>();
                    Class c1 = Class.forName("com.mysql.cj.jdbc.Driver");
                    String user = "root";
                    String pass = "";
                    final String db_url = "jdbc:mysql://localhost/patient";
                    Connection con = DriverManager.getConnection(db_url, user, pass);
                    Statement stmt = (Statement) con.createStatement();
                    ResultSet resultSet = stmt.executeQuery("select b.id,d.fname,d.lname,m.med_name from bill b,details d, medicine m where b.c_id=d.id and b.m_id=m.id;");
                      while (resultSet.next()) {
                        
                   System.out.println(resultSet.getString(1)+" "+ resultSet.getString(2)+" "+ resultSet.getString(3)+" "+ resultSet.getString(4));
            }
               con.close();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        Button btn0 = new Button("Delete");
        grid3.add(btn0, 1, 9);
        btn0.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                id1 = addid.getText();
                addid.clear();
                try {
                    Class c1 = Class.forName("com.mysql.cj.jdbc.Driver");
                    String user = "root";
                    String pass = "";
                    final String db_url = "jdbc:mysql://localhost/patient";
                    Connection con = DriverManager.getConnection(db_url, user, pass);
                    Statement stmt = (Statement) con.createStatement();
                    PreparedStatement ps = con.prepareStatement("DELETE FROM details WHERE id = ?");
                    ps.setString(1, id1);
                    ps.executeUpdate();
                    con.close();
                    if(id1.equals(1))
                    {
                        ((List<App.Person>) id).clear();
                        ((List<App.Person>) firstNameCol).clear();
                        ((List<App.Person>) lastNameCol).clear();
                        ((List<App.Person>) phoneno).clear(); 
                        ((List<App.Person>) history).clear(); 
                        ((List<App.Person>) date).clear(); 
                        ((List<App.Person>) time).clear(); 
                    }

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        scene4 = new Scene(grid3, 1000, 1000);
        window.setScene(scene1);
        window.setTitle("Hospital Management System");
        window.show();
    }

    public static class Person {

        private final SimpleStringProperty firstName;
        private final SimpleStringProperty lastName;
        private final SimpleStringProperty phone;
        private final SimpleStringProperty history;
        private final SimpleStringProperty date;
        private final SimpleStringProperty time;
        private final SimpleStringProperty id;

        private Person(String id, String fName, String lName, String phone, String history, String date, String time) {
            this.firstName = new SimpleStringProperty(fName);
            this.lastName = new SimpleStringProperty(lName);
            this.phone = new SimpleStringProperty(phone);
            this.history = new SimpleStringProperty(history);
            this.date = new SimpleStringProperty(date);
            this.time = new SimpleStringProperty(time);
            this.id = new SimpleStringProperty(id);
        }

        public String getFirstName() {
            return firstName.get();
        }

        public void setFirstName(String fName) {
            firstName.set(fName);
        }

        public String getLastName() {
            return lastName.get();
        }

        public void setLastName(String fName) {
            lastName.set(fName);
        }

        public String getPhone() {
            return phone.get();
        }

        public void setPhone(String fname) {
            phone.set(fname);

        }

        public String getHistory() {
            return history.get();
        }

        public void setHistory(String fname) {
            history.set(fname);

        }

        public String getDate() {
            return date.get();
        }

        public void setDate(String fname) {
            date.set(fname);

        }

        public String getTime() {
            return time.get();
        }

        public void setTime(String fname) {
            time.set(fname);

        }

        public String getId() {
            return id.get();
        }

        public void setId(String fname) {
            id.set(fname);

        }
    }

}