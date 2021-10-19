package screens.screentypes;

public enum ScreenType {
    MAIN_MENU ("/menu.fxml"),
    DEAL_PARAMETERS ("/deal_parameters.fxml"), GAME_PARAMETERS ("/game_parameters.fxml"),
    DEAL_LIST ("/list_deals.fxml"), GAME_LIST ("/list_games.fxml"),
    DEAL_VIEW ("/view_deal.fxml"), GAME_VIEW ("/view_game.fxml");

    private final String screenFile;

    ScreenType(String screenFile) {
        this.screenFile = screenFile;
    }

    public String getScreenFile() {
        return screenFile;
    }
}
