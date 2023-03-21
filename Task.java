import java.util.ArrayList;
public class Task
{
  private int workersNum;
  private int minutesPerPerson;
  private double totalPay;
  private int endTime;
  private ArrayList<Integer> assignedWorkers = new ArrayList<>();
  
  public Task(int minutesPerPerson, int workersNum, double totalPay)
  {
    this.workersNum = workersNum;
    this.minutesPerPerson = minutesPerPerson;
    this.totalPay = totalPay;
	}

  public int getWorkersNum() 
  {
  	return workersNum;
  }
  
  public int getMinutesPerPerson() 
  {
  	return minutesPerPerson;
  }
  
  public double getTotalPay() 
  {
  	return totalPay;
  }

  public void setEndTime(int x)
  {
    endTime = x;
  }

	public int getEndTime()
	{
		return endTime;
	}

  public void assignWorkers(ArrayList<Integer> x)
  {
    assignedWorkers = x;
  }

  public ArrayList<Integer> getWorkers()
  {
    return assignedWorkers;
  }
	
  public String toString()
  {
    return "Number of workers: " + this.getWorkersNum() + "\nMinutes per person: " + this.getMinutesPerPerson() + " minutes\nTotal Pay: $" + this.getTotalPay() + "\n";
  }
}