

import java.io.FileNotFoundException;
import java.io.IOException;



public class BTMain 
{
	boolean isRecording = false;
    public BTMain()
    {
    }
	
	public void robotInit()
    {
		//do whatevah you do here
		
	}
	
    public void autonomous()
    {
    	//during autnomous, create new player object to read recorded file
    	BTMacroPlay player = null;
    	
    	//try to create a new player
    	//if there is a file, great - you have a new non-null object "player"
    	try 
    	{
    		 player = new BTMacroPlay();
		} 
		//if not, print out an error
    	//a safety routine required by our IDE, eclipse
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
    	
    	//once autonomous is enabled
		while (isAutonomous())
		{
			//as long as there is a file you found, then use the player to scan the .csv file
			//and set the motor values to their specific motors
			if (player != null)
			{
				player.play(storage);
			}
			//do nothing if there is no file
		}
		
		//if there is a player and you've disabled autonomous, then flush the rest of the values
		//and stop reading the file
		if(player!= null)
		{
			player.end(storage);
		}
    	
    }
	
    public void operatorControl()
    {
		//lets make a new record object, it will feed the stuff we record into the .csv file
    	BTMacroRecord recorder = null;
        try {
			recorder = new BTMacroRecord();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
    	while(isOperatorControl())
    	{
    		//the statement in this "if" checks if a button you designate as your record button 
    		//has been pressed, and stores the fact that it has been pressed in a variable
    		if (storage.robot.getRecordButton().getButtonValue())
			{
    			isRecording = !isRecording;
			}  
			//if our record button has been pressed, lets start recording!
			if (isRecording)
			{
    			try
    			{
    				//if we succesfully have made the recorder object, lets start recording stuff
    				//2220 uses a storage object that we can get motors values, etc. from.
    				//if you don't need to pass an object like that in, modify the methods/classes
    				if(recorder != null)
    				{
    					recorder.record(storage);
    				}
    			
				}
				catch (IOException e) 
				{
				}
			}
		}
		
		//once we're done recording, the last thing we'll do is clean up the recording using the end
		//function. more info on the end function is in the record class
    	try 
    	{
    		if(recorder != null)
    		{
    			recorder.end();
    		}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
    }
	
    public void disabled()
    {

    }
}

