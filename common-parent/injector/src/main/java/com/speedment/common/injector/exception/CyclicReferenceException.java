/**
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.common.injector.exception;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 *
 * @author Emil Forslund
 */
public final class CyclicReferenceException extends RuntimeException {

    private static final long serialVersionUID = -5890725902790625145L;
    
    private final List<Class<?>> stack;
    
    public CyclicReferenceException(Collection<Class<?>> stack) {
        this.stack = new LinkedList<>(stack);
    }
    
    public CyclicReferenceException(Collection<Class<?>> stack, Throwable thrw) {
        super(thrw);
        this.stack = new LinkedList<>(stack);
    }
    
    public CyclicReferenceException(Class<?> referencedClass) {
        this.stack = Collections.singletonList(referencedClass);
    }
    
    public CyclicReferenceException(Class<?> referencingClass, CyclicReferenceException cause) {
        final LinkedList<Class<?>> list = new LinkedList<>(cause.stack);
        list.addFirst(referencingClass);
        this.stack = list;
    }

    @Override
    public String getMessage() {
        return "Cyclic dependency prevented class from being injected. Stack: "
            + stack.stream().map(Class::getSimpleName).collect(toList()) + ".";
    }
}