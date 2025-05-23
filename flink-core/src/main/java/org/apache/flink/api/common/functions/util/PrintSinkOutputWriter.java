/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.api.common.functions.util;

import org.apache.flink.annotation.Internal;
import org.apache.flink.api.connector.sink2.SinkWriter;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;

/** Print sink output writer for DataStream and DataSet print API. */
@Internal
public class PrintSinkOutputWriter<IN> implements Serializable, SinkWriter<IN> {

    private static final long serialVersionUID = 1L;

    private final boolean isStdErr;
    private transient PrintStream stream;
    private final String sinkIdentifier;
    private transient String completedPrefix;

    public PrintSinkOutputWriter() {
        this("", false);
    }

    public PrintSinkOutputWriter(final boolean isStdErr) {
        this("", isStdErr);
    }

    public PrintSinkOutputWriter(final String sinkIdentifier, final boolean isStdErr) {
        this.isStdErr = isStdErr;
        this.sinkIdentifier = (sinkIdentifier == null ? "" : sinkIdentifier);
    }

    public void open(int subtaskIndex, int numParallelSubtasks) {
        // get the target stream
        stream = isStdErr ? System.err : System.out;

        completedPrefix = sinkIdentifier;

        if (numParallelSubtasks > 1) {
            if (!completedPrefix.isEmpty()) {
                completedPrefix += ":";
            }
            completedPrefix += (subtaskIndex + 1);
        }

        if (!completedPrefix.isEmpty()) {
            completedPrefix += "> ";
        }
    }

    public void write(IN record) {
        stream.println(completedPrefix + record.toString());
    }

    @Override
    public void write(IN element, Context context) throws IOException, InterruptedException {
        write(element);
    }

    @Override
    public void flush(boolean endOfInput) throws IOException, InterruptedException {
        stream.flush();
    }

    @Override
    public void close() throws Exception {}

    @Override
    public String toString() {
        return "Print to " + (isStdErr ? "System.err" : "System.out");
    }
}
