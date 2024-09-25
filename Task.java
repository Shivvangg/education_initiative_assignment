// code for problem statement 1 :-

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// Task class representing a scheduled task
class Task {
    private String description;
    private Date startTime;
    private Date endTime;
    private int priority;
    private boolean isCompleted;

    public Task(String description, Date startTime, Date endTime, int priority) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.isCompleted = false;
    }

    // Getters and setters
    public String getDescription() { return description; }
    public Date getStartTime() { return startTime; }
    public Date getEndTime() { return endTime; }
    public int getPriority() { return priority; }
    public boolean isCompleted() { return isCompleted; }
    public void markCompleted() { this.isCompleted = true; }

    @Override
    public String toString() {
        return String.format("Task: %s | Start: %s | End: %s | Priority: %d | Completed: %s",
                description, startTime, endTime, priority, isCompleted);
    }
}

// TaskManager class to manage tasks
class TaskManager {
    private List<Task> tasks = new ArrayList<>();
    private Logger logger = Logger.getLogger(TaskManager.class.getName());

    public void addTask(Task task) {
        if (validateTaskOverlap(task)) {
            tasks.add(task);
            logger.log(Level.INFO, "Task added: " + task);
        } else {
            logger.log(Level.WARNING, "Task overlaps with existing tasks.");
            System.out.println("Error: Task overlaps with existing tasks.");
        }
    }

    public void removeTask(Task task) {
        if (tasks.remove(task)) {
            logger.log(Level.INFO, "Task removed: " + task);
        } else {
            logger.log(Level.WARNING, "Task not found: " + task);
            System.out.println("Error: Task not found.");
        }
    }

    public void viewTasks() {
        tasks.sort(Comparator.comparing(Task::getStartTime));
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public void editTask(Task oldTask, Task newTask) {
        if (removeTask(oldTask)) {
            addTask(newTask);
        } else {
            System.out.println("Error: Task to edit not found.");
        }
    }

    public void markTaskAsCompleted(Task task) {
        if (tasks.contains(task)) {
            task.markCompleted();
            logger.log(Level.INFO, "Task marked as completed: " + task);
        } else {
            System.out.println("Error: Task not found.");
        }
    }

    public List<Task> getTasksByPriority(int priority) {
        List<Task> filteredTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getPriority() == priority) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }

    private boolean validateTaskOverlap(Task newTask) {
        for (Task task : tasks) {
            if ((newTask.getStartTime().before(task.getEndTime()) && newTask.getEndTime().after(task.getStartTime()))) {
                return false; // Overlap found
            }
        }
        return true; // No overlap
    }
}

// Logger class for logging
class Logger {
    private static Logger instance;
    private java.util.logging.Logger logger;

    private Logger() {
        logger = java.util.logging.Logger.getLogger(Logger.class.getName());
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    public void log(Level level, String message) {
        logger.log(level, message);
    }
}

// Main class
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TaskManager taskManager = new TaskManager();

    public static void main(String[] args) {
        while (true) {
            System.out.println("Astronaut Daily Schedule Organizer");
            System.out.println("1. Add Task");
            System.out.println("2. Remove Task");
            System.out.println("3. View Tasks");
            System.out.println("4. Edit Task");
            System.out.println("5. Mark Task as Completed");
            System.out.println("6. View Tasks by Priority");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    removeTask();
                    break;
                case 3:
                    taskManager.viewTasks();
                    break;
                case 4:
                    editTask();
                    break;
                case 5:
                    markTaskAsCompleted();
                    break;
                case 6:
                    viewTasksByPriority();
                    break;
                case 7:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addTask() {
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();
        System.out.print("Enter start time (yyyy-MM-dd HH:mm): ");
        Date startTime = parseDate(scanner.nextLine());
        System.out.print("Enter end time (yyyy-MM-dd HH:mm): ");
        Date endTime = parseDate(scanner.nextLine());
        System.out.print("Enter priority (1-5): ");
        int priority = scanner.nextInt();

        Task task = new Task(description, startTime, endTime, priority);
        taskManager.addTask(task);
    }

    private static void removeTask() {
        System.out.print("Enter task description to remove: ");
        String description = scanner.nextLine();
        // You might want to implement a more sophisticated search and remove logic
        Task taskToRemove = null;
        for (Task task : taskManager.getTasks()) {
            if (task.getDescription().equalsIgnoreCase(description)) {
                taskToRemove = task;
                break;
            }
        }
        if (taskToRemove != null) {
            taskManager.removeTask(taskToRemove);
        } else {
            System.out.println("Task not found.");
        }
    }

    private static void editTask() {
        System.out.print("Enter existing task description to edit: ");
        String oldDescription = scanner.nextLine();
        Task oldTask = null;

        for (Task task : taskManager.getTasks()) {
            if (task.getDescription().equalsIgnoreCase(oldDescription)) {
                oldTask = task;
                break;
            }
        }

        if (oldTask != null) {
            System.out.print("Enter new task description: ");
            String newDescription = scanner.nextLine();
            System.out.print("Enter new start time (yyyy-MM-dd HH:mm): ");
            Date newStartTime = parseDate(scanner.nextLine());
            System.out.print("Enter new end time (yyyy-MM-dd HH:mm): ");
            Date newEndTime = parseDate(scanner.nextLine());
            System.out.print("Enter new priority (1-5): ");
            int newPriority = scanner.nextInt();

            Task newTask = new Task(newDescription, newStartTime, newEndTime, newPriority);
            taskManager.editTask(oldTask, newTask);
        } else {
            System.out.println("Task to edit not found.");
        }
    }

    private static void markTaskAsCompleted() {
        System.out.print("Enter task description to mark as completed: ");
        String description = scanner.nextLine();
        Task taskToComplete = null;

        for (Task task : taskManager.getTasks()) {
            if (task.getDescription().equalsIgnoreCase(description)) {
                taskToComplete = task;
                break;
            }
        }

        if (taskToComplete != null) {
            taskManager.markTaskAsCompleted(taskToComplete);
        } else {
            System.out.println("Task not found.");
        }
    }

    private static void viewTasksByPriority() {
        System.out.print("Enter priority level (1-5): ");
        int priority = scanner.nextInt();
        List<Task> tasks = taskManager.getTasksByPriority(priority);

        if (tasks.isEmpty()) {
            System.out.println("No tasks found with priority " + priority);
        } else {
            for (Task task : tasks) {
                System.out.println(task);
            }
        }
    }

    private static Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use yyyy-MM-dd HH:mm");
            return new Date(); // return current date as a fallback
        }
    }
}
