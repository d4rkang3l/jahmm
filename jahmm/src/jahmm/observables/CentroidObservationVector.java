/*
 * Copyright (c) 2004-2009, Jean-Marc François. All Rights Reserved.
 * Licensed under the New BSD license.  See the LICENSE file.
 */
package jahmm.observables;

import java.util.List;

/**
 * This class represents the centroid of a set of {@link ObservationVector
 * ObservationVector}.
 */
public class CentroidObservationVector implements Centroid<ObservationVector> {

    private final ObservationVector value;

    /**
     * Creates a new centroid that represents the mean value of a set of
     * {@link ObservationVector ObservationVector}s.
     *
     * @param o The initial value of the new centroid.
     * @throws java.lang.CloneNotSupportedException
     */
    public CentroidObservationVector(ObservationVector o) throws CloneNotSupportedException {
        this.value = o.clone();
    }

    @Override
    public void reevaluateAdd(ObservationVector e,
            List<? extends ObservationVector> v) {
        double[] evalues = e.value;

        for (int i = 0; i < value.dimension(); i++) {
            value.value[i]
                    = ((value.value[i] * v.size()) + evalues[i]) / (v.size() + 1);
        }
    }

    @Override
    public void reevaluateRemove(ObservationVector e,
            List<? extends ObservationVector> v) {
        double[] evalues = e.value;

        for (int i = 0; i < value.dimension(); i++) {
            value.value[i]
                    = ((value.value[i] * v.size()) - evalues[i]) / (v.size() - 1);
        }
    }

    /**
     * Returns the distance between this centroid and an element. The distance
     * metric is the euclidian distance.
     *
     * @param e The element, which must be an {@link ObservationVector
     *          ObservationVector} with a dimension compatible with this centroid.
     * @return The distance between <code>element</code> and this centroid.
     */
    @Override
    public double distance(ObservationVector e) {
        ObservationVector diff = value.minus(e);
        double sum = 0.;

        for (int i = 0; i < diff.dimension(); i++) {
            sum += diff.value[i] * diff.value[i];
        }

        return Math.sqrt(sum);
    }
}
