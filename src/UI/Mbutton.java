package UI;

import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.*;

public class Mbutton extends JButton
{
    private static final long serialVersionUID = 39082560987930759L;
    public static final Color BUTTON_COLOR1;
    public static final Color BUTTON_COLOR2;
    public static final Color BUTTON_FOREGROUND_COLOR;
    private boolean hover;
    
    static {
        BUTTON_COLOR1 = new Color(205, 255, 205);
        BUTTON_COLOR2 = new Color(0, 0, 0);
        BUTTON_FOREGROUND_COLOR = Color.WHITE;
    }
    
    public Mbutton(final String name) {
        this.setText(name);
        this.setFont(new Font("system", 0, 15));
        this.setBorderPainted(false);
        this.setForeground(Mbutton.BUTTON_COLOR2);
        this.setFocusPainted(false);
        this.setContentAreaFilled(false);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(final MouseEvent e) {
                Mbutton.this.setForeground(Mbutton.BUTTON_FOREGROUND_COLOR);
                Mbutton.access$0(Mbutton.this, true);
                Mbutton.this.repaint();
            }
            
            @Override
            public void mouseExited(final MouseEvent e) {
                Mbutton.this.setForeground(Mbutton.BUTTON_COLOR2);
                Mbutton.access$0(Mbutton.this, false);
                Mbutton.this.repaint();
            }
        });
    }
    
    @Override
    protected void paintComponent(final Graphics g) {
        final Graphics2D g2d = (Graphics2D)g.create();
        final int h = this.getHeight();
        final int w = this.getWidth();
        float tran = 1.0f;
        if (!this.hover) {
            tran = 0.3f;
        }
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint p1;
        GradientPaint p2;
        if (this.getModel().isPressed()) {
            p1 = new GradientPaint(0.0f, 0.0f, new Color(0, 0, 0), 0.0f, (float)(h - 1), new Color(100, 100, 100));
            p2 = new GradientPaint(0.0f, 1.0f, new Color(0, 0, 0, 50), 0.0f, (float)(h - 3), new Color(255, 255, 255, 100));
        }
        else {
            p1 = new GradientPaint(0.0f, 0.0f, new Color(100, 100, 100), 0.0f, (float)(h - 1), new Color(0, 0, 0));
            p2 = new GradientPaint(0.0f, 1.0f, new Color(255, 255, 255, 100), 0.0f, (float)(h - 3), new Color(0, 0, 0, 50));
        }
        g2d.setComposite(AlphaComposite.getInstance(3, tran));
        final RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0.0f, 0.0f, (float)(w - 1), (float)(h - 1), 20.0f, 20.0f);
        final Shape clip = g2d.getClip();
        g2d.clip(r2d);
        final GradientPaint gp = new GradientPaint(0.0f, 0.0f, Mbutton.BUTTON_COLOR1, 0.0f, (float)h, Mbutton.BUTTON_COLOR2, true);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
        g2d.setClip(clip);
        g2d.setPaint(p1);
        g2d.drawRoundRect(0, 0, w - 1, h - 1, 20, 20);
        g2d.setPaint(p2);
        g2d.drawRoundRect(1, 1, w - 3, h - 3, 18, 18);
        g2d.dispose();
        super.paintComponent(g);
    }
    
    static /* synthetic */ void access$0(final Mbutton mbutton, final boolean hover) {
        mbutton.hover = hover;
    }
}
