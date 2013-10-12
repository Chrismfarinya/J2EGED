package fr.gphy.piotrgui.j2eged.controllers;

import fr.gphy.piotrgui.j2eged.helpers.BrowserHelper;
import fr.gphy.piotrgui.j2eged.model.Document;
import fr.gphy.piotrgui.j2eged.model.Folder;
import fr.gphy.piotrgui.j2eged.model.Metadata;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Piotr
 */
@ManagedBean(name = "BrowserController")
@SessionScoped
public class BrowserController implements Serializable {

    private List<Object[]> data;
    private List<Folder> folders;
    private List<DisplayDoc> toDisplay;
    private final BrowserHelper helper;
    private Folder currenFolder;
    
    private boolean listView = false;
    private boolean iconView = true;
    private boolean galleriaView = false;
    
    private final FolderHistory folderHistory;

    public BrowserController() {
        toDisplay = new ArrayList<DisplayDoc>();
        helper = new BrowserHelper();
        currenFolder = null;

        folderHistory = new FolderHistory();
    }

    public List<Object[]> getData() {
        return data;
    }

    public void setData(List<Object[]> data) {
        this.data = data;
    }

    public List<DisplayDoc> getToDisplay() {
        return toDisplay;
    }

    public void setToDisplay(List<DisplayDoc> toDisplay) {
        this.toDisplay = toDisplay;
    }

    public void clear() {
        this.toDisplay = new ArrayList<>();
    }

    public void onLoad() throws IOException {
        if (currenFolder == null) {
            changeFolder(currenFolder);
        }
    }

    public void loadToDisplay() {
        for (Folder fold : this.folders) {
            this.toDisplay.add(new DisplayDoc(fold));
        }
        
        for (Object[] row : this.data) {
            this.toDisplay.add(new DisplayDoc((Document) row[0], (Metadata) row[1]));
        }

    }
    
    public void changeFolder(Folder newFolder) {
        changeFolder(newFolder, true);
    }
    
    public void changeFolder(Folder newFolder, boolean browsing) {
        Integer idFolder = null;
        if (newFolder != null) {
            idFolder = newFolder.getIdFolder();
        }

        this.clear();

        this.currenFolder = newFolder;

        this.helper.reloadSession();

        this.data = this.helper.getDocuments(idFolder);
        this.folders = this.helper.getFolders(idFolder);

        this.loadToDisplay();
        
        if(browsing) folderHistory.add(currenFolder);
    }

    public void clickOnFolder(ActionEvent event) {
        System.err.println("clickOnFolder");
        String paramId = ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getParameter("idFolder");
        Integer idDestFolder = !paramId.equals("null") ? Integer.valueOf(paramId) : null;
        this.clear();

        this.helper.reloadSession();
        this.currenFolder = this.helper.getFolder(idDestFolder);

        changeFolder(this.currenFolder);
    }
    
    public void clickOnBackward() {
        changeFolder(folderHistory.backward(), false);
    }

    public void clickOnForward() {
        changeFolder(folderHistory.forward(), false);
    }

    public Folder getCurrenFolder() {
        return currenFolder;
    }

    public void setCurrenFolder(Folder currenFolder) {
        this.currenFolder = currenFolder;
    }
    
    public boolean isListView() {
        return listView;
    }

    public void setListView(boolean listView) {
        this.listView = listView;
        this.iconView = !listView;
        this.galleriaView = !listView;
    }

    public boolean isIconView() {
        return iconView;
    }

    public void setIconView(boolean iconView) {
        this.iconView = iconView;
        this.listView = !iconView;
        this.galleriaView = !iconView;
    }

    public boolean isGalleriaView() {
        return galleriaView;
    }

    public void setGalleriaView(boolean galleriaView) {
        this.galleriaView = galleriaView;
        this.iconView = !galleriaView;
        this.listView = !galleriaView;
    }
    
    public class FolderHistory implements Serializable {

        private List<Folder> history;
        private Integer historyPosition;

        public FolderHistory() {
            history = new ArrayList<Folder>();
            historyPosition = null;
        }

        public void add(Folder f) {
            int pos = historyPosition != null ? historyPosition + 1 : 0;

            if (pos + 1 < history.size()) {
                for (int i = pos + 1; i < history.size(); i++) {
                    history.remove(i);
                }
            }
            history.add(pos, f);
            historyPosition = pos;
        }

        public Folder backward() {
            if (historyPosition != null) {
                historyPosition--;
                if (historyPosition < 0) {
                    historyPosition = 0;
                }

                return history.get(historyPosition);
            }
            return null;
        }

        public Folder forward() {
            if (historyPosition != null) {
                historyPosition++;
                if (historyPosition >= history.size()) {
                    historyPosition = history.size() - 1;
                }

                return history.get(historyPosition);
            }
            return null;
        }
    }

    public class DisplayDoc implements Serializable {

        private Document doc = null;
        private Metadata meta = null;
        private Folder folder = null;

        public DisplayDoc(Document doc, Metadata meta) {
            this.doc = doc;
            this.meta = meta;
        }

        public DisplayDoc(Folder folder) {
            this.folder = folder;
        }

        public Folder getFolder() {
            return folder;
        }

        public void setFolder(Folder folder) {
            this.folder = folder;
        }

        public Document getDoc() {
            return doc;
        }

        public Metadata getMeta() {
            return meta;
        }

        public Boolean isFolder() {
            return (this.folder != null);
        }
    }
}
