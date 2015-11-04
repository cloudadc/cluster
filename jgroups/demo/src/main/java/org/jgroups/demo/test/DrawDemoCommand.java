
package org.jgroups.demo.test;

import org.jgroups.util.Streamable;

import java.io.DataInput;
import java.io.DataOutput;

/**
 * Encapsulates information about a draw command.
 * Used by the {@link Draw} and other demos.
 *
 */
public class DrawDemoCommand implements Streamable {
    static final byte DRAW=1;
    static final byte CLEAR=2;
    byte mode;
    int x=0;
    int y=0;
    int r=0;
    int g=0;
    int b=0;

    public DrawDemoCommand() { // needed for streamable
    }

    DrawDemoCommand(byte mode) {
        this.mode=mode;
    }

    public DrawDemoCommand(byte mode, int x, int y, int r, int g, int b) {
    	
        this.mode=mode;
        this.x=x;
        this.y=y;
        this.r=r;
        this.g=g;
        this.b=b;
    }


    public void writeTo(DataOutput out) throws Exception {
        out.writeByte(mode);
        out.writeInt(x);
        out.writeInt(y);
        out.writeInt(r);
        out.writeInt(g);
        out.writeInt(b);
    }

    public void readFrom(DataInput in) throws Exception {
        mode=in.readByte();
        x=in.readInt();
        y=in.readInt();
        r=in.readInt();
        g=in.readInt();
        b=in.readInt();
    }


    public String toString() {
        StringBuilder ret=new StringBuilder();
        switch(mode) {
            case DRAW: ret.append("DRAW(" + x + ", " + y + ") [" + r + '|' + g + '|' + b + ']');
                break;
            case CLEAR: ret.append("CLEAR");
                break;
            default:
                return "<undefined>";
        }
        return ret.toString();
    }

}
