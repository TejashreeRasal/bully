import java.util.*;

public class BullyAlgorithm {
    private List<Process> processes;
    private int leader = -1;

    public BullyAlgorithm(int numProcesses) {
        processes = new ArrayList<>();
        for (int i = 0; i < numProcesses; i++) {
            processes.add(new Process(i));
        }
    }

    public void startElection(int initiatorId) {
        Process initiator = processes.get(initiatorId);
        System.out.println("Process " + initiatorId + " is initiating the election.");
        initiator.initiateElection();
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

        public void initiateElection() {
            for (int i = id + 1; i < processes.size(); i++) {
                if (processes.get(i).isAlive) {
                    System.out.println("Process " + id + " sends election message to " + i);
                }
            }

            boolean responseReceived = false;
            for (int i = id + 1; i < processes.size(); i++) {
                if (processes.get(i).isAlive) {
                    responseReceived = true;
                    break;
                }
            }

            if (!responseReceived) {
                leader = id;
                isLeader = true;
                System.out.println("Process " + id + " becomes the leader.");
            } else {
                System.out.println("Election aborted, higher ID process wins.");
            }
        }

        public void fail() {
            isAlive = false;
            System.out.println("Process " + id + " has failed.");
        }
    }

    public static void main(String[] args) {
        BullyAlgorithm bully = new BullyAlgorithm(5);

        // Simulate a process initiating an election
        bully.startElection(0);
    }
}
