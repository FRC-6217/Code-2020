package frc.robot.libraries.swerve;

public class reversibleMath {
    
    //Encoder Conversion Values
    private final double voltageOffset = 0.015;
    private final double voltageMultiplier = (36000/3385);

    //Variables to store rotational distances
    private double f1;
	private double f2;
	private double r1;
    private double r2;

    //Variable for non-reversed requested angle
    private double angleReq;

    //Variable for reversed requested angle
    private double rAngleReq;
    
    //Variable for shortest rotational distance
    private double shortest;
    
    //Boolean for non-reversed(true) vs reversed(false)
    private boolean isF;
    
    //Calculate desired angle and speed
    //Input in -1 to 1 for angle Request
    //Input 0.015v to 4.987v for angle Current
    public void calculate(double angleRequest, double angleCurrent) {
        // //Convert Requested angle encoder from -1 - 1 to 0deg - 360deg
        // angleRequest *= 180;
        // angleRequest += 180;
        
        //Convert Current angle encoder from 0.015v - 4.987v to 0deg - 360deg
        angleCurrent -= voltageOffset;
        angleCurrent *= voltageMultiplier;

        //Find distance between current and requested angle
        //f1 = clock wise
        //f2 = ccw
        //Requires next step to provide actual distances
		f1 = angleCurrent - angleRequest;
		f2 = angleRequest - angleCurrent;
        
        //Wrap Negative values to postive
        //f1 and f2 will now represent distance between Requested and Current angle going either direction around the circle 
        f1 = (f1 < 0) ? f1 + 360 : f1;
        f2 = (f2 < 0) ? f2 + 360 : f2;

        //reverse requested angle across circle
        rAngleReq = angleRequest + 180;
        //Wrap back to 0-360 if exceded
		rAngleReq = (rAngleReq > 360 ) ? rAngleReq - 360 : rAngleReq;

        //Find distance between current and requested angle
        //r1 = clock wise
        //r2 = ccw
        //Requires next step to provide actual distances
		r1 = angleCurrent - rAngleReq;
		r2 = rAngleReq - angleCurrent;
        
        //Wrap Negative values to postive
        //f1 and f2 will now represent distance between Requested and Current angle going either direction around the circle 
        r1 = (r1 < 0) ? r1 + 360 : r1;
        r2 = (r2 < 0) ? r2 + 360 : r2;
        
        //Find shortest distance from f1, f2, r1, r2
		shortest = f1;
		isF = true;

		if(shortest > f2){
			shortest = f2;
		}

		if(shortest > r1){
			shortest = r1;
			isF = false;
		}

		if (shortest > r2){
			shortest = r2;
			isF = false;
        }

        //If driving is reversed, set angle request to the reversed angle request
        if(!isF){
            angleRequest = rAngleReq;
        }

        // //Convert angle request from 0-360 angle to -1 - 1
        // angleRequest -= 180;
        // angleRequest /= 180;

        //Set class variable to new angle request
        angleReq = angleRequest;
    }

    //Returns angle request(either postive or negative of the original)
    public double getAngle(){
        return angleReq;
    }

    //Returns desired direction of speed as a boolean(true = non-reversed direction, false = reversed direction)
    public boolean getDirection(){
        return isF;
    }
}
