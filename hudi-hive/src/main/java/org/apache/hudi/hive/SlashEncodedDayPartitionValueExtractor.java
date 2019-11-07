/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hudi.hive;

import com.beust.jcommander.internal.Lists;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


/**
 * HDFS Path contain hive partition values for the keys it is partitioned on. This mapping is not
 * straight forward and requires a pluggable implementation to extract the partition value from HDFS
 * path.
 * <p>
 * This implementation extracts datestr=yyyy-mm-dd from path of type /yyyy/mm/dd
 */
public class SlashEncodedDayPartitionValueExtractor implements PartitionValueExtractor {

  private transient DateTimeFormatter dtfOut;

  public SlashEncodedDayPartitionValueExtractor() {
    this.dtfOut = DateTimeFormat.forPattern("yyyy-MM-dd");
  }

  private DateTimeFormatter getDtfOut() {
    if (dtfOut == null) {
      dtfOut = DateTimeFormat.forPattern("yyyy-MM-dd");
    }
    return dtfOut;
  }

  @Override
  public List<String> extractPartitionValuesInPath(String partitionPath) {
    String[] splits = partitionPath.split("/");
    ArrayList<String> al = new ArrayList<String>();
    for(int i = 0; i <= splits.length -1 ; i++ ){
        al.add(splits[i]);
    }
    return Lists.newArrayList(al);
  }

}