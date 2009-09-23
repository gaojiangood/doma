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
package org.seasar.doma.wrapper;

import java.sql.Array;

import org.seasar.doma.DomaNullPointerException;

/**
 * {@link Array} を値の型とするドメインのための骨格実装です。
 * 
 * @author taedium
 * 
 */
public class ArrayWrapper extends AbstractWrapper<Array> {

    public ArrayWrapper() {
    }

    public ArrayWrapper(Array value) {
        set(value);
    }

    @Override
    public <R, P, TH extends Throwable> R accept(
            WrapperVisitor<R, P, TH> visitor, P p) throws TH {
        if (visitor == null) {
            throw new DomaNullPointerException("visitor");
        }
        if (ArrayWrapperVisitor.class.isInstance(visitor)) {
            @SuppressWarnings("unchecked")
            ArrayWrapperVisitor<R, P, TH> v = ArrayWrapperVisitor.class
                    .cast(visitor);
            return v.visitArrayWrapper(this, p);
        }
        return visitor.visitUnknownDomain(this, p);
    }

}