package listenner;

import javax.swing.*;
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.event.*;

public class InfiniteProgressPanel extends JComponent implements MouseListener
{
    private static final long serialVersionUID = 1L;
    protected Area[] ticker;
    protected Thread animation;
    protected boolean started;
    protected int alphaLevel;
    protected int rampDelay;
    protected float shield;
    protected String text;
    protected int barsCount;
    protected float fps;
    protected RenderingHints hints;
    
    public InfiniteProgressPanel() {
        this("");
    }
    
    public InfiniteProgressPanel(final String text) {
        this(text, 14);
    }
    
    public InfiniteProgressPanel(final String text, final int barsCount) {
        this(text, barsCount, 0.7f);
    }
    
    public InfiniteProgressPanel(final String text, final int barsCount, final float shield) {
        this(text, barsCount, shield, 15.0f);
    }
    
    public InfiniteProgressPanel(final String text, final int barsCount, final float shield, final float fps) {
        this(text, barsCount, shield, fps, 300);
    }
    
    public InfiniteProgressPanel(final String text, final int barsCount, final float shield, final float fps, final int rampDelay) {
        this.ticker = null;
        this.animation = null;
        this.started = false;
        this.alphaLevel = 0;
        this.rampDelay = 300;
        this.shield = 0.7f;
        this.text = "";
        this.barsCount = 14;
        this.fps = 15.0f;
        this.hints = null;
        this.text = text;
        this.rampDelay = ((rampDelay >= 0) ? rampDelay : 0);
        this.shield = ((shield >= 0.0f) ? shield : 0.0f);
        this.fps = ((fps > 0.0f) ? fps : 15.0f);
        this.barsCount = ((barsCount > 0) ? barsCount : 14);
        (this.hints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)).put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.hints.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    }
    
    public void setText(final String text) {
        this.repaint();
        this.text = text;
    }
    
    public String getText() {
        return this.text;
    }
    
    public void start() {
        this.addMouseListener(this);
        this.setVisible(true);
        this.ticker = this.buildTicker();
        (this.animation = new Thread(new Animator(true))).start();
    }
    
    public void stop() {
        if (this.animation != null) {
            this.animation.interrupt();
            this.animation = null;
            (this.animation = new Thread(new Animator(false))).start();
        }
    }
    
    public void interrupt() {
        if (this.animation != null) {
            this.animation.interrupt();
            this.animation = null;
            this.removeMouseListener(this);
            this.setVisible(false);
        }
    }
    
    public void paintComponent(final Graphics g) {
        if (this.started) {
            final int width = this.getWidth();
            final int height = this.getHeight();
            double maxY = 0.0;
            final Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHints(this.hints);
            g2.setColor(new Color(255, 255, 255, (int)(this.alphaLevel * this.shield)));
            g2.fillRect(0, 0, this.getWidth(), this.getHeight());
            for (int i = 0; i < this.ticker.length; ++i) {
                final int channel = 224 - 128 / (i + 1);
                g2.setColor(new Color(channel, channel, channel, this.alphaLevel));
                g2.fill(this.ticker[i]);
                final Rectangle2D bounds = this.ticker[i].getBounds2D();
                if (bounds.getMaxY() > maxY) {
                    maxY = bounds.getMaxY();
                }
            }
            if (this.text != null && this.text.length() > 0) {
                final FontRenderContext context = g2.getFontRenderContext();
                final TextLayout layout = new TextLayout(this.text, this.getFont(), context);
                final Rectangle2D bounds = layout.getBounds();
                g2.setColor(this.getForeground());
                layout.draw(g2, (float)(width - bounds.getWidth()) / 2.0f, (float)(maxY + layout.getLeading() + 2.0f * layout.getAscent()));
            }
        }
    }
    
    private Area[] buildTicker() {
        final Area[] ticker = new Area[this.barsCount];
        final Point2D.Double center = new Point2D.Double(this.getWidth() / 2.0, this.getHeight() / 2.0);
        final double fixedAngle = 6.283185307179586 / this.barsCount;
        for (double i = 0.0; i < this.barsCount; ++i) {
            final Area primitive = this.buildPrimitive();
            final AffineTransform toCenter = AffineTransform.getTranslateInstance(center.getX(), center.getY());
            final AffineTransform toBorder = AffineTransform.getTranslateInstance(45.0, -6.0);
            final AffineTransform toCircle = AffineTransform.getRotateInstance(-i * fixedAngle, center.getX(), center.getY());
            final AffineTransform toWheel = new AffineTransform();
            toWheel.concatenate(toCenter);
            toWheel.concatenate(toBorder);
            primitive.transform(toWheel);
            primitive.transform(toCircle);
            ticker[(int)i] = primitive;
        }
        return ticker;
    }
    
    private Area buildPrimitive() {
        final Rectangle2D.Double body = new Rectangle2D.Double(6.0, 0.0, 30.0, 12.0);
        final Ellipse2D.Double head = new Ellipse2D.Double(0.0, 0.0, 12.0, 12.0);
        final Ellipse2D.Double tail = new Ellipse2D.Double(30.0, 0.0, 12.0, 12.0);
        final Area tick = new Area(body);
        tick.add(new Area(head));
        tick.add(new Area(tail));
        return tick;
    }
    
    @Override
    public void mouseClicked(final MouseEvent e) {
    }
    
    @Override
    public void mousePressed(final MouseEvent e) {
    }
    
    @Override
    public void mouseReleased(final MouseEvent e) {
    }
    
    @Override
    public void mouseEntered(final MouseEvent e) {
    }
    
    @Override
    public void mouseExited(final MouseEvent e) {
    }
    
    protected class Animator implements Runnable
    {
        private boolean rampUp;
        
        protected Animator(final boolean rampUp) {
            this.rampUp = true;
            this.rampUp = rampUp;
        }
        
        @Override
        public void run() {
            final Point2D.Double center = new Point2D.Double(InfiniteProgressPanel.this.getWidth() / 2.0, InfiniteProgressPanel.this.getHeight() / 2.0);
            final double fixedIncrement = 6.283185307179586 / InfiniteProgressPanel.this.barsCount;
            final AffineTransform toCircle = AffineTransform.getRotateInstance(fixedIncrement, center.getX(), center.getY());
            final long start = System.currentTimeMillis();
            if (InfiniteProgressPanel.this.rampDelay == 0) {
                InfiniteProgressPanel.this.alphaLevel = (this.rampUp ? 255 : 0);
            }
            InfiniteProgressPanel.this.started = true;
            boolean inRamp = this.rampUp;
            while (!Thread.interrupted()) {
                if (!inRamp) {
                    for (int i = 0; i < InfiniteProgressPanel.this.ticker.length; ++i) {
                        InfiniteProgressPanel.this.ticker[i].transform(toCircle);
                    }
                }
                InfiniteProgressPanel.this.repaint();
                if (this.rampUp) {
                    if (InfiniteProgressPanel.this.alphaLevel < 255) {
                        InfiniteProgressPanel.this.alphaLevel = (int)(255L * (System.currentTimeMillis() - start) / InfiniteProgressPanel.this.rampDelay);
                        if (InfiniteProgressPanel.this.alphaLevel >= 255) {
                            InfiniteProgressPanel.this.alphaLevel = 255;
                            inRamp = false;
                        }
                    }
                }
                else if (InfiniteProgressPanel.this.alphaLevel > 0) {
                    InfiniteProgressPanel.this.alphaLevel = (int)(255L - 255L * (System.currentTimeMillis() - start) / InfiniteProgressPanel.this.rampDelay);
                    if (InfiniteProgressPanel.this.alphaLevel <= 0) {
                        InfiniteProgressPanel.this.alphaLevel = 0;
                        break;
                    }
                }
                try {
                    Thread.sleep(inRamp ? 10 : ((int)(1000.0f / InfiniteProgressPanel.this.fps)));
                }
                catch (InterruptedException ie) {
                    break;
                }
                Thread.yield();
            }
            if (!this.rampUp) {
                InfiniteProgressPanel.this.started = false;
                InfiniteProgressPanel.this.repaint();
                InfiniteProgressPanel.this.setVisible(false);
                InfiniteProgressPanel.this.removeMouseListener(InfiniteProgressPanel.this);
            }
        }
    }
}
