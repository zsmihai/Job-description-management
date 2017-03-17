package Domain;

import Services.PostService;
import Services.SarcinaService;
import Utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Utilizator on 05-Dec-16.
 */
public class SarcinaModel implements Observer {
        private ObservableList<Sarcina> modelList;
        private SarcinaService sarcinaService;

        public SarcinaModel(SarcinaService sarcinaService){
            this.sarcinaService = sarcinaService;
            this.sarcinaService.addObserver(this);
            modelList = FXCollections.observableList( sarcinaService.getAll());
        }

        @Override
        public void update() {
            modelList.setAll( FXCollections.observableList(sarcinaService.getAll()));
        }

        public ObservableList<Sarcina> getModelList(){
            return modelList;
        }


}
