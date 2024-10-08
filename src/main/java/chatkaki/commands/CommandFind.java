package chatkaki.commands;

import chatkaki.tasks.TaskList;

/**
 * Represents a command to find tasks with a keyword.
 */
public class CommandFind extends Command {

    private String keyword;
    private boolean isMatchFullWord;
    private boolean isMatchDescription;
    private boolean isHelp;

    /**
     * Constructs a CommandFind object with the specified inputs.
     *
     * @param inputs The inputs for the command.
     */
    public CommandFind(String[] inputs) {
        if (inputs.length < 2) {
            this.isHelp = true;
            return;
        }
        inputs = inputs[1].split(" ");
        this.keyword = inputs[0];
        this.isMatchFullWord = false;
        this.isMatchDescription = false;

        for (String input : inputs) {
            switch (input) {
                case "-full":
                    this.isMatchFullWord = true;
                    break;
                case "-desc":
                    this.isMatchDescription = true;
                    break;
                case "-help":
                    this.isHelp = true;
                    break;
                default:
                    break;
            }
        }
        System.out.println("Keyword: " + keyword);
        System.out.println("Match full word: " + isMatchFullWord);
        System.out.println("Match description: " + isMatchDescription);

    }

    @Override
    public String execute() {
        StringBuilder listMessage = new StringBuilder("Here are the matching tasks in your list:");
        int count = 0;
        if ((isHelp || keyword.isEmpty()) && !isMatchFullWord && !isMatchDescription) {
            return "Find tasks with a keyword." + System.lineSeparator()
                    + "Usage: find <keyword> [-full] [-desc] [-help]" + System.lineSeparator()
                    + "Options:" + System.lineSeparator()
                    + "-full: Match full word only." + System.lineSeparator()
                    + "-desc: Match keyword in description only." + System.lineSeparator()
                    + "-help: Show help message.";
        }
        for (int i = 0; i < TaskList.getSize(); i++) {
            String taskDescription = this.isMatchDescription
                    ? TaskList.getTask(i).getDescription() : TaskList.getTask(i).toString();
            if (isMatchFullWord) {
                String[] words = taskDescription.split("[^a-zA-Z0-9]+");
                for (String word : words) {
                    if (word.equalsIgnoreCase(keyword)) {
                        count++;
                        listMessage.append("\n ").append(i + 1).append(". ").append(TaskList.getTask(i));
                        break;
                    }
                }
            } else {
                if (taskDescription.contains(keyword)) {
                    count++;
                    listMessage.append("\n ").append(i + 1).append(". ").append(TaskList.getTask(i));
                }
            }
        }
        if (count == 0) {
            return "No matching tasks found.";
        }
        return listMessage.toString();
    }
}
