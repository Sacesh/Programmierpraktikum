/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformaikmuenchen.pg4.common.util;

import java.util.Collection;

/**
 *
 * @author koehleru
 */
public class CollectionUtil {

    public static <T> T getFirst(Collection<T> coll) {
        for (T t : coll) {
            return t;
        }
        return null;
    }
}
