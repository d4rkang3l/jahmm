/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */
package jahmm.io;

import jahmm.RegularHmmBase;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * This class can read Hidden Markov Models from a byte stream.
 * <p>
 * The HMM objects are simply deserialized. HMMs could thus be unreadable using
 * a different release of this library.
 */
public class HmmBinaryReader {

    /**
     * Reads a HMM from a byte stream.
     *
     * @param stream Holds the byte stream the HMM is read from.
     * @return The {@link be.ac.ulg.montefiore.run.jahmm.Hmm HMM} read.
     * @throws java.io.IOException
     */
    static public RegularHmmBase<?> read(InputStream stream)
            throws IOException {
        ObjectInputStream ois = new ObjectInputStream(stream);

        try {
            return (RegularHmmBase) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private HmmBinaryReader() {
    }
}
