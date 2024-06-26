package thread;

import model.*;
import source.Source;
import utils.*;
import java.io.*;
import common.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import program.*;

import javax.xml.transform.*;

public class SaveThread extends Thread
{
    SaveObject obj;
    
    public SaveThread(final SaveObject obj) {
        this.obj = obj;
    }
    
    @Override
    public void run() {
        final String savepath = Source.savaPath;
        final String Time = GetTime.get();
        final File file = new File(String.valueOf(savepath)
                + obj.getMethodName()+"_"+obj.getShape().getClass().getSimpleName()+"_"+Time + ".xml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            final DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            final Document document = db.newDocument();
            final Element Result = document.createElement("Result");
            final Element Shape = document.createElement("Shape");
            final Element Method_name = document.createElement("Method_name");
            final Element totalSize = document.createElement("totalSize");
            final Element list = document.createElement("Points");
            Result.appendChild(Shape);
            Result.appendChild(Method_name);
            Result.appendChild(totalSize);
            Result.appendChild(list);
            final Shape shape = this.obj.getShape();
            final Element dim = document.createElement("Input_domain");
            for (int i = 0; i < shape.dimList.size(); ++i) {
                final Element dimension = document.createElement("Dimension");
                final Element max = document.createElement("Max");
                max.setTextContent(String.valueOf(shape.dimList.get(i).getMax()));
                final Element min = document.createElement("Min");
                min.setTextContent(String.valueOf(shape.dimList.get(i).getMin()));
                final Element range = document.createElement("Range");
                range.setTextContent(String.valueOf(shape.dimList.get(i).getRange()));
                dimension.appendChild(max);
                dimension.appendChild(min);
                dimension.appendChild(range);
                dim.appendChild(dimension);
            }
            Shape.appendChild(dim);
            final Element name = document.createElement("ShapeName");
            name.setTextContent(shape.getClass().getSimpleName());
            Shape.appendChild(name);
            final Element failRate = document.createElement("FailRate");
            failRate.setTextContent(String.valueOf(shape.getFailRate()));
            Shape.appendChild(failRate);
            final Element Compactness = document.createElement("Compactness");
            Compactness.setTextContent(String.valueOf(shape.getCompactness()));
            Shape.appendChild(Compactness);
            final Element orientation = document.createElement("Orientation");
            orientation.setTextContent(String.valueOf(shape.getorientation()));
            Shape.appendChild(orientation);
            final Element fail_points = document.createElement("Fail_points");
            for (int j = 0; j < shape.getMutiple().size(); ++j) {
                final Element point = document.createElement("Fail_point");
                final StringBuffer buff = new StringBuffer();
                for (final Double a : shape.getMutiple().get(j)) {
                    buff.append(a + " ");
                }
                point.setTextContent(buff.toString());
                fail_points.appendChild(point);
            }
            Shape.appendChild(fail_points);
            final Element fail_causing = document.createElement("First_causing");
            StringBuffer buff2 = new StringBuffer();
            for (final double a2 : this.obj.getFirstcase().list) {
                buff2.append(String.valueOf(a2) + " ");
            }
            fail_causing.setTextContent(buff2.toString());
            Shape.appendChild(fail_causing);
            Method_name.setTextContent(this.obj.getMethodName());
            totalSize.setTextContent(String.valueOf(this.obj.getTotalSize()));
            for (final ComparableTestCase testcase : this.obj.getList()) {
                final Element point2 = document.createElement("Testcase");
                buff2 = new StringBuffer();
                for (final Double a3 : testcase.list) {
                    buff2.append(a3 + " ");
                }
                point2.setTextContent(buff2.toString());
                list.appendChild(point2);
            }
            document.appendChild(Result);
            final TransformerFactory tff = TransformerFactory.newInstance();
            final Transformer tf = tff.newTransformer();
            tf.setOutputProperty("indent", "yes");
            tf.transform(new DOMSource(document), new StreamResult(file.toURI().getPath()));
        }
        catch (ParserConfigurationException | TransformerException ex2) {
            ex2.printStackTrace();
        }
    }
}
