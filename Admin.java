import java.util.*;
import java.io.*;
import java.util.Queue;
import java.util.Currency;
import java.text.NumberFormat;
public class Admin
{	
	private ArrayList<Worker> workers;
  private int totalTimeWorked = 0;
  private int totalIdleTime = 0;
  private int averageTimeWorked = 0;
  private int averageIdleTime = 0;
  private double totalMoney = 0;
  private int mostHoursWorked = 0;
	private double totalWorkingMoneyPerHour = 0;
	private double averageWorkingMoney = 0;
  private int leastHoursWorked = 0;
	private double totalElapsedMoneyPerHour = 0;
	private double averageElapsedMoney = 0;
	Worker mostWorker;
	Worker leastWorker;
  private NumberFormat nF = NumberFormat.getCurrencyInstance();
  
	public Admin(ArrayList<Worker> workers)
  {
		this.workers = workers;
  }	

	public void wageReport()
  {
		//total money
		for(int i = 0; i < workers.size(); i++)
    {
			totalMoney += workers.get(i).getTotalDollars();
		}
    System.out.println("\nTotal Money Earned: "+nF.format(totalMoney));

		//avg $/working hr
    for(int i = 0; i < workers.size(); i++)
    {
        totalWorkingMoneyPerHour+=(workers.get(i).getTotalDollars()/(workers.get(i).getWorkTime()/60.0));
    }
		averageWorkingMoney = totalWorkingMoneyPerHour/workers.size();
    System.out.println("Average Money Earned per Working Hour: "+nF.format(averageWorkingMoney));


		//avg $/elapsed hr
    for(int i = 0; i < workers.size(); i++)
    {
        totalElapsedMoneyPerHour+=(workers.get(i).getTotalDollars()/((workers.get(i).getWorkTime()/60.0) + (workers.get(i).getIdleTime()/60.0)));
    }
		averageElapsedMoney = totalElapsedMoneyPerHour/workers.size();
		System.out.println("Average Money Earned per Elapsed Hour: " + nF.format(averageElapsedMoney));
	}

  public void timeUseReport()
  {



		//total time worked
    for(int i = 0; i < workers.size(); i++)
    {
        totalTimeWorked+=workers.get(i).getWorkTime();
    }
		System.out.println("Total Time Worked = " + totalTimeWorked/60 + " hrs " + totalTimeWorked%60 + " mins");
    
    //total idle time
		for(int i = 0; i < workers.size(); i++)
    {
			totalIdleTime += workers.get(i).getIdleTime();
		}
		System.out.println("Total Idle Time = " + totalIdleTime/60 + " hrs " + totalIdleTime%60 + " mins");
    
    //average time worked
    averageTimeWorked = totalTimeWorked/(workers.size());
		System.out.println("Average Working Hours Per Worker = " + averageTimeWorked/60 + " hrs " + averageTimeWorked%60 + " mins");

    //average idle time
		averageIdleTime = totalIdleTime/workers.size();
		System.out.println("Average Idle Time Per Worker = " + averageIdleTime/60 + " hrs " + averageIdleTime%60 + " mins");
	
    //most hours worked
    mostWorker = workers.get(0);
    for(int i = 0; i < workers.size(); i++)
    {
        if(workers.get(i).getWorkTime() > mostWorker.getWorkTime())
        {
          mostWorker = workers.get(i);
        }
    }
		System.out.println("Most Hours Worked => " + mostWorker.getName() + " " + mostWorker.getWorkTime()/60 + " hrs " + mostWorker.getWorkTime()%60 + " mins");
  

    //least hours worked
    leastWorker = workers.get(0);
    for(int i = 0; i < workers.size(); i++)
    {
        if(workers.get(i).getWorkTime() < leastWorker.getWorkTime())
        {
          leastWorker = workers.get(i);
        }
    }
		System.out.println("Least Hours Worked => " + leastWorker.getName() + " " + leastWorker.getWorkTime()/60 + " hrs " + leastWorker.getWorkTime()%60 + " mins");
  }
  
  public void workerReport()
  {
    System.out.println("\n");
    System.out.printf("%-7s | %-9s | %-9s | %-8s | %-9s | %-5s","Name","Work Time", "Idle Time","$ Total", "$/Work Hr", "$/Elapsed Hr");
    System.out.println("");
    for(int i = 0; i<workers.size(); i++)
    {
      double elapsedWorkDollarsPerHour = workers.get(i).getTotalDollars();
      elapsedWorkDollarsPerHour/= ((workers.get(i).getWorkTime()+workers.get(i).getIdleTime())/60.0);
    System.out.printf("%-7s | %-9s | %-9s | %-8s | %-9s | %-5s",workers.get(i).getName(),workers.get(i).getWorkTime()/60 + " hr " + workers.get(i).getWorkTime()%60 + " m", workers.get(i).getIdleTime()/60 + " hr " + workers.get(i).getIdleTime()%60 + "m", nF.format(workers.get(i).getTotalDollars()),nF.format((workers.get(i).getTotalDollars()/(workers.get(i).getWorkTime()/60.0))), nF.format(elapsedWorkDollarsPerHour));
    System.out.println("");
 }

  }
  

  public boolean isViableTask(Task t)
  {
    if(t.getWorkersNum() <= workers.size() && ((t.getTotalPay()/(t.getMinutesPerPerson()/60.0))/t.getWorkersNum()) >= 15)
    {
           return true;
    }
    else
    {
      return false;
    }
  }
}


