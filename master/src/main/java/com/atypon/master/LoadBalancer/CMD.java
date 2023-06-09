package com.atypon.master.LoadBalancer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public final class CMD {
    private CMD(){}

    public static void removeAllDockerContainer() {
        try {
            Runtime.getRuntime().exec("powershell.exe docker container stop $(docker container list -q)");
            TimeUnit.SECONDS.sleep(3);
        } catch (IOException | InterruptedException e) {
            System.out.println("error while removing containers");
        }

    }
    public static void runNodeContainer(int nodePort)  {
        try {
             Runtime.getRuntime().exec("docker run -p "+nodePort+":8085 node");
        } catch (IOException e) {
            System.out.println("error while running container");
        }
    }
}
