/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.doma.internal.apt.type;

import static org.seasar.doma.internal.util.AssertionUtil.*;

import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import org.seasar.doma.internal.apt.TypeUtil;
import org.seasar.doma.jdbc.IterationCallback;

public class IterationCallbackType {

    protected TypeMirror type;

    protected String typeName;

    protected AnyType returnType;

    protected EntityType entityType;

    protected DomainType domainType;

    protected ValueType valueType;

    protected boolean parametarized;

    protected IterationCallbackType() {
    }

    public TypeMirror getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    public AnyType getReturnType() {
        return returnType;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public DomainType getDomainType() {
        return domainType;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public boolean isParametarized() {
        return parametarized;
    }

    public static IterationCallbackType newInstance(TypeMirror type,
            ProcessingEnvironment env) {
        assertNotNull(type, env);
        DeclaredType iterationCallbackDeclaredType = getIterationCallbackDeclaredType(
                type, env);
        if (iterationCallbackDeclaredType == null) {
            return null;
        }

        IterationCallbackType iterationCallbackType = new IterationCallbackType();
        iterationCallbackType.type = type;
        iterationCallbackType.typeName = TypeUtil.getTypeName(type, env);
        List<? extends TypeMirror> typeArguments = iterationCallbackDeclaredType
                .getTypeArguments();
        if (typeArguments.size() == 2) {
            TypeMirror callbackResultType = typeArguments.get(0);
            TypeMirror callbackTargetType = typeArguments.get(1);
            iterationCallbackType.parametarized = true;

            iterationCallbackType.returnType = AnyType.newInstance(
                    callbackResultType, env);
            iterationCallbackType.entityType = EntityType.newInstance(
                    callbackTargetType, env);
            if (iterationCallbackType.entityType == null) {
                iterationCallbackType.domainType = DomainType.newInstance(
                        callbackTargetType, env);
                if (iterationCallbackType.domainType == null) {
                    iterationCallbackType.valueType = ValueType.newInstance(
                            callbackTargetType, env);
                }
            }
        }

        return iterationCallbackType;
    }

    protected static DeclaredType getIterationCallbackDeclaredType(
            TypeMirror type, ProcessingEnvironment env) {
        if (TypeUtil.isSameType(type, IterationCallback.class, env)) {
            return TypeUtil.toDeclaredType(type, env);
        }
        for (TypeMirror supertype : env.getTypeUtils().directSupertypes(type)) {
            if (TypeUtil.isSameType(supertype, IterationCallback.class, env)) {
                return TypeUtil.toDeclaredType(supertype, env);
            }
            getIterationCallbackDeclaredType(supertype, env);
        }
        return null;
    }
}
