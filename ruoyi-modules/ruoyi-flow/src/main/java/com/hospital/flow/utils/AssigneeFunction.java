package com.hospital.flow.utils;

import java.util.Set;

/**
 * @author lgx
 */
@FunctionalInterface
public interface AssigneeFunction {

    Set<Long> getAssigneeIds(String value);
}
