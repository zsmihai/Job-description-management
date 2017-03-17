package Main;

import Domain.*;
import Interface.*;
import Repository.*;
import Services.PostSarcinaService;
import Services.PostService;
import Services.SarcinaService;
import Validator.PostSarcinaValidator;
import Validator.PostValidator;
import Validator.SarcinaValidator;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Created by Utilizator on 07-Nov-16.
 */
public class Main extends Application {

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


        /*AbstractRepository<Post, Integer> postRepository = new PostFileRepository(new PostValidator(), "src/files/post.txt");
        PostService postService = new PostService(postRepository);
        PostModel postModel = new PostModel(postService);
        PostController postController = new PostController(postService);
        PostView postView = new PostView(postModel.getModelList(), postController);
        Parent parent =postView.getView();
        Scene scene = new Scene(parent, 666, 666);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Post");
        primaryStage.show();
        Stage postStage = new Stage();
        postStage.setScene(scene);
        postStage.show();
*/

        PostStaxRepository postRepository = new PostStaxRepository(new PostValidator(), "src/files/post.xml");
        PostService postService = new PostService(postRepository);
        PostModel postModel = new PostModel(postService);
        PostController postController = new PostController(postService);
        PostView postView = new PostView(postModel.getModelList(), postController);
        Parent parent =postView.getView();
        Scene scene = new Scene(parent, 666, 666);

        Stage postStage = new Stage();
        postStage.setScene(scene);
        postStage.show();




        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/Interface/SarcinaView.fxml"));

        SarcinaSerializeRepository sarcinaRepository;
        sarcinaRepository = new SarcinaSerializeRepository(new SarcinaValidator(), "src/files/sarcina");
        sarcinaRepository.load();
        SarcinaService sarcinaService = new SarcinaService(sarcinaRepository);
        SarcinaModel sarcinaModel = new SarcinaModel(sarcinaService);


        BorderPane layout = loader.load();
        SarcinaController  sarcinaController =loader.getController();
        sarcinaController.setSarcinaModel(sarcinaModel);
        sarcinaController.setSarcinaService(sarcinaService);


        sarcinaController.init();

        Scene sarcinascene = new Scene(layout);

        primaryStage.setScene(sarcinascene);
        primaryStage.show();





        FXMLLoader lloader =new FXMLLoader();
        lloader.setLocation(Main.class.getResource("/Interface/PostSarcinaView.fxml"));


        PostSarcinaService postSarcinaService = new PostSarcinaService(new PostSarcinaFileRepository(new PostSarcinaValidator(), "src/files/postsarcina"), postRepository, sarcinaRepository);
        postService.addObserver(postSarcinaService);
        sarcinaService.addObserver(postSarcinaService);


        PostSarcinaModel postSarcinaModel = new PostSarcinaModel(sarcinaService, postSarcinaService);

        PostSarcinaController postSarcinaController = new PostSarcinaController(postSarcinaService, postSarcinaModel, postModel);

        lloader.setController(postSarcinaController);

        BorderPane llayout = lloader.load();

        postSarcinaController.init();
        Scene sscene = new Scene(llayout);
        Stage sstage = new Stage();
        sstage.setScene(sscene);
        sstage.show();


        FXMLLoader llloader =new FXMLLoader();
        llloader.setLocation(Main.class.getResource("/Interface/RapoarteView.fxml"));

        RaportModel raportModel  =new RaportModel(postService, sarcinaService, postSarcinaService);

        postSarcinaService.addObserver(raportModel);

        RapoarteController rapoarteController = new RapoarteController(raportModel);

        llloader.setController(rapoarteController);

        BorderPane lllayout = llloader.load();
        rapoarteController.init();
        Scene ssscene = new Scene(lllayout);
        Stage ssstage = new Stage();
        ssstage.setScene(ssscene);
        ssstage.show();

        String s = "a";
        System.out.println(s=="a");

    }
}
