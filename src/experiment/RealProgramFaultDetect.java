package experiment;

import program.*;

import java.io.IOException;

public class RealProgramFaultDetect {
    public static RealExpint expint = new RealExpint();
    public static RealTriangle triangle = new RealTriangle();

    public static boolean expintError(double i,double j) {
        if(i<=0||i>=4095||j<=0||j>=4095)
            return false;
        double origin,mutate;
        try {
            origin = expint.exe(i, j);
        }
        catch (Exception e) {
            try {
                mutate = expint.exeMutant(i, j);
            } catch (Exception e1) {
                return e.getMessage() != e1.getMessage();
            }
            return true;
        }
        try {
            mutate = expint.exeMutant(i, j);
        } catch (Exception e) {
            return true;
        }
        return Math.abs(origin - mutate)>1e-9;
    }

    public static boolean triangleError(double x,double y,double z){
        if(x<=0||x>=255||y<=0||y>=255||z<=0||z>=255)
            return false;
        return Math.abs(triangle.exe(x,y,z)-triangle.exeMutant(x,y,z))>1e-9;
    }

    public static boolean airyError(double x){
        if (x<=-5000||x>=5000)
            return false;
        return RealAiry.Airy.airINSTANCE.Produces_Error(x);
    }

    public static boolean bessjError(double x,double y){
        if(x<=2||x>=300||y<=0||y>=1500)
            return false;
        return RealBessj.Bessj.bessjINSTANCE.Produces_Error(x,y);
    }

    public static boolean celError(double x,double y,double z,double w){
        if(x<=0.001||x>=1||y<=0.001||y>=300||z<=0.001||z>=1||w<=0.001||w>=1)
            return false;
        return RealCel.Cel.celINSTANCE.Produces_Error(x,y,z,w);
    }

    public static boolean gammqError(double x,double y){
        if(x<=0||x>=1700||y<=0||y>=40)
            return false;
        return RealGammq.Gammq.gammqINSTANCE.Produces_Error(x,y);
    }

    public static void main(String[] args) throws IOException {
        for (double i = 0; i < 1700 ; i+=1) {
            for (double j = 0; j < 40.0 ; j+=1) {
                    if (gammqError(i, j)){
                        System.out.println(i + " " + j );
                }
            }
        }
    }
}
