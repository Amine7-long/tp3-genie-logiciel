package org.emp.gl.watch.states;

/**
 * Interface pour le viewer (sera implémentée par WatchViewer).
 */
public interface WatchViewer {
    void setTextPosition1(String txt);

    void setTextPosition2(String txt);

    void setTextSeparator(String txt);

    void setTextPosition3(String txt);
}
