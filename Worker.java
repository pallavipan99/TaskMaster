public class Worker {
  private String name = "";
  private int totalTime = 0;
  private int workTime = 0;
  private int idleTime = 0;
  private double totalDollars = 0;
  private boolean isWorking = false;

  public Worker(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getTotalTime() {
    return totalTime;
  }

  public int getWorkTime() {
    return workTime;
  }

  public void incWorkTime(int x) {
    workTime += x;
  }

  public int getIdleTime() {
    return idleTime;
  }

  public void incIdleTime(int x) {
    idleTime += x;
  }

  public void incTotalTime(int x) {
    totalTime += x;
  }

  public double getTotalDollars() {
    return totalDollars;
  }

  public boolean getIsWorking() {
    return isWorking;
  }

  public void incTotalDollars(double x) {
    totalDollars += x;
  }

  public void setIsWorking(boolean b) {
    isWorking = b;
  }

  public String toString() {
    return "Name: " + this.getName() + "\nTotal Time: " + this.getTotalTime() + "\nWork Time: " + this.getWorkTime()
        + "\nIdle Time: " + this.getIdleTime() + "\nTotal Dollars: $" + this.getTotalDollars();
  }
}
