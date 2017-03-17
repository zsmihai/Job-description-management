package Interface;

import Domain.*;
import Utils.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Predicate;

/**
 * Created by Utilizator on 09-Jan-17.
 */
public class RapoarteController {

    private RaportModel raportModel;

    @FXML
    private TableView<Post> postTable;

    @FXML
    private TableView<Sarcina> sarcinaTable;

    @FXML
    private TableColumn<Post, Integer> postIdColumn;

    @FXML
    private TableColumn<Post, String> postNumeColumn;

    @FXML
    private TableColumn<Post, String> postTipColumn;

    @FXML
    private TableColumn<Post, Integer> postNrColumn;

    @FXML
    private TableColumn<Sarcina, Integer> sarcinaIdColumn;

    @FXML
    private TableColumn<Sarcina, String> sarcinaDescriptionColumn;

    @FXML
    private TableColumn<Sarcina, Integer> sarcinaNrColumn;

    @FXML
    private ChoiceBox<String> tipChoice;

    @FXML
    private ChoiceBox<String> nrChoice;

    @FXML
    private ChoiceBox<String> sortareColoanaChoice;

    @FXML
    private ChoiceBox<String> sortareOrderChoice;

    @FXML
    private ChoiceBox<String> filtrareColumnChoice;

    @FXML
    private ChoiceBox<String> filtrareTipChoice;

    @FXML
    private TextField nrTextField;

    @FXML
    private TextField filtrareTextBox;

    @FXML
    private CheckBox sortareCheckBox;

    @FXML
    private CheckBox filtrareCheckBox;

    @FXML
    private Button raportButton;

    private ObservableList<String> tipChoiceList;
    private ObservableList<String> nrChoiceList;
    private ObservableList<String> postSortareColoanaChoiceList;
    private ObservableList<String> sarcinaSortareColoanaChoiceList;
    private ObservableList<String> sortareColoanaChoiceList;
    private ObservableList<String> sortareOrderChoiceList;
    private ObservableList<String> filtrareColumnChoiceList;
    private ObservableList<String> postFiltrareColumnChoiceList;
    private ObservableList<String> sarcinaFiltrareColumnChoiceList;
    private ObservableList<String> filtrareTipChoiceList;
    private ObservableList<String> stringFiltrareTipChoiceList;
    private ObservableList<String> numberFiltrareTipChoiceList;



    public RapoarteController(RaportModel raportModel) {
        this.raportModel = raportModel;
    }

