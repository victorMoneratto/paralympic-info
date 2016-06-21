package info.view.catalog;

import info.entity.Medalha;
import info.view.details.MedalhaDetails;
import io.datafx.controller.FXMLController;
import io.datafx.controller.flow.action.ActionMethod;
import javafx.scene.control.TableColumn;

import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@FXMLController(value = "catalog.fxml", title = "Medalhas")
public class MedalhaCatalog extends AbstractCatalog<Medalha> {
    @Override
    protected Class<Medalha> getModelClass() {
        return Medalha.class;
    }

    @Override
    protected Class getDetailsClass() {
        return MedalhaDetails.class;
    }

    @Override
    protected void postLoad() {
        details.setDisable(true);
    }

    @Override
    @ActionMethod("remove")
    public void onRemove() {
//        super.onRemove();
        Medalha m = table.getSelectionModel().getSelectedItem();
        if (m != null) {
            Query q =data.createNativeUpdate("DELETE FROM Medalha WHERE Medalha = ? AND " +
                    (m.isIndividual()? "Atleta = ?" : "Time = ?"));

            q.setParameter(1, m.getMedalha());
            q.setParameter(2, (m.isIndividual()? m.getAtleta() : m.getTime()));
            int executed = 0;
            try {
                data.getTransaction().begin();
                executed = q.executeUpdate();
            } finally {
                if (executed > 0) {
                    data.getTransaction().commit();
                    refresh();
                } else {
                    data.getTransaction().rollback();
                }
            }
        }
    }

    @Override
    protected List<TableColumn<Medalha, ?>> filterColumns(List<TableColumn<Medalha, ?>> cols) {
        return cols.stream()
                .filter(c -> !c.getText().equalsIgnoreCase("ID")
                            && !c.getText().equalsIgnoreCase("Atleta")
                            && !c.getText().equalsIgnoreCase("Time"))
                .collect(Collectors.toList());
    }
}
