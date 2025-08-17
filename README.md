# TaskMaster — Java Console Simulation

**TaskMaster** is a Java console application that simulates assigning queued tasks to a pool of workers, tracks work/idle time, and computes detailed productivity & pay metrics. It reads input from a simple text file, schedules tasks over time, and produces a final report.

---

## What it does

- Loads **workers** (names) and a list of **tasks** from `TaskData.txt`.
- Simulates time in minutes; when enough workers are free, it **assigns** a task.
- Each task requires `workersNum` people for `minutesPerPerson` minutes and pays `totalPay` dollars, split **evenly** among assigned workers when finished.
- Tracks, per worker and in aggregate:
  - total time, work time, idle time,
  - dollars earned, dollars per hour,
  - team totals and averages.

---

## Core Classes

- `Main.java` — entry point; starts the app by creating `Master`.
- `Master.java` — **simulation engine**:
  - Parses `TaskData.txt`,
  - Holds the **queue** (`Queue<Task> tasks`), the **workers list**, and **in-progress** tasks,
  - Advances a clock (`time`), assigns workers to tasks, and finishes tasks at `endTime`,
  - Updates workers’ work/idle minutes and pay.
- `Task.java` — data model for a task:
  - `workersNum`, `minutesPerPerson`, `totalPay`, `endTime`,
  - `assignedWorkers` (list of worker indices).
- `Worker.java` — data model for a worker:
  - name, `isWorking`, total/work/idle minutes, `totalDollars`,
  - helpers: `incWorkTime`, `incIdleTime`, `incTotalDollars`, etc.
- `Admin.java` — **reporting & metrics**:
  - computes totals/averages (time & money),
  - formats and prints the final report of team and per‑worker stats.

---

## Input File Format (`TaskData.txt`)

Line 1 — **comma‑separated** worker names:  
```
Aarya,Betty,Cornell,Divya,Enoch,Frank
```

Lines 2+ — one task per line, fields are **minutesPerPerson, workersNum, totalPay**:  
```
30,1,10
240,6,280
90,3,60
60,2,40
90,1,15
100,2,60
120,4,160
```

Meaning of a line like `240,6,280`:
- Each of the **6 workers** assigned will work **240 minutes**, and when the task completes the **$280** is split **evenly** among them.

> You can add or remove workers by editing the first line, and add tasks by appending lines in the above format.

---

## How the simulation runs (high level)

1. **Initialize** workers from line 1 and **enqueue** tasks from the remaining lines.  
2. A discrete **time loop** advances `time` minute by minute:  
   - If the **front task** requires `k` workers and at least `k` workers are **idle**, they’re assigned and the task is moved to `inProgressTasks` with `endTime = time + minutesPerPerson`.  
   - Each minute, all working workers get `workTime++`; idle workers get `idleTime++`; everyone gets `totalTime++`.  
3. When a task **finishes** (clock reaches `endTime`), each assigned worker receives `totalPay / workersNum` dollars.  
4. At the end, **Admin** calculates **totals and averages** and prints the report.

---

## Build & Run

Requires **JDK 11+**.

### Option A — Compile & run with `javac`/`java`
```bash
cd TaskMaster-main/TaskMaster-main

# Compile
javac Main.java Master.java Admin.java Task.java Worker.java

# Run
java Main
```

> Make sure `TaskData.txt` is in the **same folder** as the class files when you run the program.

### Option B — Run in an IDE
- Open the folder as a Java project in IntelliJ IDEA / VS Code.  
- Set the run configuration main class to `Main`.  
- Ensure `TaskData.txt` is set as the working directory resource (same folder).

---

## Sample Run (using the provided TaskData.txt)

- 6 workers created: `Aarya, Betty, Cornell, Divya, Enoch, Frank`  
- 7 tasks queued (see file example above).  
- The simulation assigns tasks when enough workers are available and distributes pay at completion.  
- At the end you’ll see a **console report** with:
  - **Totals**: time worked/idle, dollars earned, $/hr.  
  - **Averages** per worker.  
  - **Per‑worker** lines (name, total/work/idle minutes, dollars).

*(Exact numbers depend on the queue order and timings in `Master.java`.)*

---

## Customization Tips

- Change worker names (line 1) and add more tasks to model different scenarios.
- Increase `minutesPerPerson` or `workersNum` to create **bottlenecks** and observe idle time growing.
- Adjust `totalPay` to see how **$ per hour** shifts across workers and team totals.
- If you want CSV output instead of console, you can extend `Admin` to write to a file.

---

## Project Layout
```
TaskMaster-main/
  TaskMaster-main/
    Admin.java
    Main.java
    Master.java
    Task.java
    Worker.java
    TaskData.txt
```

---

## Implementation Notes

- Uses standard Java collections: `Queue<Task>`, `ArrayList<Worker>`.
- Task assignment checks for enough **idle** workers before starting.
- `endTime` is stored per task; completion triggers pay distribution.
- Work & idle minutes update each clock tick for all workers.
- No external libraries; pure Java SE.

---

## Limitations / Next Steps

- No persistence beyond `TaskData.txt`; all results print to console.  
- No priorities or preemption; tasks are processed in **queue order** only.  
- All pay splits are **equal share**; no role‑based rates.  
- Idea: add **priorities**, **overtime rules**, **CSV export**, **unit tests**, or a simple **GUI**.