    public void init(){
        sarcinaTable.setItems(this.raportModel.getSarcinaList());
        postTable.setItems(this.raportModel.getPostList());
        sarcinaIdColumn.setCellValueFactory(new PropertyValueFactory<Sarcina, Integer>("ID"));
        sarcinaDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        sarcinaNrColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sarcina, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Sarcina, Integer> param) {
                return new ReadOnlyObjectWrapper<Integer>(raportModel.countPostsPerSarcina(param.getValue()));
            }
        });
        postNrColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Post, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Post, Integer> param) {
                return new ReadOnlyObjectWrapper<Integer>(raportModel.countSarcinaPerPost(param.getValue()));
            }
        });


        postIdColumn.setCellValueFactory(new PropertyValueFactory<Post, Integer>("ID"));
        postNumeColumn.setCellValueFactory(new PropertyValueFactory<Post, String>("name"));
        postTipColumn.setCellValueFactory(new PropertyValueFactory<Post, String>("tip"));

        postTable.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Post>() {
                    @Override
                    public void changed(ObservableValue<? extends Post> observable, Post oldValue, Post newValue) {
                        raportModel.setSarcinaListFromPost(newValue);
                        System.out.println("ceva");
                    }
                }
        );

        sarcinaTable.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Sarcina>() {
                    @Override
                    public void changed(ObservableValue<? extends Sarcina> observable, Sarcina oldValue, Sarcina newValue) {
                        raportModel.setPostListFromSarcina(newValue);
                        System.out.println("ceva");
                    }
                }
        );


        tipChoiceList = FXCollections.observableArrayList("Post", "Sarcina");
        nrChoiceList = FXCollections.observableArrayList("Toate", "Primele", "Ultimele");
        sarcinaSortareColoanaChoiceList = FXCollections.observableArrayList("ID", "Descriere", "Nr posturi");
        postSortareColoanaChoiceList = FXCollections.observableArrayList("ID", "Nume", "Tip", "Nr sarcini");
        sortareOrderChoiceList = FXCollections.observableArrayList("asc", "desc");
        sarcinaFiltrareColumnChoiceList = FXCollections.observableArrayList("ID", "Descriere", "Nr posturi");
        postFiltrareColumnChoiceList = FXCollections.observableArrayList("ID", "Nume", "Tip", "Nr sarcini");
        stringFiltrareTipChoiceList = FXCollections.observableArrayList("Egal cu", "Contine", "Incepe cu");
        numberFiltrareTipChoiceList = FXCollections.observableArrayList("Egal cu", "Mai mic decat", "Mai mare decat");

        filtrareTipChoiceList = FXCollections.observableArrayList(new ArrayList<String>());
        sortareColoanaChoiceList = FXCollections.observableArrayList(new ArrayList<String>());
        filtrareColumnChoiceList = FXCollections.observableArrayList(new ArrayList<String>());

        tipChoice.setItems(tipChoiceList);
        nrChoice.setItems(nrChoiceList);
        filtrareTipChoice.setItems(filtrareTipChoiceList);
        sortareColoanaChoice.setItems(sortareColoanaChoiceList);
        sortareOrderChoice.setItems(sortareOrderChoiceList);
        filtrareColumnChoice.setItems(filtrareColumnChoiceList);





        tipChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if( newValue == "Post" ){
                    filtrareColumnChoiceList.setAll(postFiltrareColumnChoiceList);
                    sortareColoanaChoiceList.setAll(postSortareColoanaChoiceList);
                }
                else
                    if(newValue == "Sarcina"){
                        filtrareColumnChoiceList.setAll(sarcinaFiltrareColumnChoiceList);
                        sortareColoanaChoiceList.setAll(sarcinaSortareColoanaChoiceList);
                    }
            }
        });


        filtrareColumnChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(newValue == "ID" || newValue =="Nr posturi" ||newValue == "Nr sarcini"){
                    filtrareTipChoiceList.setAll(numberFiltrareTipChoiceList);
                }
                else{
                    filtrareTipChoiceList.setAll(stringFiltrareTipChoiceList);
                }
            }
        });


        raportButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if( tipChoice.getSelectionModel().getSelectedItem()==( "Post") ) {

                    raportModel.setState(State.Post);
                    if (nrChoice.getSelectionModel().getSelectedItem()==("Primele")) {
                        raportModel.setPostDirection(Direction.TOP);
                        raportModel.setPostHowMany(HowMany.COUNT);
                        raportModel.setPostNumber(Double.valueOf(nrTextField.getText()));
                    } else if (nrChoice.getSelectionModel().getSelectedItem()==("Ultimele")) {
                        raportModel.setPostDirection(Direction.BOTTOM);
                        raportModel.setPostHowMany(HowMany.COUNT);
                        raportModel.setPostNumber(Double.valueOf(nrTextField.getText()));
                    } else
                        raportModel.setPostDirection(Direction.ALL);

                    if (sortareColoanaChoice.getSelectionModel().getSelectedItem()==("ID")) {
                        if (sortareOrderChoice.getSelectionModel().getSelectedItem()==("asc"))
                            raportModel.setPostComparator(new Comparator<Post>() {
                                @Override
                                public int compare(Post o1, Post o2) {
                                    return o1.getID().compareTo(o2.getID());
                                }
                            });
                        if (sortareOrderChoice.getSelectionModel().getSelectedItem()==("desc"))
                            raportModel.setPostComparator(new Comparator<Post>() {
                                @Override
                                public int compare(Post o1, Post o2) {
                                    return o2.getID().compareTo(o1.getID());
                                }
                            });
                    }
                    if (sortareColoanaChoice.getSelectionModel().getSelectedItem()==("Nume")) {
                        if (sortareOrderChoice.getSelectionModel().getSelectedItem()==("asc"))
                            raportModel.setPostComparator(new Comparator<Post>() {
                                @Override
                                public int compare(Post o1, Post o2) {
                                    return o1.getName().compareTo(o2.getName());
                                }
                            });
                        if (sortareOrderChoice.getSelectionModel().getSelectedItem()==("desc"))
                            raportModel.setPostComparator(new Comparator<Post>() {
                                @Override
                                public int compare(Post o1, Post o2) {
                                    return o2.getName().compareTo(o1.getName());
                                }
                            });
                    }
                    if (sortareColoanaChoice.getSelectionModel().getSelectedItem()==("Tip")) {
                        if (sortareOrderChoice.getSelectionModel().getSelectedItem()==("asc"))
                            raportModel.setPostComparator(new Comparator<Post>() {
                                @Override
                                public int compare(Post o1, Post o2) {
                                    return o1.getTip().compareTo(o2.getTip());
                                }
                            });
                        if (sortareOrderChoice.getSelectionModel().getSelectedItem()==("desc"))
                            raportModel.setPostComparator(new Comparator<Post>() {
                                @Override
                                public int compare(Post o1, Post o2) {
                                    return o2.getTip().compareTo(o1.getTip());
                                }
                            });
                    }
                    if (sortareColoanaChoice.getSelectionModel().getSelectedItem()==("Nr sarcini")) {
                        if (sortareOrderChoice.getSelectionModel().getSelectedItem()==("asc"))
                            raportModel.setPostComparator(new Comparator<Post>() {
                                @Override
                                public int compare(Post o1, Post o2) {
                                    return raportModel.countSarcinaPerPost(o1).compareTo(raportModel.countSarcinaPerPost(o2));
                                }
                            });
                        if (sortareOrderChoice.getSelectionModel().getSelectedItem()==("desc"))
                            raportModel.setPostComparator(new Comparator<Post>() {
                                @Override
                                public int compare(Post o1, Post o2) {
                                    return raportModel.countSarcinaPerPost(o2).compareTo(raportModel.countSarcinaPerPost(o1));
                                }
                            });
                    }


                    if (filtrareColumnChoice.getSelectionModel().getSelectedItem()==("ID")) {

                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()==("Egal cu")) {
                            raportModel.setPostFilter(new Predicate<Post>() {
                                @Override
                                public boolean test(Post post) {
                                    return post.getID().equals(Integer.parseInt(filtrareTextBox.getText()));
                                }
                            });
                        }
                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()==("Mai mic decat")) {
                            raportModel.setPostFilter(new Predicate<Post>() {
                                @Override
                                public boolean test(Post post) {
                                    return post.getID() < Integer.parseInt(filtrareTextBox.getText());
                                }
                            });
                        }
                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()==("Mai mare decat")) {
                            raportModel.setPostFilter(new Predicate<Post>() {
                                @Override
                                public boolean test(Post post) {
                                    return post.getID() > Integer.parseInt(filtrareTextBox.getText());
                                }
                            });
                        }

                    }
                    if (filtrareColumnChoice.getSelectionModel().getSelectedItem()==("Nr sarcini")) {

                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()==("Egal cu")) {
                            raportModel.setPostFilter(new Predicate<Post>() {
                                @Override
                                public boolean test(Post post) {
                                    return raportModel.countSarcinaPerPost(post).equals(Integer.parseInt(filtrareTextBox.getText()));
                                }
                            });
                        }
                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()==("Mai mic decat")) {
                            raportModel.setPostFilter(new Predicate<Post>() {
                                @Override
                                public boolean test(Post post) {
                                    return raportModel.countSarcinaPerPost(post) < Integer.parseInt(filtrareTextBox.getText());
                                }
                            });
                        }
                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()==("Mai mare decat")) {
                            raportModel.setPostFilter(new Predicate<Post>() {
                                @Override
                                public boolean test(Post post) {
                                    return raportModel.countSarcinaPerPost(post) > Integer.parseInt(filtrareTextBox.getText());
                                }
                            });
                        }

                    }

                    if (filtrareColumnChoice.getSelectionModel().getSelectedItem()==("Nr sarcini")) {

                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()==("Egal cu")) {
                            raportModel.setPostFilter(new Predicate<Post>() {
                                @Override
                                public boolean test(Post post) {
                                    return raportModel.countSarcinaPerPost(post).equals(Integer.parseInt(filtrareTextBox.getText()));
                                }
                            });
                        }
                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()==("Mai mic decat")) {
                            raportModel.setPostFilter(new Predicate<Post>() {
                                @Override
                                public boolean test(Post post) {
                                    return raportModel.countSarcinaPerPost(post) < Integer.parseInt(filtrareTextBox.getText());
                                }
                            });
                        }
                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()==("Mai mare decat")) {
                            raportModel.setPostFilter(new Predicate<Post>() {
                                @Override
                                public boolean test(Post post) {
                                    return raportModel.countSarcinaPerPost(post) > Integer.parseInt(filtrareTextBox.getText());
                                }
                            });
                        }
                    }

                    if (filtrareColumnChoice.getSelectionModel().getSelectedItem()==("Nume")) {

                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()==("Egal cu")) {
                            raportModel.setPostFilter(new Predicate<Post>() {
                                @Override
                                public boolean test(Post post) {
                                    return post.getName().equals(filtrareTextBox.getText());
                                }
                            });
                        }
                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()==("Contine")) {
                            raportModel.setPostFilter(new Predicate<Post>() {
                                @Override
                                public boolean test(Post post) {
                                    return post.getName().contains(filtrareTextBox.getText());
                                }
                            });
                        }
                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()==("Incepe cu")) {
                            raportModel.setPostFilter(new Predicate<Post>() {
                                @Override
                                public boolean test(Post post) {
                                    return post.getName().startsWith(filtrareTextBox.getText());
                                }
                            });
                        }
                    }
                    if (filtrareColumnChoice.getSelectionModel().getSelectedItem()==("Tip")) {

                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()==("Egal cu")) {
                            raportModel.setPostFilter(new Predicate<Post>() {
                                @Override
                                public boolean test(Post post) {
                                    return post.getTip().equals(filtrareTextBox.getText());
                                }
                            });
                        }
                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()==("Contine")) {
                            raportModel.setPostFilter(new Predicate<Post>() {
                                @Override
                                public boolean test(Post post) {
                                    return post.getTip().contains(filtrareTextBox.getText());
                                }
                            });
                        }
                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()==("Incepe cu")) {
                            raportModel.setPostFilter(new Predicate<Post>() {
                                @Override
                                public boolean test(Post post) {
                                    return post.getTip().startsWith(filtrareTextBox.getText());
                                }
                            });
                        }
                    }

                }
                else
                if( tipChoice.getSelectionModel().getSelectedItem()==("Sarcina")){

                    raportModel.setState(State.Sarcina);
                    if (nrChoice.getSelectionModel().getSelectedItem()==("Primele")) {
                        raportModel.setSarcinaDirection(Direction.TOP);
                        raportModel.setSarcinaHowMany(HowMany.COUNT);
                        raportModel.setSarcinaNumber(Double.valueOf(nrTextField.getText()));
                    } else if (nrChoice.getSelectionModel().getSelectedItem()==("Ultimele")) {
                        raportModel.setSarcinaDirection(Direction.BOTTOM);
                        raportModel.setSarcinaHowMany(HowMany.COUNT);
                        raportModel.setSarcinaNumber(Double.valueOf(nrTextField.getText()));
                    } else
                        raportModel.setSarcinaDirection(Direction.ALL);

                    if (sortareColoanaChoice.getSelectionModel().getSelectedItem()==("ID")) {
                        if (sortareOrderChoice.getSelectionModel().getSelectedItem()==("asc"))
                            raportModel.setSarcinaComparator(new Comparator<Sarcina>() {
                                @Override
                                public int compare(Sarcina o1, Sarcina o2) {
                                    return o1.getID().compareTo(o2.getID());
                                }
                            });
                        if (sortareOrderChoice.getSelectionModel().getSelectedItem()==("desc"))
                            raportModel.setSarcinaComparator(new Comparator<Sarcina>() {
                                @Override
                                public int compare(Sarcina o1, Sarcina o2) {
                                    return o2.getID().compareTo(o1.getID());
                                }
                            });
                    }
                    if (sortareColoanaChoice.getSelectionModel().getSelectedItem()==("Descriere")) {
                        if (sortareOrderChoice.getSelectionModel().getSelectedItem()==("asc"))
                            raportModel.setSarcinaComparator(new Comparator<Sarcina>() {
                                @Override
                                public int compare(Sarcina o1, Sarcina o2) {
                                    return o1.getDescription().compareTo(o2.getDescription());
                                }
                            });
                        if (sortareOrderChoice.getSelectionModel().getSelectedItem()==("desc"))
                            raportModel.setSarcinaComparator(new Comparator<Sarcina>() {
                                @Override
                                public int compare(Sarcina o1, Sarcina o2) {
                                    return o2.getDescription().compareTo(o1.getDescription());
                                }
                            });
                    }


                    if (sortareColoanaChoice.getSelectionModel().getSelectedItem()==("Nr posturi")) {
                        if (sortareOrderChoice.getSelectionModel().getSelectedItem()==("asc"))
                            raportModel.setSarcinaComparator(new Comparator<Sarcina>() {
                                @Override
                                public int compare(Sarcina o1, Sarcina o2) {
                                    return raportModel.countPostsPerSarcina(o1).compareTo(raportModel.countPostsPerSarcina(o2));
                                }
                            });
                        if (sortareOrderChoice.getSelectionModel().getSelectedItem()==("desc"))
                            raportModel.setSarcinaComparator(new Comparator<Sarcina>() {
                                @Override
                                public int compare(Sarcina o1, Sarcina o2) {
                                    return raportModel.countPostsPerSarcina(o2).compareTo(raportModel.countPostsPerSarcina(o1));
                                }
                            });
                    }


                    if (filtrareColumnChoice.getSelectionModel().getSelectedItem()==("ID")) {

                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()==("Egal cu")) {
                            raportModel.setSarcinaFilter(new Predicate<Sarcina>() {
                                @Override
                                public boolean test(Sarcina post) {
                                    return post.getID().equals(Integer.parseInt(filtrareTextBox.getText()));
                                }
                            });
                        }
                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()==("Mai mic decat")) {
                            raportModel.setSarcinaFilter(new Predicate<Sarcina>() {
                                @Override
                                public boolean test(Sarcina post) {
                                    return post.getID() < Integer.parseInt(filtrareTextBox.getText());
                                }
                            });
                        }
                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()==("Mai mare decat")) {
                            raportModel.setSarcinaFilter(new Predicate<Sarcina>() {
                                @Override
                                public boolean test(Sarcina post) {
                                    return post.getID() > Integer.parseInt(filtrareTextBox.getText());
                                }
                            });
                        }

                    }
                    if (filtrareColumnChoice.getSelectionModel().getSelectedItem()==("Nr posturi")) {

                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()==("Egal cu")) {
                            raportModel.setSarcinaFilter(new Predicate<Sarcina>() {
                                @Override
                                public boolean test(Sarcina post) {
                                    return raportModel.countPostsPerSarcina(post).equals(Integer.parseInt(filtrareTextBox.getText()));
                                }
                            });
                        }
                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()=="Mai mic decat") {
                            raportModel.setSarcinaFilter(new Predicate<Sarcina>() {
                                @Override
                                public boolean test(Sarcina post) {
                                    return raportModel.countPostsPerSarcina(post) < Integer.parseInt(filtrareTextBox.getText());
                                }
                            });
                        }
                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()=="Mai mare decat") {
                            raportModel.setSarcinaFilter(new Predicate<Sarcina>() {
                                @Override
                                public boolean test(Sarcina post) {
                                    return raportModel.countPostsPerSarcina(post) > Integer.parseInt(filtrareTextBox.getText());
                                }
                            });
                        }

                    }


                    if (filtrareColumnChoice.getSelectionModel().getSelectedItem()=="Descriere") {

                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()==("Egal cu")) {
                            raportModel.setSarcinaFilter(new Predicate<Sarcina>() {
                                @Override
                                public boolean test(Sarcina post) {
                                    return post.getDescription()==(filtrareTextBox.getText());
                                }
                            });
                        }
                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()=="Contine") {
                            raportModel.setSarcinaFilter(new Predicate<Sarcina>() {
                                @Override
                                public boolean test(Sarcina post) {
                                    return post.getDescription().contains(filtrareTextBox.getText());
                                }
                            });
                        }
                        if (filtrareTipChoice.getSelectionModel().getSelectedItem()=="Incepe cu") {
                            raportModel.setSarcinaFilter(new Predicate<Sarcina>() {
                                @Override
                                public boolean test(Sarcina post) {
                                    return post.getDescription().startsWith(filtrareTextBox.getText());
                                }
                            });
                        }
                    }
                }

                raportModel.update();
            }
        });


    }



}
