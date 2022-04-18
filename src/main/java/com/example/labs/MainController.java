package com.example.labs;

import javafx.scene.Parent;
import javafx.stage.Stage;
import repository.interfaces.Repository;
import repository.interfaces.RepositoryAdmin;
import repository.interfaces.RepositoryAgent;
import repository.interfaces.RepositoryProduct;

public class MainController {
    private Repository repoAgent;
    private Repository repoAdmin;
    Stage dialogStage;
    RepositoryAgent repositoryAgent;
    RepositoryAdmin repositoryAdmin;
    RepositoryProduct repositoryProduct;

    Parent mainChatParent;
    public void set(RepositoryAgent repo1, RepositoryAdmin repo2,RepositoryProduct repo3,Stage stage)
    {
        repositoryAdmin=repo2;
        repositoryAgent=repo1;
        repositoryProduct=repo3;
        this.dialogStage = stage;
    }
}
