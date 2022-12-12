import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SBOMTest {

    public static void main(final String[] args) throws IOException, InterruptedException {
        //Build command 
        List<String> commands = new ArrayList<String>();
        commands.add("./cyclonedx-linux-x64");
        commands.add("validate");
        commands.add("--input-file");
	    commands.add(args[0]);

        //Run macro on target
        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        //Read output
        StringBuilder out = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = null, previous = null;
        while ((line = br.readLine()) != null)
            if (!line.equals(previous)) {
                previous = line;
                out.append(line).append('\n');
                System.out.println(line);
            }

        //Check result
        if (process.waitFor() == 0) {
            System.out.println("Done!");
            System.exit(0);
        }
    }
}