/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.runtime.operators.coordination;

import java.io.Serializable;

/**
 * Root interface for all events sent between {@link OperatorCoordinator} and an {@link
 * OperatorEventHandler}.
 */
public interface OperatorEvent extends Serializable {
    /**
     * @return true if event is optional and an occasional loss or inability to deliver that event
     *     doesn't affect the job's correctness.
     */
    default boolean isLossTolerant() {
        return false;
    }
}
