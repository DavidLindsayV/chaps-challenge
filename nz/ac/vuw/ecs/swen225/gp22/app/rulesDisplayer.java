package nz.ac.vuw.ecs.swen225.gp22.app;

public class rulesDisplayer {

  // The rules text
  private final static String rulesText =
    "You're a little rabbit, try and navigate through the maze and collect all the carrots before time runs out!\n" +
    "\n" +
    "Controls:" +
    "- Up, down, left and right arrow keys move the rabbit\n" +
    "- Ctrl-X exits the game\n" +
    "- Ctrl-S saves and exits the game\n" +
    "- Ctrl-R resumes a saved game\n" +
    "- Ctrl-1 and Ctrl-2 start games at level 1 and level 2\n" +
    "- Space to Pause game, Esc to Play game (as well as the pause/play button\n" +
    "- There are menu items for showing rules, saving, exiting, and showing recorded levels\n" +
    "\n" +
    "Core game mechanics:\n" +
    "- Collect all the carrots and walk down the rabbit hole to win\n" +
    "- Collect keys to open doors of their respective colours\n" +
    "- Avoid colliding with enemies\n";

  public static void showRules() {
    MessageBox.showMessage("Game rules", rulesText);
  }
}
