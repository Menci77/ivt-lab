package hu.bme.mit.spaceship;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
* Class storing and managing the torpedoes of a ship
*
* (Deliberately contains bugs.)
*/
public class TorpedoStore {

  // rate of failing to fire torpedos [0.0, 1.0]
  private double FAILURE_RATE = 0.0; //NOSONAR

  private int torpedoCount = 0;

  private Random rand; 


  public TorpedoStore(int numberOfTorpedos){
    this.torpedoCount = numberOfTorpedos;

    try{
      rand = SecureRandom.getInstanceStrong();
    } catch(NoSuchAlgorithmException e){
      var logger = Logger.getLogger(TorpedoStore.class.getName());
      logger.log(Level.WARNING, e.getMessage());
    }
    // update failure rate if it was specified in an environment variable
    String failureEnv = System.getenv("IVT_RATE");
    if (failureEnv != null){
      try {
        FAILURE_RATE = Double.parseDouble(failureEnv);
      } catch (NumberFormatException nfe) {
        FAILURE_RATE = 0.0;
      }
    }
  }

  public boolean fire(int numberOfTorpedos){
    
    if(numberOfTorpedos < 1 || numberOfTorpedos > this.torpedoCount){
      throw new IllegalArgumentException("numberOfTorpedos");
    }

    var success = false;

    // simulate random overheating of the launcher bay which prevents firing
   
    var r = this.rand.nextDouble();

    if (r >= FAILURE_RATE) {
      // successful firing
      this.torpedoCount -= numberOfTorpedos;
      success = true;
    } else {
      // simulated failure
      success = false;
    }

    return success;
  
  }

  public boolean isEmpty(){
    return this.torpedoCount <= 0;
  }

  public int getTorpedoCount() {
    return this.torpedoCount;
  }
}
