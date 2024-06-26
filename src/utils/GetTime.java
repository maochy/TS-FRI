package utils;

import java.text.*;
import java.util.*;

public class GetTime
{
    public static String get() {
        final SimpleDateFormat df = new SimpleDateFormat("MM_dd_HH_mm_ss");
        return df.format(new Date());
    }
}
