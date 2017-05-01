package poker;

public class TwitterInformation {

    private String gameMessage;
    private long originalMessageId;
    private long currentMessageId;
    private String playerName;

    public TwitterInformation(long messageId, String name) {
        this.originalMessageId = messageId;
        this.gameMessage = "";
        this.playerName = name;
    }

    public void updateGameMessage(String message) {
        System.out.println(message);
        this.gameMessage += message + "\n";
    }

    public long getOriginalMessageId() {
        return this.originalMessageId;
    }

    public long getCurrentMessageId() {
        return this.currentMessageId;
    }

    public void updateCurrentMessageId(long lastTweetId) {
        this.currentMessageId = lastTweetId;
    }

    public String getGameMessage() {
        return this.gameMessage;
    }

    public void clearGameMessage() {
        this.gameMessage = "";
    }

    public String getPlayerName() { return this.playerName; }

    public static void main(String[] args) {
        TwitterInformation ti = new TwitterInformation(1234L, "BitPlayer");

        System.out.println(ti.getPlayerName().equals("BitPlayer"));
        ti.updateGameMessage("Simple game message");
        System.out.println(ti.getGameMessage().equals("Simple game message\n"));
        System.out.println(ti.originalMessageId == 1234L);
        ti.updateCurrentMessageId(3456L);
        System.out.println(ti.getCurrentMessageId() == 3456L);
        ti.clearGameMessage();
        System.out.println(ti.getGameMessage().equals(""));
    }


}
