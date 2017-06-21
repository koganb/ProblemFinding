package il.ac.bgu;

import org.agreement_technologies.agents.MAPboot;
import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.Reader;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Boris on 26/05/2017.
 */
public class SatSolver {
    public static void main ( String [] args ) throws IOException {
        String [] agentDefs = Files.readAllLines(Paths.get("C:\\MyProjects\\FMAP\\elevator1")).stream().
                flatMap(t -> Arrays.stream(t.split("\t"))).
                toArray(String[]::new);


        MAPboot.main(agentDefs);


        ISolver solver = SolverFactory. newDefault ();
        solver.setTimeout (3600); // 1 hour timeout
        Reader reader = new DimacsReader( solver );

        try {
            IProblem problem = reader.parseInstance (
                    SatSolver.class.getClassLoader().getResourceAsStream("elevators1_failed.cnf"));
            if ( problem.isSatisfiable ()) {
                System.out.println (" Satisfiable !");
                System.out.println ( reader.decode ( problem.model()));
            } else {
                System.out.println (" Unsatisfiable !");
            }
        } catch ( Exception e) {
            throw new RuntimeException(e);
        }
    }

}
