/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AlgolXXI.Editor.Memory;

import java.util.Vector;
import processing.core.*;

/**
 *
 * @author Apocas
 */
public class DiagramaVenn extends PApplet {

    float radius = 10;
    Unidade root, s2, s3;
    float gravity = (float) 6.0;
    float mass = (float) 8.0;
    int x_prin = width / 2;
    int y_prin = 0;
    int niveis = 1;

    public void setup() {
        size(200, 200);
        smooth();
        fill(0);
        x_prin = width / 2;
        y_prin = 0;
        // Inputs: x, y, mass, gravity
        root = new Unidade((float) 0.0, width / 2, mass, gravity);


        root.addFilho(new Unidade((float) 0.0, width / 2, mass, gravity));
        root.addFilho(new Unidade((float) 0.0, width / 2, mass, gravity));

        root.getFilho(0).addFilho(new Unidade((float) 0.0, width / 2, mass, gravity));
        root.getFilho(0).addFilho(new Unidade((float) 0.0, width / 2, mass, gravity));
        root.getFilho(1).addFilho(new Unidade((float) 0.0, width / 2, mass, gravity));
        root.getFilho(1).addFilho(new Unidade((float) 0.0, width / 2, mass, gravity));

        root.getFilho(0).getFilho(0).addFilho(new Unidade((float) 0.0, width / 2, mass, gravity));
        root.getFilho(0).getFilho(0).addFilho(new Unidade((float) 0.0, width / 2, mass, gravity));
        root.getFilho(0).getFilho(1).addFilho(new Unidade((float) 0.0, width / 2, mass, gravity));
        root.getFilho(0).getFilho(1).addFilho(new Unidade((float) 0.0, width / 2, mass, gravity));
        root.getFilho(1).getFilho(0).addFilho(new Unidade((float) 0.0, width / 2, mass, gravity));
        root.getFilho(1).getFilho(0).addFilho(new Unidade((float) 0.0, width / 2, mass, gravity));
        root.getFilho(1).getFilho(1).addFilho(new Unidade((float) 0.0, width / 2, mass, gravity));
        root.getFilho(1).getFilho(1).addFilho(new Unidade((float) 0.0, width / 2, mass, gravity));
    }

    public void draw() {
        background(0);
        root.update(x_prin, y_prin);
        root.display(x_prin, y_prin);
    }

    public void mousePressed() {
        x_prin = mouseX;
        y_prin = mouseY;
    }

    public class Unidade {

        float vx,   vy; // The x- and y-axis velocities
        float x,   y; // The x- and y-coordinates
        float gravity;
        float mass;
        float stiffness = (float) 0.3;
        float damping = (float) 0.7;
        int nivel = niveis;
        Vector<Unidade> filhos = null;
        private float y_last = 0;

        Unidade(float xpos, float ypos, float m, float g) {
            filhos = new Vector<Unidade>(2);
            x = xpos;
            y = ypos;
            mass = m;
            gravity = g;
        }

        void addFilho(Unidade soon) {
            if (filhos.isEmpty()) {
                niveis++;
            }
            soon.setNivel(niveis);
            filhos.add(soon);

            root.update(x_prin, y_prin);
        }

        void setNivel(int nl) {
            nivel = nl;
        }

        void update(float targetX, float targetY) {
            float forceX = (targetX - x) * stiffness;
            float ax = forceX / mass;
            vx = damping * (vx + ax);
            x += vx;
            float forceY = (targetY - y) * stiffness;
            forceY += gravity;
            float ay = forceY / mass;
            vy = damping * (vy + ay);
            y += vy;

            for (int i = 0; i < filhos.size(); i++) {
                if (i == 0) {
                    filhos.get(i).update(x - (((y_last - (y - y_prin)) / radius) * radius), y);
                } else {
                    filhos.get(i).update(x + (((y_last - (y - y_prin)) / radius) * radius), y);
                }
            }

            if (filhos.isEmpty()) {
                y_last = y;
            }
        }

        void display(float nx, float ny) {
            stroke(0, 255, 0);
            line(x, y, nx, ny);
            noStroke();
            fill(255, 0, 0);
            ellipse(x, y, radius * 2, radius * 2);

            for (Unidade sp : filhos) {
                sp.display(x, y);
            }
        }

        Unidade getFilho(int i) {
            return filhos.get(i);
        }
    }
}

