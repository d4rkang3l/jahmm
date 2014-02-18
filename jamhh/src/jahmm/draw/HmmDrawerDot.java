/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */
package jahmm.draw;

import jutils.draw.StructuredDrawerDotBase;
import jahmm.Hmm;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.NumberFormat;
import java.util.logging.Logger;

/**
 * An HMM to <i>dot</i> file converter. See
 * <url>http://www.research.att.com/sw/tools/graphviz/</url>
 * for more information on the <i>dot</i> tool.
 * <p>
 * The command <tt>dot -Tps -o &lt;outputfile&gt; &lt;inputfile&gt;</tt>
 * should produce a Postscript file describing an HMM.
 */
class HmmDrawerDot<THMM extends Hmm<?>> extends StructuredDrawerDotBase<THMM> {

    private static final Logger LOG = Logger.getLogger(HmmDrawerDot.class.getName());

    protected double minimumAij = 0.01;
    protected double minimumPi = 0.01;
    protected NumberFormat probabilityFormat;

    /**
     * This class converts an HMM to a dot file.
     */
    HmmDrawerDot() {
        probabilityFormat = NumberFormat.getInstance();
        probabilityFormat.setMaximumFractionDigits(2);
    }

    @Override
    protected void innerWrite(THMM input, OutputStreamWriter streamWriter) throws IOException {
        streamWriter.write(this.transitions(input));
        streamWriter.write(this.states(input));
    }

    protected String transitions(Hmm<?> hmm) {
        String s = "";

        for (int i = 0; i < hmm.nbStates(); i++) {
            for (int j = 0; j < hmm.nbStates(); j++) {
                if (hmm.getAij(i, j) >= minimumAij) {
                    s += "\t" + i + " -> " + j + " [label="
                            + probabilityFormat.format(hmm.getAij(i, j)) + "];\n";
                }
            }
        }

        return s;
    }

    protected String states(THMM hmm) {
        String s = "";

        for (int i = 0; i < hmm.nbStates(); i++) {
            s += "\t" + i + " [";

            if (hmm.getPi(i) >= minimumPi) {
                s += "shape=doublecircle, label=\"" + i
                        + " - Pi= " + probabilityFormat.format(hmm.getPi(i)) + " - "
                        + opdfLabel(hmm, i) + "\"";
            } else {
                s += "shape=circle, label=\"" + i + " - "
                        + opdfLabel(hmm, i) + "\"";
            }

            s += "];\n";
        }

        return s;
    }

    protected String opdfLabel(THMM hmm, int stateNb) {
        return "[ " + hmm.getOpdf(stateNb).toString() + " ]";
    }
}
