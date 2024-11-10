package com.lyflexi.threaddesignpattern.syncParten.guardedSuspension.rpcDubbo_OneToOne;

import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * @Author: ly
 * @Date: 2024/3/16 10:59
 */
class Mailboxes {
    private static Map<Integer, GuardedObject> boxes = new Hashtable<>();

    private static int id = 1;
    // 产生唯一 id
    private static synchronized int generateId() {
        return id++;
    }

    public static GuardedObject getGuardedObject(int id) {
        return boxes.remove(id);
    }

    public static GuardedObject createGuardedObject() {
        GuardedObject go = new GuardedObject(generateId());
        boxes.put(go.getId(), go);
        return go;
    }

    public static Set<Integer> getIds() {
        return boxes.keySet();
    }
}
