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

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import org.seasar.doma.internal.apt.util.TypeUtil;

/**
 * @author taedium
 * 
 */
public abstract class AbstractDataType implements DataType {

    protected final TypeMirror typeMirror;

    protected final String typeName;

    protected final String typeNameAsTypeParameter;

    protected final TypeElement typeElement;

    protected String qualifiedName;

    protected AbstractDataType(TypeMirror typeMirror, ProcessingEnvironment env) {
        assertNotNull(typeMirror, env);
        this.typeMirror = typeMirror;
        this.typeName = TypeUtil.getTypeName(typeMirror, env);
        this.typeElement = TypeUtil.toTypeElement(typeMirror, env);
        if (typeElement != null) {
            qualifiedName = typeElement.getQualifiedName().toString();
        } else {
            qualifiedName = typeName;
        }
        if (typeMirror.getKind().isPrimitive()) {
            Class<?> boxedClass = getBoxedClass(typeMirror);
            typeNameAsTypeParameter = boxedClass.getName();
        } else {
            typeNameAsTypeParameter = typeName;
        }
    }

    public TypeMirror getTypeMirror() {
        return typeMirror;
    }

    public String getTypeName() {
        return typeName;
    }

    @Override
    public String getTypeNameAsTypeParameter() {
        return typeNameAsTypeParameter;
    }

    @Override
    public String getQualifiedName() {
        return qualifiedName;
    }

    @Override
    public boolean isEnum() {
        return typeElement != null && typeElement.getKind() == ElementKind.ENUM;
    }

    @Override
    public boolean isPrimitive() {
        return typeMirror.getKind().isPrimitive();
    }

    protected Class<?> getBoxedClass(TypeMirror typeMirror) {
        switch (typeMirror.getKind()) {
        case BOOLEAN:
            return Boolean.class;
        case BYTE:
            return Byte.class;
        case SHORT:
            return Short.class;
        case INT:
            return Integer.class;
        case LONG:
            return Long.class;
        case FLOAT:
            return Float.class;
        case DOUBLE:
            return Double.class;
        case CHAR:
            return Character.class;
        }
        return assertUnreachable();
    }

}