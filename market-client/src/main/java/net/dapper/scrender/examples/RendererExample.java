/**
 * 
 */
package net.dapper.scrender.examples;

import java.io.File;

import net.dapper.scrender.Scrender;

/**
 * @author Ohad Serfaty
 * 
 */
public class RendererExample {

    public static void main(String[] args) throws Exception {
        // Create a standard self-disposing scrender object :
        Scrender scrender = new Scrender();
        scrender.init();

        scrender.render("http://203.119.80.62/sealverify/verifyseal.dll?sn=2011062400100008140&bd=BackDoor&ct=ad&pa=074372", new File("d:/sealverify.html.jpg"));

        scrender = new Scrender();
        scrender.init();
        // render it ( and dispose when finish):
        scrender.render("http://scrender.sourceforge.net", new File("d:/scrender.index.html.jpg"));

        // Creae a second scrender object , one that is continuous ( meaning ,
        // you can make all the screenshots
        // you want but you have to dispose it yourself )
        Scrender scrender2 = new Scrender(true);
        scrender2.init();
        scrender2.render("http://www.google.com", new File("d:/google.com.jpg"));

        scrender2 = new Scrender();
        scrender2.init();
        scrender2.render("http://www.yahoo.com", new File("d:/yahoo.com.jpg"));
        scrender2.dispose();

    }
}
