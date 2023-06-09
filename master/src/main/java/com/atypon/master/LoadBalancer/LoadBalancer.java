package com.atypon.master.LoadBalancer;

public final class LoadBalancer {
    private static final int firstPort = 8085;
    private static final int lastPort = 8087;
    private static int currentNodeTurn =8085;

    private LoadBalancer(){}
    public static void loadNodes(){
        CMD.removeAllDockerContainer();
        // loop through nodes port numbers and run container
        for (int i = firstPort; i <= lastPort ; i++) {
            Node node = Node.newInstance(i);
            if (i==firstPort)
                continue;
            CMD.runNodeContainer(i); // run a node in docker container
        }
    }
    public static int getCurrentNodeTurn() {
        int nodePortNumber = currentNodeTurn;
        if (currentNodeTurn+1 > lastPort)
            currentNodeTurn=firstPort;
        else
            currentNodeTurn++;
        return nodePortNumber;
    }



}
