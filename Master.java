import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Master {
    Queue<Task> tasks = new LinkedList<Task>();
    ArrayList<Worker> workers = new ArrayList<Worker>();
    ArrayList<Task> inProgressTasks = new ArrayList<Task>();
    int time = 0;
    int numWorking = 0;
    int numNotWorking = 0;

    public Master() {
        loadData();
        numNotWorking = workers.size();
        while (!tasks.isEmpty()) {
            int wor = tasks.peek().getWorkersNum();
            while (numNotWorking >= wor) {
                Task newTask = tasks.peek();
                int workersNeeded = newTask.getWorkersNum();
                int workTime = newTask.getMinutesPerPerson();
                ArrayList<Integer> newTaskWorkers = new ArrayList<>();

                int workingOnNewTask = 0;
                for (int x = 0; x < workers.size(); x++) {
                    if (!workers.get(x).getIsWorking() && workingOnNewTask < workersNeeded) {
                        numWorking++;
                        numNotWorking--;
                        workingOnNewTask++;
                        workers.get(x).setIsWorking(true);
                        newTaskWorkers.add(x);
                    }
                }
                tasks.peek().assignWorkers(newTaskWorkers);
                tasks.peek().setEndTime(time + workTime);
                tasks.poll();
                inProgressTasks.add(newTask);
            }

            for (int i = inProgressTasks.size() - 1; i >= 0; i--) {
                Task task = inProgressTasks.get(i);
                double salary = task.getTotalPay() / task.getWorkersNum();
                if (inProgressTasks.get(i).getEndTime() == time) {
                    for (int x = 0; x < task.getWorkers().size(); x++) {
                        workers.get(task.getWorkers().get(x)).incTotalDollars(salary);
                        workers.get(task.getWorkers().get(x)).setIsWorking(false);
                        numNotWorking++;
                        numWorking--;
                    }
                    inProgressTasks.remove(i);
                }
            }

            for (int i = 0; i < workers.size(); i++) {
                if (workers.get(i).getIsWorking()) {
                    workers.get(i).incWorkTime(1);
                } else {
                    workers.get(i).incIdleTime(1);
                }
                workers.get(i).incTotalTime(1);
            }
            time++;
        }

        while (inProgressTasks.size() != 0) {
            for (int i = inProgressTasks.size() - 1; i >= 0; i--) {
                Task task = inProgressTasks.get(i);
                double salary = task.getTotalPay() / task.getWorkersNum();

                if (inProgressTasks.get(i).getEndTime() == time) {
                    for (int x = 0; x < task.getWorkers().size(); x++) {
                        workers.get(task.getWorkers().get(x)).incTotalDollars(salary);
                        workers.get(task.getWorkers().get(x)).setIsWorking(false);
                        numNotWorking++;
                        numWorking--;
                    }
                    inProgressTasks.remove(i);
                }
            }

            for (int i = 0; i < workers.size(); i++) {
                if (workers.get(i).getIsWorking()) {
                    workers.get(i).incWorkTime(1);
                    workers.get(i).incTotalTime(1);
                }
            }
            time++;
        }
        Admin admin = new Admin(workers);
        admin.timeUseReport();
        admin.wageReport();
        admin.workerReport();
    }

    public void loadData() {
        try {
            File name = new File("TaskData.txt");
            BufferedReader input = new BufferedReader(new FileReader(name));
            String text = input.readLine();

            if (text != null) {
                for (int i = 0; i < text.split(",").length; i++) {
                    workers.add(new Worker(text.split(",")[i]));
                }
            }
            Admin admin = new Admin(workers);

            while ((text = input.readLine()) != null) {
                Task t = new Task(Integer.parseInt(text.split(",")[0]), Integer.parseInt(text.split(",")[1]),
                        Double.parseDouble(text.split(",")[2]));
                if (admin.isViableTask(t)) {
                    tasks.add(t);
                }
            }
            System.out.println("\nViable Tasks (" + tasks.size() + ")=> " + tasks);
        } catch (IOException e) {

        }
    }
}
