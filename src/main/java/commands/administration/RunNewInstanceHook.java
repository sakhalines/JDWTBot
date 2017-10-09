package commands.administration;

import java.io.IOException;

public class RunNewInstanceHook  extends Thread {
    private String[] commands;
    public RunNewInstanceHook(String... args)
    {
        commands = args;
    }
    @Override
    public void run()
    {
        try
        {
            new ProcessBuilder().command(commands).start();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
