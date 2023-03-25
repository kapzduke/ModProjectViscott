package viscott.content;

import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import mindustry.entities.Effect;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.rect;
import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;

public class PvEffects {
    public static Effect
        slowEnergeticEffect, particleDeath1,particleDeath2,particleDeath3,railFrag,waveBullet
            ;
    public static void load()
    {
        slowEnergeticEffect = new Effect(32,e -> {
            color(Pal.sap);
            alpha(e.fout());
            stroke(e.fout()*4);
            circle(e.x,e.y,e.fin()*8.3f*8);
        });
        particleDeath1 = new Effect(100, e -> {
            color(Pal.sap);
            alpha(e.fout());
            stroke(e.fout()*4);
            circle(e.x,e.y,e.fin()*8);
        });
        particleDeath2 = new Effect(100, e -> {
            color(Pal.sap);
            alpha(e.fout());
            stroke(e.fout()*8);
            circle(e.x,e.y,e.fin()*16);
            circle(e.x,e.y,e.fin()*8);
        });
        particleDeath3 = new Effect(100, e -> {
            color(Pal.sap);
            alpha(e.fout());
            stroke(e.fout()*8);
            circle(e.x,e.y,e.fin()*24);
            circle(e.x,e.y,e.fin()*16);
            circle(e.x,e.y,e.fin()*8);
        });
        railFrag = new Effect(32f, e -> {
            color(Pal.sap);

            for(int i : Mathf.signs){
                Drawf.tri(e.x, e.y, 20f * e.fout(), 30f, e.rotation + 90 + 90f * i);
            }

            Drawf.light(e.x, e.y, 60f * e.fout(), Pal.orangeSpark, 0.5f);
        });
        waveBullet = new Effect(120f,e -> {
            color(Pal.lancerLaser);

            float x1 = e.x + Mathf.sin(e.rotation/180*Mathf.pi) * Mathf.sin(e.fin()*6*Mathf.pi) * 8;
            float y1 = e.y - Mathf.cos(e.rotation/180*Mathf.pi) * Mathf.sin(e.fin()*6*Mathf.pi) * 8;
            float x2 = e.x - Mathf.sin(e.rotation/180*Mathf.pi) * Mathf.sin(e.fin()*6*Mathf.pi) * 8;
            float y2 = e.y + Mathf.cos(e.rotation/180*Mathf.pi) * Mathf.sin(e.fin()*6*Mathf.pi) * 8;
            Fill.circle(x1,y1,e.fout());
            Fill.circle(x2,y2,e.fout());
        });
    }
}
