package com.around.me.authorization.core.utils;

import java.util.List;

public class ListUtils {

    /**
     * List is null or empty
     *
     * @param list
     * @return
     */
    public final static boolean isEmpty(List list) {
        return list == null || list.isEmpty();
    }

    /**
     * list is not null or not empty
     * @param list
     * @return
     */
    public final static boolean isNotEmpty(List list) {
        return !ListUtils.isEmpty(list);
    }
}
