package method;

import common.*;
import program.Shape;
import model.SaveObject;
import utils.Calc;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class TSFRI implements Method {

    Shape shape;    //Failure domain
    int dimension;    //Failure domain dimension
    ComparableTestCase firstcase;   //Initial failure point
    ComparableTestCase oldCenter;    //Record the old center of gravity of Round_Robin method
    ArrayList<ComparableTestCase> finded;    //Save all found boundary points of failure domain
    int goalNumber;   //Record the total number of search points
    int have;   //Record how many points have been found
    double halfPerimeter;  //Record the half perimeter of the input field
    double relative;   //Record the initial center of gravity movement distance of center of gravity adjustment
    final double roundRobinRatio = 0.5;  //Set round_ Proportion of points used by Robin
    final double smallRatio = 0.001; //Set the  parameters of ratio of smallest value
    final int binaryTime = 20;  //Set the bisection number to find the boundary point
    boolean isTrail;
    double initLenRatio;
    final int round = 2;
    ArrayList<ComparableTestCase> record;

    public TSFRI(){
        // Initialization
        this.halfPerimeter = 0;
        this.relative = -1;
    }

    //The run method is rewritten to find the boundary point of failure domain and calculate the accuracy
    @Override
    public SaveObject run(final Shape shape, final ArrayList<ComparableTestCase> subfirstcase, final ComparableTestCase firstcase, final int goalNumber) throws Exception {

        // Initialization
        long time = System.nanoTime();
        this.dimension = shape.dimList.size();  //Record failure domain dimension
        this.shape = shape;
        this.firstcase = firstcase;
        this.goalNumber = goalNumber;
        this.initLenRatio = 1.0;
        this.have = 0;
        this.relative = -1;
        this.halfPerimeter = 0;
        this.isTrail = true;
        this.finded = new ArrayList<>();
        this.record = new ArrayList<>();
        ArrayList<SubSpace> subSpaces = new ArrayList<>();//Record subspace point set

        //Calculate the half circumference of failure region
        for (int i = 0; i < this.dimension; i++) {
            halfPerimeter+=this.shape.dimList.get(i).getRange();
        }

        //Center of gravity adjustment by round Rubin method
        this.firstcase = gravityAdjust();
        this.isTrail = false;
        //System.out.println(this.have);
        //Find the boundary point in the direction of the coordinate axis
        ArrayList<ComparableTestCase> axisPoint = new ArrayList<>();   //The boundary points in the direction of coordinate axis are recorded to facilitate the establishment of subspace
        for (int i = 0; i < this.dimension && this.have < this.goalNumber; i++) {
            for (int j = 1; j >=-1; j-=2) {
                //Distance, as a parameter of the point finding algorithm, represents a unit distance to move in a certain direction, and how much should be changed in each dimension
                ArrayList<Double> distance = new ArrayList<>();
                for (int k = 0; k < this.dimension; k++)
                    distance.add(k==i?(double)j:0.0);

                ComparableTestCase point = method(this.firstcase,distance);
                axisPoint.add(point);
            }
        }

         //Establish subspace
        for (int i = 0; i < Math.pow(2,this.dimension); i++) {    //A total of 2 ^ n subspaces are created
            ArrayList<ComparableTestCase> points = new ArrayList<>();
            //The binary number is used to identify the coordinate axis. For example, 101 marks the subspace formed by the positive direction of X axis, the negative direction of Y axis and the positive direction of Z axis, and then the corresponding points are taken from axispoint to establish the subspace
            for (int j = 0; j < this.dimension && axisPoint.size()>2*j+1; j++) {
                points.add(((i>>j)&1)==1?axisPoint.get(2*j):axisPoint.get(2*j+1));
            }
            subSpaces.add(new SubSpace(points));
        }

        this.initLenRatio = 0.5;
        //The method of divide subspace
        ArrayList<SubSpace> newSubSpaces = new ArrayList<>(); //Store new subspaces found
        while(this.have<this.goalNumber) {
            // If the current round of the binary space is finished, the next round of binary space is searched
            if (subSpaces.isEmpty()) {
                subSpaces.addAll(newSubSpaces);
                newSubSpaces = new ArrayList<>();
            }

             //Find a subspace at random
            int ran = (int)(Math.random()*subSpaces.size());
            SubSpace subSpace = subSpaces.get(ran);
            subSpaces.remove(ran);

            ComparableTestCase mid = subSpace.getMid(); //Find the center of gravity of the subspace
            //Calculate how much each dimension needs to move up by one unit from the starting point to the mid point
            ArrayList<Double> distance = new ArrayList<>();
            for (int j = 0; j < this.dimension; j++) {
                distance.add((mid.list.get(j)-this.firstcase.list.get(j))/ getDistance(this.firstcase,mid));
            }
            mid = method(mid,distance);   //Find the boundary points in the direction of the center of gravity of the subspace

           //Continue divide subspace
            for (int i = 0; i < dimension; i++) {
                ArrayList<ComparableTestCase> points = new ArrayList<>();
                for (int j = 0; j < dimension; j++) {
                    if(i!=j)
                        points.add(subSpace.getPoints().get(j));
                }
                points.add(mid);
                newSubSpaces.add(new SubSpace(points));
            }

        }

        //Save the boundary points of the failure region, and calculate the area / volume of the failure area
        SaveObject save = new SaveObject();
        save.setTime(System.nanoTime()-time);
        save.list = this.record;
        save.totalSize = Calc.calcauate(finded,dimension);
        save.shape = this.shape;
        save.setFirstcase(this.firstcase);
        save.setMethodName("TSFRI");
        return save;
    }

    //Calculating the Euclidean distance between two points in space
    private double getDistance(ComparableTestCase a, ComparableTestCase b){
        double ans = 0;
        for (int i = 0; i < a.list.size(); i++) {
            ans+=(a.list.get(i)-b.list.get(i))*(a.list.get(i)-b.list.get(i));
        }
        return Math.sqrt(ans);
    }

    //Find the midpoint of two points in space
    private ComparableTestCase getMid(ComparableTestCase a, ComparableTestCase b){
        ComparableTestCase mid = new ComparableTestCase();
        for (int i = 0; i < this.dimension; i++) {
            mid.list.add((a.list.get(i)+b.list.get(i))/2);
        }
        return mid;
    }

    //Moving a point in space
    private ComparableTestCase move(final ComparableTestCase before, ArrayList<Double> distance){
        ComparableTestCase after = new ComparableTestCase();
        for (int i = 0; i < this.dimension; i++) {
            after.list.add(before.list.get(i)+before.increment*distance.get(i));
        }
        after.increment = before.increment;
        return after;
    }

    //Point finding algorithm
    private ComparableTestCase method(ComparableTestCase now, ArrayList<Double> distance){

        shape.time = 0;

        //If a small step is pass, return directly
        if (this.isTrail){
            now.increment = initLenRatio*this.halfPerimeter/4/this.dimension/1000;
            if(this.shape.isCorrect(move(now,distance)))
                return now;
        }

        //Initialization
        ComparableTestCase failure = now,pass = null;
        now.increment = initLenRatio*this.halfPerimeter/4/this.dimension;

        //Find the first pass point
        while(pass==null){
            //Get the point after the current failure point is moved
            ComparableTestCase moved = move(failure,distance);
            if(this.shape.isCorrect(moved))
                pass = moved;
            else
                failure = moved;
        }

        //Searching for boundary points of failure region by dichotomy
        int halfNum = 0;    ///Record the half times
        while (halfNum < this.binaryTime){
            //The results of failure and pass are two points
            ComparableTestCase mid = getMid(failure,pass);
            halfNum++;
            if (this.shape.isCorrect(mid))
                pass = mid;
            else
                failure = mid;
        }

        shape.allTime.add(shape.time);

        this.have++;
        this.record.add(failure);
        this.finded.add(failure);
        return failure;
    }

    //Using Round_ Robin method to adjust the center of gravity
    private ComparableTestCase gravityAdjust(){

        //Initialize the adjusted center of gravity
        ComparableTestCase adjusted = new ComparableTestCase();
        for (int j = 0; j < this.dimension; j++)
            adjusted.list.add(0.0);

        //Roundpoints is used to record Boundary points found by round_Robin method
        ArrayList<ComparableTestCase> roundPoints= new ArrayList<>();
        roundPoints.add(this.firstcase);
        this.oldCenter = this.firstcase;
        double avg = Math.pow(2,this.dimension),sum = 0;
        //If the center of gravity movement distance is too small, the method will end early
        for (int i = 0; this.have<this.goalNumber * this.roundRobinRatio; i++) {
            //Find each point in roundpoints in the direction of i dimension, the point found serves as the base point for the next round
            ArrayList<ComparableTestCase> newRoundPoints = axisAdjust(i%this.dimension,roundPoints,adjusted.list);
            if(newRoundPoints.size()!=0)
                roundPoints = newRoundPoints;

            if (i%(this.dimension*this.round)==(this.dimension*this.round-1)){
                ComparableTestCase newCenter = new ComparableTestCase();
                for (int j = 0; j < this.dimension; j++)
                    newCenter.list.add(adjusted.list.get(j)/this.have);
//                System.out.println(getDistance(newCenter,this.oldCenter)+"&&"+this.relative*this.smallRatio);
                if (getDistance(newCenter,this.oldCenter)<this.relative*this.smallRatio)
                    break;
                //If it is the first calculation, the center of gravity moving distance is taken as the initial value
                if(i==(this.dimension*this.round-1))
                    this.relative = getDistance(newCenter,this.oldCenter);
                this.oldCenter = newCenter;
            }
        }

        //Calculate the center of gravity
        for (int i = 0; i < this.dimension; i++)
            adjusted.list.set(i,adjusted.list.get(i)/this.have);

        return adjusted;
    }

    //Use Round_Robin on axis dimension does the search
    private ArrayList<ComparableTestCase> axisAdjust(int axis, ArrayList<ComparableTestCase> roundPoints, ArrayList<Double> pointSum){

        ArrayList<ComparableTestCase> newRoundPoints;    //Record the newly found point on the axis dimension
        double len = this.halfPerimeter*this.smallRatio/4/this.dimension;     //If this point moves in the axis direction len results in pass, then this point is not used as the base point of the next round

        newRoundPoints = new ArrayList<>();
        for (int i = 0; i < roundPoints.size() && this.have < this.goalNumber *this.roundRobinRatio; i++) {
            for (int k = 1; k >= -1; k-=2) {
                //Set the trial step size and calculate the moving distance on each dimension of a unit
                roundPoints.get(i).increment = len;
                ArrayList<Double> distance = new ArrayList<Double>();
                for (int j = 0; j < this.dimension; j++)
                    distance.add(j==axis?(double)k:0.0);
                //If the trial point is failure, start with this point
                if(!this.shape.isCorrect(move(roundPoints.get(i),distance))){
                    ComparableTestCase temp = method(roundPoints.get(i),distance);
                    newRoundPoints.add(temp);
                    //Calculation of center of gravity increment
                    for (int j = 0; j < this.dimension; j++)
                        pointSum.set(j,pointSum.get(j)+temp.list.get(j));
                }
            }
        }

        //Returns the list of newly found base points
        return newRoundPoints;
    }


}
