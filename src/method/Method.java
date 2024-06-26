package method;

import program.*;

import java.util.*;
import common.*;
import model.*;

public interface Method
{
    SaveObject run(final Shape p0, final ArrayList<ComparableTestCase> p1, final ComparableTestCase p2, final int p3) throws Exception;
}
