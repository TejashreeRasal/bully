import java.util.*;

public class RingAlgorithm {
    private List<Process> processes;
    private int leader = -1;

    public RingAlgorithm(int numProcesses) {
        processes = new ArrayList<>();
        for (int i = 0; i < numProcesses; i++) {
            processes.add(new Process(i));
        }
    }

    public void startElection() {
        System.out.println("Election started.");
        Process tokenHolder = processes.get(0);
        tokenHolder.sendToken();
    }

    public class Process {
        private int id;
        private boolean isAlive;
        private boolean isLeader;

        public Process(int id) {
            this.id = id;
            this.isAlive = true;
            this.isLeader = false;
        }

        public int getId() {
            return id;
        }

        public void sendToken() {
            System.out.println("Process " + id + " sends the token.");
            int nextProcessIndex = (id + 1) % processes.size();
            Process nextProcess = processes.get(nextProcessIndex);
            nextProcess.receiveToken(this);
        }

        public void receiveToken(Process previousProcess) {
            System.out.println("Process " + id + " receives the token from Process " + previousProcess.getId());
            if (id > previousProcess.getId()) {
                sendToken();
            } else {
                leader = id;
                isLeader = true;
                System.out.println("Process " + id + " is elected as the leader.");
            }
        }

        public void fail() {
            isAlive = false;
            System.out.println("Process " + id + " has failed.");
        }
    }

    public static void main(String[] args) {
        RingAlgorithm ring = new RingAlgorithm(5);

        // Start the election by sending the token
        ring.startElection();
    }
}
