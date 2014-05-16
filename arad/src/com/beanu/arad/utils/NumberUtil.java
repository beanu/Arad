/*
 * Copyright 1999-2101 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 	http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.beanu.arad.utils;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * All about deal with numbers, floats, doubles.
 * Date: 12-4-16
 * Time: 下午1:35
 *
 * @author zhe.yangz
 */
public class NumberUtil {

    /**
     * 使数字按最小保留小数位数格式化,3个为一组添加逗号
     * @param num 需要格式化数字
     * @param digits 最小保留小数位数
     * @return
     */
    public static String fractionDigits(float num, int digits) {
        NumberFormat nbf = NumberFormat.getInstance(Locale.US);
        nbf.setMinimumFractionDigits(digits);
        nbf.setMaximumFractionDigits(digits);
        nbf.setGroupingUsed(true);
        return nbf.format(num);
    }

    public static String fractionDigits(double num, int digits) {
        NumberFormat nbf = NumberFormat.getInstance(Locale.US);
        nbf.setMinimumFractionDigits(digits);
        nbf.setMaximumFractionDigits(digits);
        nbf.setGroupingUsed(true);
        return nbf.format(num);
    }

    private NumberUtil() {}
}
