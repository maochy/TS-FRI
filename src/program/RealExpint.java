package program;

import common.Dimension;
import common.ComparableTestCase;
import experiment.RealProgramFaultDetect;

import java.util.ArrayList;

public class RealExpint extends Shape {

    private static final double MAXIT = 100;
    private static final double EULER = 0.5772156649;
    private static final double FPMIN = 1.0e-30;
    private static final double EPS = 1.0e-7;

    public RealExpint() {
        dimList = new ArrayList<>();
        dimList.add(new Dimension(0, 4095));
        dimList.add(new Dimension(0, 4095));
        failTotalSize = 6668534.8214;
    }

    public double exe(double n, double x)
    {
        int i,ii;
        double nm1;
        double a,b,c,d,del,fact,h,psi,ans;

        nm1=n-1;

        if (n < 0 || x < 0.0 || (x==0.0 && (n==0 || n==1)))
            throw new RuntimeException("error: n < 0 or x < 0");
        else
        {
            if (n == 0)
                ans = Math.exp(-x)/x;
            else
            {
                if (x == 0.0)
                    ans=1.0/nm1;
                else
                {
                    if (x > 1.0)
                    {
                        b=x+n;
                        c=1.0/FPMIN;
                        d=1.0/b;
                        h=d;

                        for (i=1;i<=MAXIT;i++)
                        {
                            a = -i*(nm1+i);
                            b += 2.0;
                            d=1.0/(a*d+b);
                            c=b+a/c;
                            del=c*d;
                            h *= del;

                            if (Math.abs(del-1.0) < EPS)
                            {
                                return h*Math.exp(-x);
                            }
                        }

                        throw new RuntimeException("continued fraction failed in expint");
                    }

                    else
                    {
                        ans = (nm1!=0 ? 1.0/nm1 : -Math.log(x)-EULER);
                        fact=1.0;

                        for (i=1;i<=MAXIT;i++) {
                            fact *= -x/i;

                            if (i != nm1)
                                del = -fact/(i-nm1);
                            else
                            {
                                psi = -EULER;

                                for (ii=1;ii<=nm1;ii++)
                                    psi += 1.0/ii;

                                del = fact*(-Math.log(x)+psi);
                            }

                            ans += del;

                            if (Math.abs(del) < Math.abs(ans)*EPS)
                            {
                                return ans;
                            }
                        }
                        throw new RuntimeException("series failed in expint");
                    }
                }
            }
        }
        return ans;
    }

    public double exeMutant( double n, double x )
    {
        int i;
        int ii;
        double nm1;
        double a;
        double b;
        double c;
        double d;
        double del;
        double fact;
        double h;
        double psi;
        double ans;
        nm1 = n - 1;
        if (n < 0 || x < 0.0 || x == 0.0 && (n == 0 || n == 1)) {
            throw new RuntimeException( "error: n < 0 or x < 0" );
        } else {
            if (n == 0) {
                ans = Math.exp( -x ) / x;
            } else {
                if (x == 0.0) {
                    ans = 1.0 / nm1;
                } else {
                    if (x > 1.0) {
                        b = x + n;
                        c = 1.0 / FPMIN;
                        d = 1.0 / b;
                        h = d;
                        for (i = 1; i <= MAXIT; i++) {
                            a = -i * (nm1 + i);
                            b += 2.0;
                            d = 1.0 / (a * d + b);
                            c = b + a / ++c;		//AOIS
                            del = c * d;
                            h *= del;
                            if (Math.abs( del - 1.0 ) < EPS) {
                                return h * Math.exp( -x );
                            }
                        }
                        throw new RuntimeException( "continued fraction failed in expint" );
                    } else {
                        ans = nm1 != 0 ? 1.0 / nm1 : -Math.log( x ) - EULER;
                        fact = 1.0;
                        for (i = 1; i <= MAXIT; i++) {
                            fact *= -x / i;
                            if (i != nm1) {
                                del = -fact / (i - nm1);
                            } else {
                                psi = -EULER;
                                for (ii = 1; ii <= nm1; ii++) {
                                    psi += 1.0 / ii;
                                }
                                del = fact * (-Math.log( x ) + psi);
                            }
                            ans += del;
                            if (Math.abs( del ) < Math.abs( ans ) * EPS) {
                                return ans;
                            }
                        }
                        throw new RuntimeException( "series failed in expint" );
                    }
                }
            }
        }
        return ans;
    }

    @Override
    public void product() {
        ArrayList<Dimension> d = new ArrayList<Dimension>();
        d.add(new Dimension(0,4095));
        d.add(new Dimension(0,4095));
        this.dimList = d;
    }

    @Override
    public boolean isCorrect(ComparableTestCase p) {
        this.time++;
        this.cost++;
        long temp_time = System.nanoTime();
        boolean result = !RealProgramFaultDetect.expintError(p.list.get(0),p.list.get(1));
        this.realtime+=System.nanoTime()-temp_time;
        return result;
    }
}
