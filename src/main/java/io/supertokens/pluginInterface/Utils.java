/*
 *    Copyright (c) 2023, VRAI Labs and/or its affiliates. All rights reserved.
 *
 *    This software is licensed under the Apache License, Version 2.0 (the
 *    "License") as published by the Apache Software Foundation.
 *
 *    You may not use this file except in compliance with the License. You may
 *    obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *    License for the specific language governing permissions and limitations
 *    under the License.
 */

package io.supertokens.pluginInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Utils {
    public static boolean unorderedStringArrayEquals(String[] arr1, String[] arr2) {
        if (arr1 == null && arr2 == null) {
            return true;
        }

        if (arr1 == null || arr2 == null) {
            return false;
        }

        Set<String> set1 = Set.of(arr1);
        Set<String> set2 = Set.of(arr2);

        return set1.equals(set2);
    }

    public static boolean unorderedArrayEquals(Object[] array1, Object[] array2) {
        if (array1 == null && array2 == null) {
            return true;
        } else if (array1 == null || array2 == null) {
            return false;
        }

        List<Object> items1 = List.of(array1);
        List<Object> items2 = new ArrayList<>();
        items2.addAll(Arrays.asList(array2));

        if (items1.size() != items2.size()) return false;

        for (Object p1 : items1) {
            boolean found = false;
            for (Object p2 : items2) {
                if (p1.equals(p2)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                return false;
            } else {
                items2.remove(p1);
            }
        }

        return true;
    }
}
