package com.example.labs;

import javafx.scene.Parent;
import javafx.stage.Stage;
import repository.interfaces.Repository;
import repository.interfaces.RepositoryAdmin;
import repository.interfaces.RepositoryAgent;

public class EmplController {
    private Repository repoAgent;
    private Repository repoAdmin;
    Stage dialogStage;
    RepositoryAgent repositoryAgent;
    RepositoryAdmin repositoryAdmin;
    Parent mainChatParent;
    public void set(RepositoryAgent repo1, RepositoryAdmin repo2,Stage stage)
    {
        repositoryAdmin=repo2;
        repositoryAgent=repo1;
        this.dialogStage = stage;
    }
}
